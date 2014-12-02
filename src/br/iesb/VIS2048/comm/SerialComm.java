package br.iesb.VIS2048.comm;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.TooManyListenersException;

import javax.comm.CommPortIdentifier;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;
import javax.comm.UnsupportedCommOperationException;

public class SerialComm implements SerialPortEventListener, Runnable {
	static Enumeration<?> portList;
    static CommPortIdentifier portId;
    static String messageString = "Hello, world!\n";
    static SerialPort serialPort;
    static OutputStream outputStream;
    static InputStream inputStream;
    Thread readThread;
    
    static List<String> printSerialPorts(){
    	List<String> ports = new ArrayList<String>();
    	portList = CommPortIdentifier.getPortIdentifiers();
    	while (portList.hasMoreElements()) {
    		portId = (CommPortIdentifier) portList.nextElement();
        	ports.add(portId.getName());
    	}
		return ports;
    }
    
    static boolean sendData(String port, String Data){
    	portList = CommPortIdentifier.getPortIdentifiers();
    	while (portList.hasMoreElements()) {
        	portId = (CommPortIdentifier) portList.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
            	if (portId.getName().equals(port)) {
                    try {
                        serialPort = (SerialPort) portId.open("SimpleWriteApp", 2000);
                        outputStream = serialPort.getOutputStream();
                        serialPort.setSerialPortParams(9600,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
                        outputStream.write(Data.getBytes());
                        return true;
                    } catch (PortInUseException | IOException | UnsupportedCommOperationException e) {
                    	e.printStackTrace();
                    	return false;
                    }
                }
            }
        }
		return false;
    }

    public static void main(String[] args) {
        portList = CommPortIdentifier.getPortIdentifiers();

        while (portList.hasMoreElements()) {
            portId = (CommPortIdentifier) portList.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                if (portId.getName().equals("COM1")) {
                    SerialComm reader = new SerialComm();
                }
            }
        }
    }

    public SerialComm() {
        try {
            serialPort = (SerialPort) portId.open("SimpleReadApp", 2000);
            inputStream = serialPort.getInputStream();
            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
            serialPort.setSerialPortParams(9600,
                SerialPort.DATABITS_8,
                SerialPort.STOPBITS_1,
                SerialPort.PARITY_NONE);
            readThread = new Thread(this);
            readThread.start();
        } catch (PortInUseException | IOException | TooManyListenersException | UnsupportedCommOperationException e) {}
    }

    public void run() {
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {}
    }

    public void serialEvent(SerialPortEvent event) {
        switch(event.getEventType()) {
        case SerialPortEvent.BI:
        case SerialPortEvent.OE:
        case SerialPortEvent.FE:
        case SerialPortEvent.PE:
        case SerialPortEvent.CD:
        case SerialPortEvent.CTS:
        case SerialPortEvent.DSR:
        case SerialPortEvent.RI:
        case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
            break;
        case SerialPortEvent.DATA_AVAILABLE:
            byte[] readBuffer = new byte[20];

            try {
                while (inputStream.available() > 0) {
                    int numBytes = inputStream.read(readBuffer);
                }
                System.out.print(new String(readBuffer));
            } catch (IOException e) {}
            break;
        }
    }
    
	public static void main1(String[] args) {
		List<String> ports = printSerialPorts();
		for(String port : ports) if(port.matches("COM\\d")) System.out.println(port);
		
		if(sendData("COM1", "Hello, it's working!")) System.out.println("Data sent!");
		else System.out.println("An error occurred");
        
		//readData("COM1");
	}

}
