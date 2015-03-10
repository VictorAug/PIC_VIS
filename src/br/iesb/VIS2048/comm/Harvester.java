package br.iesb.VIS2048.comm;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

import org.jfree.data.xy.XYSeries;

import br.iesb.VIS2048.frame.Chart;

public class Harvester {
    // //Port Settings////
    private String portName = "";
    private int baudRate = 1000000;
    private int dataBits = 8;
    private int stopBits = 0;
    private int parity = 0;
    // ///////////////////

    // //Protocol////
    private int numberOfSamples = 2048;
    private String data = "";
    // //////////////

    // //Vari�veis de Estado////
    private SerialPort serialPort = null;
    private boolean connected = false;
    private boolean openPort = false;
    private boolean readyToGet = false;
    // /////////////////////////

    private Thread launch;

    public Harvester() {
    }

    // ////////////////////////////
    // //Communications Methods////
    // ////////////////////////////
    private void openComm() {
	if (!isConnected()) {
	    // System.out.println("SerialComm OpenComm: Imposs�vel abrir porta "+
	    // getPortName() + ", n�o h� conex�o");
	    return;
	}
	try {
	    serialPort.openPort();
	    openPort = true;
	    // System.out.println("SerialComm OpenComm: Porta " + getPortName()
	    // + " Aberta");
	} catch (SerialPortException e3) {
	    e3.printStackTrace();
	    openPort = false;
	    // System.out.println("SerialComm OpenComm: Erro ao abrir porta" +
	    // getPortName());
	}
    }

    public void closeComm() {
	if (serialPort != null) {
	    try {
		serialPort.closePort();
	    } catch (SerialPortException e) {
		e.printStackTrace();
	    } 
	}
    }

    private void openReading() {
	if (!openPort) {
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

    public void updatePortSettings(int baudRate, int dataBits, int stopBits, int parity) {
	// System.out.println("SerialComm updatePortSettings:" + baudRate +
	// " - " + dataBits + " - " + stopBits + " - " +parity);
	try {
	    serialPort.setParams(baudRate, dataBits, stopBits, parity);
	    this.baudRate = baudRate;
	    this.dataBits = dataBits;
	    this.stopBits = stopBits;
	    this.parity = parity;
	    // System.out.println("SerialComm updatePortSettings: Dados de Porta Atualizados");
	} catch (SerialPortException e2) {
	    // System.out.println("SerialComm updatePortSettings: Erro na atualiza��o dos dados");
	    e2.printStackTrace();
	}
    }

    public void sendString(String str) {
	// System.out.println("Entrou");
	if (str.length() > 0) {
	    try {
		serialPort.writeBytes(str.getBytes());
		System.out.println("SerialComm OpenComm: " + str + " Enviado");
	    } catch (Exception ex) {
		ex.printStackTrace();
		System.out.println("SerialComm OpenComm: Erro ao escrever: " + str);
	    }
	}
    }

    public void launch() {
	launch = new Thread(new Harvest(), "Spectrometer");
	launch.setDaemon(true);
	launch.start();
    }

    public boolean tryConnection(String port) {
	this.portName = port;
	if ("null".equals(portName) || "".equals(portName)) {
	    // System.out.println("SerialComm Constructor: nome de porta Nulo");
	    setConnected(false);
	}
	serialPort = new SerialPort(portName);
	if (serialPort != null) {
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
	    updatePortSettings(baudRate, dataBits, stopBits, parity);
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
    private class Harvest implements Runnable { // N�o foi implementado ainda
	@Override
	public void run() {
	    while (true) {
	    }
	}
    }

    Double[] leitura = null;
    String dataSet = null;

    public Chart getDataset(String str) {
	String buffer = "";
	readyToGet(false);
	double time = System.nanoTime();
	sendString(str);
	long timestamp = 0;
	XYSeries series = null;
	try {
	    waitToGet();
	    dataSet = new String(data);
	    // System.out.println("Reading Time: "+
	    // (System.nanoTime()-time)/1000000.0 + " elapsed");
	    // time = System.nanoTime();
	    leitura = new Double[numberOfSamples];
	    int i = 0;
	    char[] charArray = dataSet.toCharArray();
	    timestamp = System.currentTimeMillis();
	    series = new XYSeries(timestamp);
	    for (char Char : charArray) {
		if (Char != 13 && Char != 10) {
		    buffer += Char;
		} else if (buffer != "") {
		    leitura[i] = Double.parseDouble(buffer);
		    series.add(i, leitura[i]);
		    buffer = "";
		    i++;
		}
	    }
	    time = System.nanoTime() - time;
	    System.out.println("Full Harvesting Time: " + time / 1000000.0 + " elapsed");
	    Chart chart = new Chart(/* leitura, */"", "", numberOfSamples, timestamp, series);

	    chart.setXyseries(series);
	    return chart;
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}

	return null;
    }

    public synchronized void waitToGet() throws InterruptedException {
	while (!readyToGet) {
	    wait();
	}
    }

    public synchronized void readyToGet(boolean readyToGet) {
	this.readyToGet = readyToGet;
	if (readyToGet) {
	    notifyAll();
	}
    }

    private class Reader implements SerialPortEventListener {
	private String str = "";
	// double readingTime = 0;
	// double spentTime = 0;
	byte[] buffer = null;

	@Override
	public void serialEvent(SerialPortEvent spe) {
	    if (spe.isRXCHAR() || spe.isRXFLAG()) {
		if (spe.getEventValue() > 0) {
		    // spentTime += (System.nanoTime()-readingTime);
		    try {
			str = "";
			// System.out.println(serialPort.getInputBufferBytesCount());
			buffer = serialPort.readBytes(spe.getEventValue());
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

	private int pos = 0;
	String reading = "";
    }

    // ///////////////////////////////
    // //Variable Handling Methods////
    // ///////////////////////////////
    private void setData(String data) {
	if (data == null) {
	    this.data = null;
	} else {
	    this.data = new String(data);
	}
    }

    public void setPortName(String portName) {
	if (portName == null) { 
	    this.portName = null;
	} else {
	    this.portName = new String(portName);
	}
    }

    public boolean isConnected() {
	return connected;
    }

    public void setConnected(boolean connected) {
	this.connected = connected;
    }

}
