package br.iesb.VIS2048.comm;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;

public class SerialComm {

	private String portName = null;
	private int baudRate = SerialPort.BAUDRATE_115200;
	private int dataBits = SerialPort.DATABITS_8;
	private int stopBits = SerialPort.STOPBITS_1;
	private int parity = SerialPort.PARITY_NONE;
	private SerialPort serialPort;
	private boolean readyToWrite = false;
	private int dataSize = 2048;
	private String Data = "";
	private int pos = 0;
	private String reading = "";

	public SerialComm() {
		String[] ports = SerialPortList.getPortNames();
		if (ports.length > 0) {
			if(this.portName == null) {
				setPortName(ports[0]);
			}
			serialPort = new SerialPort(getPortName());
			if (serialPort != null) {
				System.out.println("Connected to Port " + getPortName());
			}
		} else {
			System.out.println("Nenhuma porta disponível");
		}
	}

	public void configPort(String portName) {
		setPortName(portName);
	}

	public void openComm() {
		try {
			serialPort.openPort();
		} catch (SerialPortException e3) {
			e3.printStackTrace();
		}
		this.updatePortSettings(portName, baudRate, dataBits, stopBits, parity);
		try {
			serialPort.addEventListener(new Reader(), SerialPort.MASK_RXCHAR | SerialPort.MASK_RXFLAG
					| SerialPort.MASK_CTS | SerialPort.MASK_DSR | SerialPort.MASK_RLSD);
		} catch (SerialPortException e1) {
			e1.printStackTrace();
		}
		if (serialPort.isOpened()) {
			System.out.println("Comunicação Aberta");
		}
	}

	public void closeComm() {
		try {
			serialPort.closePort();
		} catch (SerialPortException e) {
			e.printStackTrace();
		}
		if (!serialPort.isOpened()) {
			System.out.println("Comunicação Encerrada");
		}
	}

	public void updatePortSettings(String portName, int baudRate, int dataBits, int stopBits, int parity) {
		try {
			serialPort.setParams(baudRate, dataBits, stopBits, parity);
			this.setPortName(portName);
			this.baudRate = baudRate;
			this.dataBits = dataBits;
			this.stopBits = stopBits;
			this.parity = parity;
		} catch (SerialPortException e2) {
			e2.printStackTrace();
		}
	}

	public void sendString(String str) {
		if (str.length() > 0) {
			try {
				serialPort.writeBytes(str.getBytes());
				System.out.println(str + " Enviado");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public synchronized void waitToWrite() throws InterruptedException {
		while (!readyToWrite) {
			wait();
		}
	}

	public synchronized void readyToWrite(boolean readyToWrite) {
		this.readyToWrite = readyToWrite;
		if (readyToWrite) {
			notifyAll();
		}
	}

	private class Reader implements SerialPortEventListener {
		private String str = "";

		@Override
		public void serialEvent(SerialPortEvent spe) {
			if (spe.isRXCHAR() || spe.isRXFLAG()) {
				if (spe.getEventValue() > 0) {
					try {
						str = "";
						byte[] buffer = serialPort.readBytes(spe.getEventValue());
						str = new String(buffer);
						pos += str.length() - str.replace("\n", "").length();
						reading += str;
						if (pos == dataSize) {
							setData(reading);
							pos = 0;
							reading = "";
							readyToWrite(true);
						}
					} catch (Exception ex) {
						System.out.println("Erro na thread de leitura");
					}
				}
			}
		}

	}

	public String getData() {
		return Data;
	}

	public void setData(String data) {
		Data = data;
	}

	public String getPortName() {
		return portName;
	}

	public void setPortName(String portName) {
		this.portName = portName;
	}

	public int getBaudRate() {
		return baudRate;
	}

	public void setBaudRate(int baudRate) {
		this.baudRate = baudRate;
	}

	public int getDataBits() {
		return dataBits;
	}

	public void setDataBits(int dataBits) {
		this.dataBits = dataBits;
	}

	public int getStopBits() {
		return stopBits;
	}

	public void setStopBits(int stopBits) {
		this.stopBits = stopBits;
	}

	public int getParity() {
		return parity;
	}

	public void setParity(int parity) {
		this.parity = parity;
	}

	public SerialPort getSerialPort() {
		return serialPort;
	}

	public void setSerialPort(SerialPort serialPort) {
		this.serialPort = serialPort;
	}

	public boolean isReadyToWrite() {
		return readyToWrite;
	}

	public void setReadyToWrite(boolean readyToWrite) {
		this.readyToWrite = readyToWrite;
	}

	public int getDataSize() {
		return dataSize;
	}

	public void setDataSize(int dataSize) {
		this.dataSize = dataSize;
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	public String getReading() {
		return reading;
	}

	public void setReading(String reading) {
		this.reading = reading;
	}

}
