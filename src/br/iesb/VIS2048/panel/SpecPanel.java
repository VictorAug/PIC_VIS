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

public class SpecPanel extends JPanel {

	/**
	 * Spec Panel
	 */
	private static final long serialVersionUID = 1L;
	
	public SpecPanel() {
		init();
	}
	
	public void init() {
		
		XYSeries series = new XYSeries("Espectro A");
		series.add(375, 220);
		series.add(400, 280);
		series.add(416, 342);
		series.add(425, 500);
		series.add(450, 2736);
		series.add(475, 340);
		series.add(500, 1026);
		series.add(525, 3800);
		series.add(550, 1520);
		series.add(575, 1150);
		series.add(600, 342);
		series.add(625, 338);
		series.add(650, 337);
		series.add(675, 336);
		series.add(700, 336);
		series.add(725, 336);
		series.add(750, 336);
		series.add(775, 336);
		series.add(800, 336);
		series.add(825, 336);
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series);
		JFreeChart chart = ChartFactory.createXYLineChart("", "Comprimento de Onda", "Counts", dataset, 
				PlotOrientation.VERTICAL,true,true,false);
		chart.getXYPlot().setRenderer(new XYSplineRenderer());
		ChartPanel panel = new ChartPanel(chart);
		panel.setPreferredSize(new Dimension(600, 300));
		this.setLayout(new BorderLayout());
		this.add(panel, BorderLayout.CENTER);
		
	}

}
