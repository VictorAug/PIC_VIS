package br.iesb.VIS2048.comm;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;

// TODO: Auto-generated Javadoc
/**
 * The Class SerialComm.
 */
public class SerialComm {

    /** The port name. */
    private String portName = null;
    
    /** The baud rate. */
    private int baudRate = SerialPort.BAUDRATE_115200;
    
    /** The data bits. */
    private int dataBits = SerialPort.DATABITS_8;
    
    /** The stop bits. */
    private int stopBits = SerialPort.STOPBITS_1;
    
    /** The parity. */
    private int parity = SerialPort.PARITY_NONE;
    
    /** The serial port. */
    private SerialPort serialPort;
    
    /** The ready to write. */
    private boolean readyToWrite = false;
    
    /** The data size. */
    private int dataSize = 2048;
    
    /** The Data. */
    private String Data = "";
    
    /** The pos. */
    private int pos = 0;
    
    /** The reading. */
    private String reading = "";

    /**
     * Instancia uma nova Comunicação Serial.
     */
    public SerialComm() {
	String[] ports = SerialPortList.getPortNames();
	if (ports.length > 0) {
	    if (this.portName == null) {
		setPortName(ports[0]);
	    }
	    serialPort = new SerialPort(getPortName());
	    if (serialPort != null) {
		System.out.println("Connected to Port " + getPortName());
	    }
	} else {
	    System.out.println("Nenhuma porta dispon�vel");
	}
    }

    /**
     * Método para configurar a porta selecionada.
     *
     * @param portName: o nome da porta
     */
    public void configPort(String portName) {
	setPortName(portName);
    }

    /**
     * Abrir conexão.
     */
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
	    System.out.println("Comunica��o Aberta");
	}
    }

    /**
     * Fechar conexão.
     */
    public void closeComm() {
	try {
	    serialPort.closePort();
	} catch (SerialPortException e) {
	    e.printStackTrace();
	}
	if (!serialPort.isOpened()) {
	    System.out.println("Comunica��o Encerrada");
	}
    }

    /**
     * Update port settings.
     *
     * @param portName the port name
     * @param baudRate the baud rate
     * @param dataBits the data bits
     * @param stopBits the stop bits
     * @param parity the parity
     */
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

    /**
     * Send string.
     *
     * @param str the str
     */
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

    /**
     * Wait to write.
     *
     * @throws InterruptedException the interrupted exception
     */
    public synchronized void waitToWrite() throws InterruptedException {
	while (!readyToWrite) {
	    wait();
	}
    }

    /**
     * Ready to write.
     *
     * @param readyToWrite the ready to write
     */
    public synchronized void readyToWrite(boolean readyToWrite) {
	this.readyToWrite = readyToWrite;
	if (readyToWrite) {
	    notifyAll();
	}
    }

    /**
     * The Class Reader.
     */
    private class Reader implements SerialPortEventListener {
	
	/** The str. */
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

    /**
     * Gets the data.
     *
     * @return the data
     */
    public String getData() {
	return Data;
    }

    /**
     * Sets the data.
     *
     * @param data the new data
     */
    public void setData(String data) {
	Data = data;
    }

    /**
     * Gets the port name.
     *
     * @return the port name
     */
    public String getPortName() {
	return portName;
    }

    /**
     * Sets the port name.
     *
     * @param portName the new port name
     */
    public void setPortName(String portName) {
	this.portName = portName;
    }

    /**
     * Gets the baud rate.
     *
     * @return the baud rate
     */
    public int getBaudRate() {
	return baudRate;
    }

    /**
     * Sets the baud rate.
     *
     * @param baudRate the new baud rate
     */
    public void setBaudRate(int baudRate) {
	this.baudRate = baudRate;
    }

    /**
     * Gets the data bits.
     *
     * @return the data bits
     */
    public int getDataBits() {
	return dataBits;
    }

    /**
     * Sets the data bits.
     *
     * @param dataBits the new data bits
     */
    public void setDataBits(int dataBits) {
	this.dataBits = dataBits;
    }

    /**
     * Gets the stop bits.
     *
     * @return the stop bits
     */
    public int getStopBits() {
	return stopBits;
    }

    /**
     * Sets the stop bits.
     *
     * @param stopBits the new stop bits
     */
    public void setStopBits(int stopBits) {
	this.stopBits = stopBits;
    }

    /**
     * Gets the parity.
     *
     * @return the parity
     */
    public int getParity() {
	return parity;
    }

    /**
     * Sets the parity.
     *
     * @param parity the new parity
     */
    public void setParity(int parity) {
	this.parity = parity;
    }

    /**
     * Gets the serial port.
     *
     * @return the serial port
     */
    public SerialPort getSerialPort() {
	return serialPort;
    }

    /**
     * Sets the serial port.
     *
     * @param serialPort the new serial port
     */
    public void setSerialPort(SerialPort serialPort) {
	this.serialPort = serialPort;
    }

    /**
     * Checks if is ready to write.
     *
     * @return true, if is ready to write
     */
    public boolean isReadyToWrite() {
	return readyToWrite;
    }

    /**
     * Sets the ready to write.
     *
     * @param readyToWrite the new ready to write
     */
    public void setReadyToWrite(boolean readyToWrite) {
	this.readyToWrite = readyToWrite;
    }

    /**
     * Gets the data size.
     *
     * @return the data size
     */
    public int getDataSize() {
	return dataSize;
    }

    /**
     * Sets the data size.
     *
     * @param dataSize the new data size
     */
    public void setDataSize(int dataSize) {
	this.dataSize = dataSize;
    }

    /**
     * Gets the pos.
     *
     * @return the pos
     */
    public int getPos() {
	return pos;
    }

    /**
     * Sets the pos.
     *
     * @param pos the new pos
     */
    public void setPos(int pos) {
	this.pos = pos;
    }

    /**
     * Gets the reading.
     *
     * @return the reading
     */
    public String getReading() {
	return reading;
    }

    /**
     * Sets the reading.
     *
     * @param reading the new reading
     */
    public void setReading(String reading) {
	this.reading = reading;
    }

}
