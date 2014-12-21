package br.iesb.VIS2048.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import br.iesb.VIS2048.comm.SerialComm;
import br.iesb.VIS2048.database.FileDataBase;


public class SpecPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private boolean get = false;
	private boolean readOnce = false;
	private Thread RSpec = new Thread(new updateChart(), "Spectrometer");
	private XYSeries series;
	private XYSeriesCollection dataset;
	private JFreeChart chart;
	private ChartPanel panel;
	private String Image = "Counts";
	SerialComm Device = null;
	String portName;
	static int baudRate = 115200;
	static int dataBits = 8;
	static int stopBits = 0;
	static int parity = 0;
	double time = System.nanoTime();
	String str = "+";
	String buffer = "";
	public int dataSize = 2048;
	
	public void reloadTitle(){
		
	}
	
	public void setImage(String Image){
		this.Image = Image;
	}
	
	public boolean isReadOnce(){
		return this.readOnce;
	}
	
	public SpecPanel(){
		RSpec.setDaemon(true);
		RSpec.start();
		Device = new SerialComm();
		Device.openComm();
		//if(Device.isOpen()) {
			Device.updatePortSettings(Device.getPortName(), baudRate, dataBits, stopBits, parity);
			Device.readyToWrite(true);
		//}
		//else{
		//	System.out.println("No device found;");
		//}
	}
	
	private synchronized void checkIfGet() throws InterruptedException{
		while(!get) wait();
	}
	
	public synchronized void getSpec(boolean get){
		this.get = get;
		if(this.get) notifyAll();
	}
	
	public void toggleReadOnce(boolean readOnce){
		this.readOnce = readOnce;
	}
	
	private class updateChart implements Runnable{
		public void run() {
			try {
				while(true){
					checkIfGet();
					//getSpecData();
					reChart();
					if(readOnce) getSpec(false);
				}
			} catch (InterruptedException e) {
				System.out.println("Coleta de dados interrompida");
				e.printStackTrace();
			}
		}
		
	}
	
	public void reChart() throws InterruptedException{
		//Device.listPorts();
		//if(Device.portId.isCurrentlyOwned() == true) Device = new SerialComm("COM3");
		//else System.out.println("Ok");
		//if(Device.outputStream == null) return;
		this.removeAll();
		System.out.println("Update in Progress");
		instDataset();
		//fillDataset();
		//sampleDataset();
		//getDataset();
		
		setDataset();
		
		chart = ChartFactory.createXYLineChart("", "Comprimento de Onda", Image, dataset, 
				PlotOrientation.VERTICAL,true,true,false);
		//chart.getXYPlot().setRenderer(new XYSplineRenderer());
		
		//Limite da Imagem. para limite do domínio, getDomainAxis
		NumberAxis counts = (NumberAxis) ((XYPlot) chart.getPlot()).getRangeAxis();
		counts.setRange(0, 2500);
	
		//chart.setBackgroundPaint(new Color(255,255,255,50));
		panel = new ChartPanel(chart);
		panel.setPreferredSize(new Dimension(600, 300));
		
		((XYPlot) chart.getPlot()).setBackgroundPaint(new Color(0, 0, 51));
		//((XYPlot) chart.getPlot()).setBackgroundAlpha(0.5f);
		((XYPlot) chart.getPlot()).setRangeGridlinePaint(Color.white);
		
		//Plot plot = chart.getPlot();
		//GradientPaint gradientPaint = new GradientPaint(0.0F, 10.0F, Color.WHITE, 1000, 1000, Color.green.darker());
		//plot.setBackgroundPaint(gradientPaint);

		this.setLayout(new BorderLayout());
		this.add(panel, BorderLayout.CENTER);
		this.revalidate();
		Thread.sleep(50);
	}
	public void instDataset(){
		series = new XYSeries("Espectro A");
		dataset = new XYSeriesCollection();
	}
	
	private void sampleDataset(){
		series.add(375, 220+(Math.random()-0.5)*300);
		series.add(400, 280+(Math.random()-0.5)*300);
		series.add(416, 342+(Math.random()-0.5)*300);
		series.add(425, 500+(Math.random()-0.5)*300);
		series.add(450, 2736+(Math.random()-0.5)*300);
		series.add(475, 340+(Math.random()-0.5)*300);
		series.add(500, 1026+(Math.random()-0.5)*300);
		series.add(525, 3800+(Math.random()-0.5)*300);
		series.add(550, 1520+(Math.random()-0.5)*300);
		series.add(575, 1150+(Math.random()-0.5)*300);
		series.add(600, 342+(Math.random()-0.5)*300);
		series.add(625, 338+(Math.random()-0.5)*300);
		series.add(650, 337+(Math.random()-0.5)*300);
		series.add(675, 336+(Math.random()-0.5)*300);
		series.add(700, 336+(Math.random()-0.5)*300);
		series.add(725, 336+(Math.random()-0.5)*300);
		series.add(750, 336+(Math.random()-0.5)*300);
		series.add(775, 336+(Math.random()-0.5)*300);
		series.add(800, 336+(Math.random()-0.5)*300);
		series.add(825, 336+(Math.random()-0.5)*300);
		dataset.addSeries(series);
	}
	
	/*private void getDataset(){
		//Device = new SerialComm("COM3");
		if(Device.outputStream == null) return;
		
		try {
			Device.outputStream.write("+".getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			Device.serialPort.close();
		}
		while(!Device.isDataReady()) if(Device.isDataReady()) break;
		System.out.println("Done");
		Double[] leitura = Device.reader.parseReading();
		Device.reader.clearReading();
		for(int i=0; i<2048; i++)
			series.add(i, leitura[i]);
		FileDataBase.logVector(leitura);
		dataset.addSeries(series);
	}*/
	
	private void setDataset(){
		Device.sendString(str);
    	Device.readyToWrite(false);
    	time = System.nanoTime();
		try {
			Device.waitToWrite();
			String data = Device.getData(); 
			Double[] leitura = new Double[dataSize];
			int i = 0;
			char[] charArray = data.toCharArray();
			for(char Char : charArray){
				//System.out.println("Char: "+(int) Char);
				if(Char!=13 && Char!=10){
					buffer += Char;
				}
				else if(buffer!=""){
					//System.out.println(Double.parseDouble(buffer));
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
//	private void fillDataset(){
//		int x = 0;
//		while(x<5){
//			series.add(Math.random()*101, Math.random()*1001);
//			series.add(Math.random()*101, Math.random()*1001);
//			x++;
//		}
//		dataset.addSeries(series);
//	}
//	dataset.getSeries("Espectro A").clear();
//	dataset.removeSeries(0);
//	dataset.getSeries(0).clear();
}
