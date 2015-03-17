package br.iesb.VIS2048.comm;

import java.text.SimpleDateFormat;
import java.util.Date;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

import org.jfree.data.xy.XYSeries;

import br.iesb.VIS2048.frame.Chart;

/**
 * Class Harvester.
 */
public class Harvester {
    /** Nome da porta. */
    private String portName = "";

    /** Número de bits/s. */
    private int baudRate = 330000/*1000000*/;

    /** Capacidade de transferir 8-bits de uma vez. */
    private int dataBits = 8;

    /** Indica o fim da palavra. */
    private int stopBits = 0;

    /** Indica erro. */
    private int parity = 0;

    /** Número de amostras. */
    private int numberOfSamples = 2048;

    /** Atributo data. */
    private String data = "";

    /** Atributo serial port. */
    private SerialPort serialPort = null;

    /** Atributo connected. */
    private boolean connected = false;

    /** Atributo ready to get. */
    private boolean readyToGet = false;

    /** Atributo launch. */
    private Thread launch;

    /**
     * Instancia um novo harvester.
     */
    public Harvester() {
    }

    // ////////////////////////////
    // //Communications Methods////
    // ////////////////////////////
    /**
     * Open comm.
     */
    private void openComm() {
	// if (!isConnected()) {
	// System.out.println("SerialComm OpenComm: Imposs�vel abrir porta "+
	// getPortName() + ", n�o h� conex�o");
	// return;
	// }
	try {
	    serialPort.openPort();
	    // System.out.println("SerialComm OpenComm: Porta " + getPortName()
	    // + " Aberta");
	} catch (SerialPortException e3) {
	    e3.printStackTrace();
	    // System.out.println("SerialComm OpenComm: Erro ao abrir porta" +
	    // getPortName());
	}
    }

    /**
     * Close comm.
     */
    public void closeComm() {
	if (serialPort != null) {
	    try {
		serialPort.closePort();
	    } catch (SerialPortException e) {
		e.printStackTrace();
	    }
	}
    }

    /**
     * Open reading.
     */
    private void openReading() {
	if (!serialPort.isOpened()) {
	    // System.out.println("SerialComm OpenReading: Nenhuma porta aberta");
	    return;
	}
	try {
	    serialPort.addEventListener(new Reader(), SerialPort.MASK_RXCHAR | SerialPort.MASK_RXFLAG | SerialPort.MASK_CTS | SerialPort.MASK_DSR | SerialPort.MASK_RLSD);
	    // System.out.println("SerialComm OpenReading: Evento de Leitura Registrado");
	} catch (SerialPortException e1) {
	    e1.printStackTrace();
	}
    }

    /**
     * Update port settings.
     *
     * @param baudRate
     *            the baud rate
     * @param dataBits
     *            the data bits
     * @param stopBits
     *            the stop bits
     * @param parity
     *            the parity
     */
    public void updatePortSettings(int baudRate, int dataBits, int stopBits, int parity) {
	if (!isSerialPortNull()) {
	    this.baudRate = baudRate;
	    this.dataBits = dataBits;
	    this.stopBits = stopBits;
	    this.parity = parity;
	    setParams(baudRate, dataBits, stopBits, parity);
	}
    }

    /**
     * Verifica se nenhum parâmetro foi alterado.
     *
     * @param baudRate2
     *            the baud rate2
     * @param dataBits2
     *            the data bits2
     * @param stopBits2
     *            the stop bits2
     * @param parity2
     *            the parity2
     * @return true, se bem-sucedido
     */
    public boolean verifyParameters(int baudRate2, int dataBits2, int stopBits2, int parity2) {
	return this.baudRate == baudRate2 && this.dataBits == dataBits2 && this.stopBits == stopBits2 && this.parity == parity2;
    }

    /**
     * Configuração inicial dos parâmetros da porta serial.
     *
     * @param baudRate
     *            the baud rate
     * @param dataBits
     *            the data bits
     * @param stopBits
     *            the stop bits
     * @param parity
     *            the parity
     */
    private void setParams(int baudRate, int dataBits, int stopBits, int parity) {
	// System.out.println("SerialComm updatePortSettings:" + baudRate +
	// " - " + dataBits + " - " + stopBits + " - " +parity);
	try {
	    serialPort.setParams(baudRate, dataBits, stopBits, parity);
	} catch (SerialPortException e2) {
	    e2.printStackTrace();
	}
    }

    /**
     * Send string.
     *
     * @param str
     *            the str
     */
    public void sendString(String str) {
	if (!str.isEmpty()) {
	    try {
		serialPort.writeBytes(str.getBytes());
		System.out.println("SerialComm OpenComm: " + str + " Enviado");
	    } catch (Exception ex) {
		ex.printStackTrace();
		System.out.println("SerialComm OpenComm: Erro ao escrever: " + str);
	    }
	}
    }

    /**
     * Launch.
     */
    public void launch() {
	launch = new Thread(new Harvest(), "Spectrometer");
	launch.setDaemon(true);
	launch.start();
    }

    /**
     * Try connection.
     *
     * @param port
     *            the port
     * @return true, se bem-sucedido
     */
    public boolean tryConnection(String port) {
	this.portName = port;
	if ("null".equals(portName) || "".equals(portName)) {
	    // System.out.println("SerialComm Constructor: nome de porta Nulo");
	    setConnected(false);
	}
	serialPort = new SerialPort(portName);
	if (!isSerialPortNull()) {
	    setPortName(portName);
	    setConnected(true);
	    // System.out.println("SerialComm Constructor: Conex�o em Porta " +
	    // getPortName());
	} else {
	    // System.out.println("SerialComm Constructor: N�o foi poss�vel estabelecer conex�o: serialPort = null");
	    setConnected(false);
	}
	// setDevice(new SerialComm(this.port));
	if (isConnected()) {
	    openComm();
	    openReading();
	    setParams(baudRate, dataBits, stopBits, parity);
	    readyToGet(true);
	    try {
		Thread.sleep(2000);
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	    // launch();
	    return true;
	}
	return false;
    }

    // ///////////////////////////
    // //Harvesting Chart Data////
    // ///////////////////////////
    /**
     * Class Harvest.
     */
    private class Harvest implements Runnable { // N�o foi implementado ainda
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
	    while (true) {
	    }
	}
    }

    /** Atributo leitura. */
    Double[] leitura = null;

    /** Atributo data set. */
    String dataSet = null;

    /**
     * Retorna dataset.
     *
     * @param str
     *            the str
     * @return dataset
     */
    public Chart getDataset(String str) {
	String buffer = "";
	readyToGet(false);
	double time = System.nanoTime();
	sendString(str);
	long timeStamp = 0;
	XYSeries series = null;
	try {
	    waitToGet();
	    dataSet = new String(data);
	    leitura = new Double[numberOfSamples];
	    int i = 0;
	    char[] charArray = dataSet.toCharArray();
	    timeStamp = System.currentTimeMillis();
	    series = new XYSeries(new SimpleDateFormat("HH'h' mm'min' ss,SSS's'").format(new Date(timeStamp)));
	    for (char c : charArray) {
		if (c != 13 && c != 10) {
		    buffer += c;
		} else if (!buffer.isEmpty()) {
		    leitura[i] = Double.parseDouble(buffer);
		    series.add(i, leitura[i]);
		    buffer = "";
		    i++;
		}
	    }
	    time = System.nanoTime() - time;
	    System.out.println("Full Harvesting Time: " + time / 1000000.0 + " elapsed");
	    Chart chart = new Chart(/* leitura, */"", "", numberOfSamples, timeStamp, series);

	    chart.setXyseries(series);
	    return chart;
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}

	return null;
    }

    /**
     * Wait to get.
     *
     * @throws InterruptedException
     *             the interrupted exception
     */
    public synchronized void waitToGet() throws InterruptedException {
	while (!readyToGet) {
	    wait();
	}
    }

    /**
     * Ready to get.
     *
     * @param readyToGet
     *            the ready to get
     */
    public synchronized void readyToGet(boolean readyToGet) {
	this.readyToGet = readyToGet;
	if (readyToGet) {
	    notifyAll();
	}
    }

    /**
     * Class Reader.
     */
    private class Reader implements SerialPortEventListener {

	/** Atributo str. */
	private String str = "";
	// double readingTime = 0;
	// double spentTime = 0;
	/** Atributo buffer. */
	byte[] buffer = null;

	/** Atributo pos. */
	private int pos = 0;

	/** Atributo reading. */
	String reading = "";

	/*
	 * (non-Javadoc)
	 * 
	 * @see jssc.SerialPortEventListener#serialEvent(jssc.SerialPortEvent)
	 */
	@Override
	public void serialEvent(SerialPortEvent portEvent) {
	    // RXCHAR: bytes count in input buffer
	    // RXFLAG: bytes count in input buffer (non-Linux)
	    if (portEvent.isRXCHAR() || portEvent.isRXFLAG()) {
		if (portEvent.getEventValue() > 0) {
		    // spentTime += (System.nanoTime()-readingTime);
		    try {
			// System.out.println(serialPort.getInputBufferBytesCount());
			buffer = serialPort.readBytes(portEvent.getEventValue());
			str = new String(buffer);
			pos += str.length() - str.replace("\n", "").length();
			reading += str;
			if (pos == numberOfSamples) {
			    setData(reading);
			    pos = 0;
			    reading = "";
			    // System.out.println("Spent Time: " +
			    // (spentTime)/1000000.0);
			    // spentTime = 0;
			    readyToGet(true);
			}
			// readingTime = System.nanoTime();
		    } catch (Exception ex) {
			System.out.println("Erro na thread de leitura");
		    }
		}
	    }
	}

    }

    public boolean isSerialPortNull() {
	return serialPort == null;
    }

    // ///////////////////////////////
    // //Variable Handling Methods////
    // ///////////////////////////////
    
    /**
     * Retorna data.
     *
     * @return data
     */
    public String getData() {
	return data;
    }
    
    /**
     * Atribui o valor data.
     *
     * @param data
     *            novo data
     */
    private void setData(String data) {
	if (data == null) {
	    this.data = null;
	} else {
	    this.data = new String(data);
	}
    }

    /**
     * Atribui o valor port name.
     *
     * @param portName
     *            novo port name
     */
    public void setPortName(String portName) {
	if (portName == null) {
	    this.portName = null;
	} else {
	    this.portName = new String(portName);
	}
    }

    /**
     * Verifica se é connected.
     *
     * @return true, se é connected
     */
    public boolean isConnected() {
	return connected;
    }

    /**
     * Atribui o valor connected.
     *
     * @param connected
     *            novo connected
     */
    public void setConnected(boolean connected) {
	this.connected = connected;
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

    public int getNumberOfSamples() {
	return numberOfSamples;
    }

    public void setNumberOfSamples(int numberOfSamples) {
	this.numberOfSamples = numberOfSamples;
    }

}
