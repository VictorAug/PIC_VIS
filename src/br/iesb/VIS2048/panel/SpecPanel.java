package br.iesb.VIS2048.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class SpecPanel extends JPanel implements Runnable{
	//JPanel graphic;
	/**
	 * Spec Panel
	 */
	private static final long serialVersionUID = 1L;
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
	
	
	XYSeries series;
	XYSeriesCollection dataset;
	JFreeChart chart;
	ChartPanel panel;
	SpecGenerator Gen;
	public Boolean stop = false;
	
	public void init() {
		this.removeAll();
		System.out.println("Update in Progress");
		series = new XYSeries("Espectro A");
		int x = 0;
		while(x<5){
			series.add(Math.random()*101, Math.random()*1001);
			series.add(Math.random()*101, Math.random()*1001);
			x++;
		}
		dataset = new XYSeriesCollection();
		dataset.addSeries(series);
		chart = ChartFactory.createXYLineChart("", "Comprimento de Onda", "Counts", dataset, 
				PlotOrientation.VERTICAL,true,true,false);
		chart.getXYPlot().setRenderer(new XYSplineRenderer());
		//chart.setBackgroundPaint(new Color(255,255,255,50));
		panel = new ChartPanel(chart);
		panel.setPreferredSize(new Dimension(600, 300));
		
		this.setLayout(new BorderLayout());
		this.add(panel, BorderLayout.CENTER);
		this.revalidate();
	}

	@Override
	public void run() {
		stop = false;
		while(true){
			init();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(stop)
				return;
		}
	}
//	public void update(){
//
//		System.out.println("Update in progress");
//		series = new XYSeries("Espectro A");
//		Gen = new SpecGenerator();
//		Thread t1 = new Thread(Gen);
//		t1.start();
//
//
//		dataset = new XYSeriesCollection();
//		dataset.addSeries(series);
//		chart = ChartFactory.createXYLineChart("", "Comprimento de Onda", "Counts", dataset, 
//				PlotOrientation.VERTICAL,true,true,false);
//		chart.getXYPlot().setRenderer(new XYSplineRenderer());
//		panel = new ChartPanel(chart);
//		panel.setPreferredSize(new Dimension(600, 300));
//		this.setLayout(new BorderLayout());
//		this.add(panel, BorderLayout.CENTER);
//	}

}
