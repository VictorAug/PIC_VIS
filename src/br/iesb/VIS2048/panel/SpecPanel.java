package br.iesb.VIS2048.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


public class SpecPanel extends JPanel {
	private static final long serialVersionUID = 1L;
<<<<<<< HEAD
	boolean get = false;
	Thread RSpec = new Thread(new updateChart(), "Spectrometer");
	XYSeries series;
	XYSeriesCollection dataset;
	JFreeChart chart;
	ChartPanel panel;
=======
	//public SpecPanel(){
		//graphic = new JPanel();
	//}
//	public SpecPanel() {
//		//init();
//		SpecPanel SpecDis = new SpecPanel();
//		Thread t1 = new Thread(SpecDis);
//		t1.start();
//	}
	public SpecPanel(){
		
		init();
	}
	
	
	/*protected*/private XYSeries series;
	/*protected*/private XYSeriesCollection dataset;
	/*protected*/private JFreeChart chart;
	/*protected*/private ChartPanel panel;
	/*protected*/private SpecGenerator Gen;
	/*protected*/private Boolean stop = false;
>>>>>>> c5e247280a9fe7a4c1803ffaf674706305d1c6bb
	
	public SpecPanel(){
		RSpec.setDaemon(true);
		RSpec.start();
	}
	
	private synchronized void checkIfGet() throws InterruptedException{
		while(!get) wait();
	}
	
	public synchronized void getSpec(boolean get){
		this.get = get;
		if(this.get) notifyAll();
	}
	
	private class updateChart implements Runnable{
		@Override
		public void run() {
			try {
				while(true){
					checkIfGet();
					//getSpecData();
					reChart();
				}
			} catch (InterruptedException e) {
				System.out.println("Coleta de dados interrompida");
				e.printStackTrace();
			}
		}
	}
	
	public void reChart() throws InterruptedException{
		this.removeAll();
		//System.out.println("Update in Progress");
		instDataset();
		//fillDataset();
		sampleDataset();
		chart = ChartFactory.createXYLineChart("", "Comprimento de Onda", "Counts", dataset, 
				PlotOrientation.VERTICAL,true,true,false);
		chart.getXYPlot().setRenderer(new XYSplineRenderer());
		
		//Limite da Image. para limite do domínio, getDomainAxis
		NumberAxis counts = (NumberAxis) ((XYPlot) chart.getPlot()).getRangeAxis();
		counts.setRange(0, 4000);
	
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
		Thread.sleep(100);
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
		series.add(575, 1150+(Math.random()-0.5)*600);
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
	
	private void fillDataset(){
		int x = 0;
		while(x<5){
			series.add(Math.random()*101, Math.random()*1001);
			series.add(Math.random()*101, Math.random()*1001);
			x++;
		}
		dataset.addSeries(series);
	}
//	dataset.getSeries("Espectro A").clear();
//	dataset.removeSeries(0);
//	dataset.getSeries(0).clear();
}
