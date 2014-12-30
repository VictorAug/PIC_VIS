package br.iesb.VIS2048.comm;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class SerialComm {
	private String portName = "";
    private int baudRate = 115200;
    private int dataBits = 8;
    private int stopBits = 0;
    private int parity = 0;
    private SerialPort serialPort = null;
    private int numberOfSamples = 2048;
    private boolean connected = false;
    private boolean openPort = false;
    private boolean readyToGet = false;
    
	public SerialComm(String port) {
		if(port.equals("null") || port.equals("")){
			//System.out.println("SerialComm Constructor: nome de porta Nulo");
			setConnected(false);
		}
		serialPort = new SerialPort(port);
        if(serialPort != null){
        	setPortName(port);
        	setConnected(true);
        	//System.out.println("SerialComm Constructor: Conexão em Porta " + getPortName());
        }else{
        	//System.out.println("SerialComm Constructor: Não foi possível estabelecer conexão: serialPort = null");
        	setConnected(false);
        }
	}
	
	public void OpenComm(){
		if(!isConnected()){
			//System.out.println("SerialComm OpenComm: Impossível abrir porta "+ getPortName() + ", não há conexão");
			return;
		}
		try {
			serialPort.openPort();
			setOpenPort(true);
			//System.out.println("SerialComm OpenComm: Porta " + getPortName() + " Aberta");
		} catch (SerialPortException e3) {
			e3.printStackTrace();
			setOpenPort(false);
			//System.out.println("SerialComm OpenComm: Erro ao abrir porta" + getPortName());
		}
	}
	
	public void closeComm(){
		if(serialPort != null)
		try {
			serialPort.closePort();
		} catch (SerialPortException e) {
			e.printStackTrace();
		}
	}
	
	public void OpenReading(){
		if(!isOpenPort()){
			//System.out.println("SerialComm OpenReading: Nenhuma porta aberta");
			return;
		}
		try {
			serialPort.addEventListener(new Reader(), SerialPort.MASK_RXCHAR |
			        SerialPort.MASK_RXFLAG |
			        SerialPort.MASK_CTS |
			        SerialPort.MASK_DSR |
			        SerialPort.MASK_RLSD);
			//System.out.println("SerialComm OpenReading: Evento de Leitura Registrado");
		} catch (SerialPortException e1) {
			e1.printStackTrace();
		}
	}
	
	public void updatePortSettings(int baudRate, int dataBits, int stopBits, int parity) {
		//System.out.println("SerialComm updatePortSettings:" + baudRate + " - " + dataBits + " - " + stopBits + " - " +parity);
    	try {
			serialPort.setParams(baudRate, dataBits, stopBits, parity);
	        this.baudRate = baudRate;
	        this.dataBits = dataBits;
	        this.stopBits = stopBits;
	        this.parity = parity;
	        //System.out.println("SerialComm updatePortSettings: Dados de Porta Atualizados");
		} catch (SerialPortException e2) {
			//System.out.println("SerialComm updatePortSettings: Erro na atualização dos dados");
			e2.printStackTrace();
		}
    }
	
	public void sendString(String str) {
		//System.out.println("Entrou");
        if(str.length() > 0){
            try {
            	serialPort.writeBytes(str.getBytes());
            	System.out.println("SerialComm OpenComm: " + str + " Enviado");
            }
            catch (Exception ex) {
            	ex.printStackTrace();
                System.out.println("SerialComm OpenComm: Erro ao escrever: " + str);
            }
        }
    }
	public synchronized void waitToGet() throws InterruptedException{
		while(!readyToGet) wait();
	}
	public synchronized void readyToGet(boolean readyToGet){
		this.readyToGet = readyToGet;
		if(readyToGet) notifyAll();
	}
	
	public String getData(){
		return this.Data;
	}
	private void setData(String Data){
		this.Data = Data;
	}
	private String Data = "";

	private class Reader implements SerialPortEventListener {
        private String str = "";
		@Override
		public void serialEvent(SerialPortEvent spe) {
			if(spe.isRXCHAR() || spe.isRXFLAG()){
                if(spe.getEventValue() > 0){
                	//System.out.println("Event");
                    try {
                        str = "";
                        byte[] buffer = serialPort.readBytes(spe.getEventValue());
                        str = new String(buffer);
                        pos += str.length() - str.replace("\n", "").length();
                        reading += str;
                        if(pos == numberOfSamples){
                        	setData(reading);
                        	//System.out.println("Posicao: " + pos);
                        	pos = 0; 
                        	reading = "";
                        	readyToGet(true);
                        }
                    }
                    catch (Exception ex) {
                    	System.out.println("Erro na thread de leitura");
                    }
                }
            }
		}
		private int pos = 0;
		String reading = "";
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

	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	public int getNumberOfSamples() {
		return numberOfSamples;
	}

	public void setNumberOfSamples(int numberOfSamples) {
		this.numberOfSamples = numberOfSamples;
	}

	public boolean isOpenPort() {
		return openPort;
	}

	public void setOpenPort(boolean openPort) {
		this.openPort = openPort;
	}

}
