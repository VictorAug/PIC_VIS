package br.iesb.VIS2048.comm;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;

public class SerialComm{
	private String portName;
    private int baudRate = SerialPort.BAUDRATE_115200;
    private int dataBits = SerialPort.DATABITS_8;
    private int stopBits = SerialPort.STOPBITS_1;
    private int parity = SerialPort.PARITY_NONE;
    private SerialPort serialPort;
    private boolean readyToWrite = false;
    private int dataSize = 2048;
    //private boolean readyToCollect = false;
	public SerialComm(){
		String[] ports = SerialPortList.getPortNames();
        if(ports.length > 0){
            setPortName(ports[0]);
            serialPort = new SerialPort(getPortName());
            if(serialPort != null){
            	System.out.println("Connected to Port " + getPortName());
            }
        }else{
        	System.out.println("Nenhuma porta disponível");
        }
	}
	public boolean isOpen(){
		return this.isOpen();
	}
	public void openComm(){
		try {
			serialPort.openPort();
		} catch (SerialPortException e3) {
			e3.printStackTrace();
		}
		
		this.updatePortSettings(portName, baudRate, dataBits, stopBits, parity);
		
		try {
			serialPort.addEventListener(new Reader(), SerialPort.MASK_RXCHAR |
			        SerialPort.MASK_RXFLAG |
			        SerialPort.MASK_CTS |
			        SerialPort.MASK_DSR |
			        SerialPort.MASK_RLSD);
		} catch (SerialPortException e1) {
			e1.printStackTrace();
		}
		
        if(serialPort.isOpened()){
        	System.out.println("Comunicação Aberta");
        }
	}
	public void closeComm(){
		try {
			serialPort.closePort();
		} catch (SerialPortException e) {
			e.printStackTrace();
		}
		if(!serialPort.isOpened()){
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
        if(str.length() > 0){
            try {
            	serialPort.writeBytes(str.getBytes());
            	System.out.println(str + " Enviado");
            }
            catch (Exception ex) {
            	ex.printStackTrace();
                System.out.println("Erro ao escrever!");
            }
        }
    }
	public String getPortName() {
		return portName;
	}
	public void setPortName(String portName) {
		this.portName = portName;
	}
	
	public synchronized void waitToWrite() throws InterruptedException{
		while(!readyToWrite) wait();
	}
	public synchronized void readyToWrite(boolean readyToWrite){
		this.readyToWrite = readyToWrite;
		if(readyToWrite) notifyAll();
	}
	public String getData(){
		return this.Data;
	}
	String Data = "";
	
	private void setData(String Data){
		this.Data = Data;
	}
	private class Reader implements SerialPortEventListener {
        private String str = "";
		@Override
		public void serialEvent(SerialPortEvent spe) {
			if(spe.isRXCHAR() || spe.isRXFLAG()){
                if(spe.getEventValue() > 0){
                    try {
                        str = "";
                        byte[] buffer = serialPort.readBytes(spe.getEventValue());
                        str = new String(buffer);
                        pos += str.length() - str.replace("\n", "").length();
                        reading += str;
                        if(pos == dataSize){
                        	setData(reading);
                        	pos = 0; 
                        	reading = "";
                        	readyToWrite(true);
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

}
