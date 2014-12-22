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

public class SpecPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private final int dataSize = 2048;

	private static int baudRate = 115200;
	private static int dataBits = 8;
	private static int stopBits = 0;
	private static int parity = 0;

	private boolean get = false;
	private boolean readOnce = false;
	private double time = System.nanoTime();

	private String image = "Counts";
	private String portName;
	private String str = "+";
	private String buffer = "";

	private Thread RSpec = new Thread(new updateChart(), "Spectrometer");
	private XYSeries series;
	private XYSeriesCollection dataset;
	private JFreeChart chart;
	private ChartPanel panel;
	private SerialComm device = null;
	private NumberAxis counts;

	public SpecPanel() {
		this.RSpec.setDaemon(true);
		this.RSpec.start();
		this.device.openComm();
		this.device.updatePortSettings(device.getPortName(), baudRate, dataBits, stopBits, parity);
		this.device.readyToWrite(true);
	}

	private synchronized void checkIfGet() throws InterruptedException {
		while (!get) {
			wait();
		}
	}

	public synchronized void getSpec(boolean get) {
		this.get = get;
		if (this.get) {
			notifyAll();
		}
	}

	private class updateChart implements Runnable {
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

	public void setCountRange(Double lower, Double upper) {
		counts.setRange(lower, upper);
	}

	public void instDataset() {
		series = new XYSeries("Espectro A");
		dataset = new XYSeriesCollection();
	}

	public void toggleReadOnce(boolean readOnce) {
		this.readOnce = readOnce;
	}

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

	public boolean isGet() {
		return get;
	}

	public void setGet(boolean get) {
		this.get = get;
	}

	public Thread getRSpec() {
		return RSpec;
	}

	public void setRSpec(Thread rSpec) {
		RSpec = rSpec;
	}

	public XYSeries getSeries() {
		return series;
	}

	public void setSeries(XYSeries series) {
		this.series = series;
	}

	public XYSeriesCollection getDataset() {
		return dataset;
	}

	public void setDataset(XYSeriesCollection dataset) {
		this.dataset = dataset;
	}

	public JFreeChart getChart() {
		return chart;
	}

	public void setChart(JFreeChart chart) {
		this.chart = chart;
	}

	public ChartPanel getPanel() {
		return panel;
	}

	public void setPanel(ChartPanel panel) {
		this.panel = panel;
	}

	public SerialComm getDevice() {
		return device;
	}

	public void setDevice(SerialComm device) {
		this.device = device;
	}

	public NumberAxis getCounts() {
		return counts;
	}

	public void setCounts(NumberAxis counts) {
		this.counts = counts;
	}

	public String getPortName() {
		return portName;
	}

	public void setPortName(String portName) {
		this.portName = portName;
	}

	public static int getBaudRate() {
		return baudRate;
	}

	public static void setBaudRate(int baudRate) {
		SpecPanel.baudRate = baudRate;
	}

	public static int getDataBits() {
		return dataBits;
	}

	public static void setDataBits(int dataBits) {
		SpecPanel.dataBits = dataBits;
	}

	public static int getStopBits() {
		return stopBits;
	}

	public static void setStopBits(int stopBits) {
		SpecPanel.stopBits = stopBits;
	}

	public static int getParity() {
		return parity;
	}

	public static void setParity(int parity) {
		SpecPanel.parity = parity;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public String getBuffer() {
		return buffer;
	}

	public void setBuffer(String buffer) {
		this.buffer = buffer;
	}

	public int getDataSize() {
		return dataSize;
	}

	public String getImage() {
		return image;
	}

	public void setReadOnce(boolean readOnce) {
		this.readOnce = readOnce;
	}

	public void setImage(String Image) {
		this.image = Image;
	}

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
