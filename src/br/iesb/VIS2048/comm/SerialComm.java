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

public class SerialComm{
	static Enumeration<?> portList;
    static CommPortIdentifier portId;
    static SerialPort serialPort;
    public static OutputStream outputStream;
    static InputStream inputStream;
    Thread readThread;
    SerialWrite reader=null;
    public SerialComm(String port){
        portList = CommPortIdentifier.getPortIdentifiers();

        while (portList.hasMoreElements()) {
            portId = (CommPortIdentifier) portList.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                if (portId.getName().equals(port)) {
                    reader = new SerialWrite();
                }
            }
        }
    }
    public boolean isDataReady(){
    	return reader.ready;
    }
    public void clearData(){
    	reader.ready = false;
    }
    public Double[] dados(){
    	return reader.leitura;
    }
    public class SerialWrite implements SerialPortEventListener, Runnable {
    	public SerialWrite(){
    		try {
	            serialPort = (SerialPort) portId.open("SimpleReadApp", 2000);
	            inputStream = serialPort.getInputStream();
	            outputStream = serialPort.getOutputStream();
	            serialPort.addEventListener(this);
	            serialPort.notifyOnDataAvailable(true);
	            serialPort.setSerialPortParams(9600,
	                SerialPort.DATABITS_8,
	                SerialPort.STOPBITS_1,
	                SerialPort.PARITY_NONE);
	            readThread = new Thread(this);
	            readThread.start();
	        } catch (PortInUseException | IOException | TooManyListenersException | UnsupportedCommOperationException e) {
	        	e.printStackTrace();
	        }
    	}
    	public void run() {
	        try {
	            Thread.sleep(20000);
	        } catch (InterruptedException e) {}
	    }

	    public void serialEvent(SerialPortEvent event) {
	        switch(event.getEventType()) {
		        case SerialPortEvent.BI:
		        	System.out.println("BI");
		        	break;
		        case SerialPortEvent.OE:
		        	System.out.println("OE");
		        	break;
		        case SerialPortEvent.FE:
		        	System.out.println("FE");
		        	break;
		        case SerialPortEvent.PE:
		        	System.out.println("PE");
		        	break;
		        case SerialPortEvent.CD:
		        	System.out.println("CD");
		        	break;
		        case SerialPortEvent.CTS:
		        	System.out.println("CTS");
		        	break;
		        case SerialPortEvent.DSR:
		        	System.out.println("DSR");
		        	break;
		        case SerialPortEvent.RI:
		        	System.out.println("RI");
		        	break;
		        case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
		        	System.out.println("OBR");
		            break;
		        case SerialPortEvent.DATA_AVAILABLE:
		            byte[] readBuffer = new byte[2048];
		
		            try {
		                while (inputStream.available() > 0) {
		                    int numBytes = inputStream.read(readBuffer);
		                    //System.out.println(new String(readBuffer));
		                }
		                
		                if(pos == 2047){
		                	pos = 0; ready = true; 
		                }else pos++;
		                //System.out.println("posicao = "+pos);
		                
		            } catch (IOException e) {}
		            break;
	        }
	    }
	    public boolean isReady(){
	    	return ready;
	    }
	    Double[] leitura = new Double[2048];
	    int pos = 0;
	    boolean ready = false;
    }
}
