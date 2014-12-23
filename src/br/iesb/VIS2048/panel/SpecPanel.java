package br.iesb.VIS2048.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import br.iesb.VIS2048.comm.SerialComm;
import br.iesb.VIS2048.database.FileDataBase;

// TODO: Auto-generated Javadoc
/**
 * The Class SpecPanel.
 */
public class SpecPanel extends JPanel {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The data size. */
    private final int dataSize = 2048;

    /** The baud rate. */
    private static int baudRate = 115200;

    /** The data bits. */
    private static int dataBits = 8;

    /** The stop bits. */
    private static int stopBits = 0;

    /** The parity. */
    private static int parity = 0;

    /** The get. */
    private boolean get = false;

    /** The read once. */
    private boolean readOnce = false;

    /** The time. */
    private double time = System.nanoTime();

    /** The image. */
    private String image = "Counts";

    /** The port name. */
    private String portName;

    /** The str. */
    private String str = "+";

    /** The buffer. */
    private String buffer = "";

    /** The R spec. */
    private Thread RSpec = new Thread(new updateChart(), "Spectrometer");

    /** The series. */
    private XYSeries series;

    /** The dataset. */
    private XYSeriesCollection dataset;

    /** The chart. */
    private JFreeChart chart;

    /** The panel. */
    private ChartPanel panel;

    /** The device. */
    private SerialComm device = new SerialComm();

    /** The counts. */
    private NumberAxis counts;

    /**
     * Instantiates a new spec panel.
     */
    public SpecPanel() {
	this.RSpec.setDaemon(true);
	this.RSpec.start();
	if (this.device != null) {
	    this.device.openComm();
	    this.device.updatePortSettings(device.getPortName(), baudRate, dataBits, stopBits, parity);
	    this.device.readyToWrite(true);
	}
    }

    /**
     * Check if get.
     *
     * @throws InterruptedException
     *             the interrupted exception
     */
    private synchronized void checkIfGet() throws InterruptedException {
	while (!get) {
	    wait();
	}
    }

    /**
     * Gets the spec.
     *
     * @param get
     *            the get
     * @return the spec
     */
    public synchronized void getSpec(boolean get) {
	this.get = get;
	if (this.get) {
	    notifyAll();
	}
    }

    /**
     * The Class updateChart.
     */
    private class updateChart implements Runnable {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
	    try {
		while (true) {
		    checkIfGet();
		    reChart();
		    if (readOnce) {
			getSpec(false);
		    }
		}
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	}

    }

    /**
     * Re chart.
     *
     * @throws InterruptedException
     *             the interrupted exception
     */
    public void reChart() throws InterruptedException {
	this.removeAll();
	System.out.println("Update in Progress");
	instDataset();
	setDataset();
	this.chart = ChartFactory.createXYLineChart("", "Comprimento de Onda", image, dataset,
		PlotOrientation.VERTICAL, true, true, false);

	this.counts = (NumberAxis) ((XYPlot) this.chart.getPlot()).getRangeAxis();

	this.panel = new ChartPanel(chart);
	this.panel.setPreferredSize(new Dimension(600, 300));

	((XYPlot) this.chart.getPlot()).setBackgroundPaint(new Color(0, 0, 51));
	// ((XYPlot) chart.getPlot()).setBackgroundAlpha(0.5f);
	((XYPlot) this.chart.getPlot()).setRangeGridlinePaint(Color.white);

	// Plot plot = chart.getPlot();
	// GradientPaint gradientPaint = new GradientPaint(0.0F, 10.0F,
	// Color.WHITE, 1000, 1000, Color.green.darker());
	// plot.setBackgroundPaint(gradientPaint);

	this.setLayout(new BorderLayout());
	this.add(panel, BorderLayout.CENTER);
	this.revalidate();
	Thread.sleep(50);
    }

    /**
     * Sets the count range.
     *
     * @param lower
     *            the lower
     * @param upper
     *            the upper
     */
    public void setCountRange(Double lower, Double upper) {
	counts.setRange(lower, upper);
    }

    /**
     * Inst dataset.
     */
    public void instDataset() {
	series = new XYSeries("Espectro A");
	dataset = new XYSeriesCollection();
    }

    /**
     * Toggle read once.
     *
     * @param readOnce
     *            the read once
     */
    public void toggleReadOnce(boolean readOnce) {
	this.readOnce = readOnce;
    }

    /**
     * Sets the dataset.
     */
    private void setDataset() {
	this.device.sendString(str);
	this.device.readyToWrite(false);
	this.time = System.nanoTime();
	try {
	    this.device.waitToWrite();
	    String data = this.device.getData();
	    Double[] leitura = new Double[dataSize];
	    int i = 0;
	    char[] charArray = data.toCharArray();
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
	    FileDataBase.logVector(leitura);
	    time = System.nanoTime() - time;
	    System.out.println(time + " elapsed");
	    dataset.addSeries(series);
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
    }

    /**
     * Checks if is gets the.
     *
     * @return true, if is gets the
     */
    public boolean isGet() {
	return get;
    }

    /**
     * Sets the gets the.
     *
     * @param get
     *            the new gets the
     */
    public void setGet(boolean get) {
	this.get = get;
    }

    /**
     * Gets the r spec.
     *
     * @return the r spec
     */
    public Thread getRSpec() {
	return RSpec;
    }

    /**
     * Sets the r spec.
     *
     * @param rSpec
     *            the new r spec
     */
    public void setRSpec(Thread rSpec) {
	RSpec = rSpec;
    }

    /**
     * Gets the series.
     *
     * @return the series
     */
    public XYSeries getSeries() {
	return series;
    }

    /**
     * Sets the series.
     *
     * @param series
     *            the new series
     */
    public void setSeries(XYSeries series) {
	this.series = series;
    }

    /**
     * Gets the dataset.
     *
     * @return the dataset
     */
    public XYSeriesCollection getDataset() {
	return dataset;
    }

    /**
     * Sets the dataset.
     *
     * @param dataset
     *            the new dataset
     */
    public void setDataset(XYSeriesCollection dataset) {
	this.dataset = dataset;
    }

    /**
     * Gets the chart.
     *
     * @return the chart
     */
    public JFreeChart getChart() {
	return chart;
    }

    /**
     * Sets the chart.
     *
     * @param chart
     *            the new chart
     */
    public void setChart(JFreeChart chart) {
	this.chart = chart;
    }

    /**
     * Gets the panel.
     *
     * @return the panel
     */
    public ChartPanel getPanel() {
	return panel;
    }

    /**
     * Sets the panel.
     *
     * @param panel
     *            the new panel
     */
    public void setPanel(ChartPanel panel) {
	this.panel = panel;
    }

    /**
     * Gets the device.
     *
     * @return the device
     */
    public SerialComm getDevice() {
	return device;
    }

    /**
     * Sets the device.
     *
     * @param device
     *            the new device
     */
    public void setDevice(SerialComm device) {
	this.device = device;
    }

    /**
     * Gets the counts.
     *
     * @return the counts
     */
    public NumberAxis getCounts() {
	return counts;
    }

    /**
     * Sets the counts.
     *
     * @param counts
     *            the new counts
     */
    public void setCounts(NumberAxis counts) {
	this.counts = counts;
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
     * @param portName
     *            the new port name
     */
    public void setPortName(String portName) {
	this.portName = portName;
    }

    /**
     * Gets the baud rate.
     *
     * @return the baud rate
     */
    public static int getBaudRate() {
	return baudRate;
    }

    /**
     * Sets the baud rate.
     *
     * @param baudRate
     *            the new baud rate
     */
    public static void setBaudRate(int baudRate) {
	SpecPanel.baudRate = baudRate;
    }

    /**
     * Gets the data bits.
     *
     * @return the data bits
     */
    public static int getDataBits() {
	return dataBits;
    }

    /**
     * Sets the data bits.
     *
     * @param dataBits
     *            the new data bits
     */
    public static void setDataBits(int dataBits) {
	SpecPanel.dataBits = dataBits;
    }

    /**
     * Gets the stop bits.
     *
     * @return the stop bits
     */
    public static int getStopBits() {
	return stopBits;
    }

    /**
     * Sets the stop bits.
     *
     * @param stopBits
     *            the new stop bits
     */
    public static void setStopBits(int stopBits) {
	SpecPanel.stopBits = stopBits;
    }

    /**
     * Gets the parity.
     *
     * @return the parity
     */
    public static int getParity() {
	return parity;
    }

    /**
     * Sets the parity.
     *
     * @param parity
     *            the new parity
     */
    public static void setParity(int parity) {
	SpecPanel.parity = parity;
    }

    /**
     * Gets the time.
     *
     * @return the time
     */
    public double getTime() {
	return time;
    }

    /**
     * Sets the time.
     *
     * @param time
     *            the new time
     */
    public void setTime(double time) {
	this.time = time;
    }

    /**
     * Gets the str.
     *
     * @return the str
     */
    public String getStr() {
	return str;
    }

    /**
     * Sets the str.
     *
     * @param str
     *            the new str
     */
    public void setStr(String str) {
	this.str = str;
    }

    /**
     * Gets the buffer.
     *
     * @return the buffer
     */
    public String getBuffer() {
	return buffer;
    }

    /**
     * Sets the buffer.
     *
     * @param buffer
     *            the new buffer
     */
    public void setBuffer(String buffer) {
	this.buffer = buffer;
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
     * Gets the image.
     *
     * @return the image
     */
    public String getImage() {
	return image;
    }

    /**
     * Sets the read once.
     *
     * @param readOnce
     *            the new read once
     */
    public void setReadOnce(boolean readOnce) {
	this.readOnce = readOnce;
    }

    /**
     * Sets the image.
     *
     * @param Image
     *            the new image
     */
    public void setImage(String Image) {
	this.image = Image;
    }

    /**
     * Checks if is read once.
     *
     * @return true, if is read once
     */
    public boolean isReadOnce() {
	return this.readOnce;
    }

    // private void sampleDataset(){
    // series.add(375, 220+(Math.random()-0.5)*300);
    // series.add(400, 280+(Math.random()-0.5)*300);
    // series.add(416, 342+(Math.random()-0.5)*300);
    // series.add(425, 500+(Math.random()-0.5)*300);
    // series.add(450, 2736+(Math.random()-0.5)*300);
    // series.add(475, 340+(Math.random()-0.5)*300);
    // series.add(500, 1026+(Math.random()-0.5)*300);
    // series.add(525, 3800+(Math.random()-0.5)*300);
    // series.add(550, 1520+(Math.random()-0.5)*300);
    // series.add(575, 1150+(Math.random()-0.5)*300);
    // series.add(600, 342+(Math.random()-0.5)*300);
    // series.add(625, 338+(Math.random()-0.5)*300);
    // series.add(650, 337+(Math.random()-0.5)*300);
    // series.add(675, 336+(Math.random()-0.5)*300);
    // series.add(700, 336+(Math.random()-0.5)*300);
    // series.add(725, 336+(Math.random()-0.5)*300);
    // series.add(750, 336+(Math.random()-0.5)*300);
    // series.add(775, 336+(Math.random()-0.5)*300);
    // series.add(800, 336+(Math.random()-0.5)*300);
    // series.add(825, 336+(Math.random()-0.5)*300);
    // dataset.addSeries(series);
    // }
}
