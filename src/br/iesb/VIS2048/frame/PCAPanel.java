package br.iesb.VIS2048.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Random;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import Jama.Matrix;

public class PCAPanel extends JPanel {

    /**
	 * 
	 */
    private static final long serialVersionUID = -792937870442306359L;
    private NumberAxis counts;
    ChartPanel PCApanel;
    JFreeChart PCAjfreechart;
    XYDataset dataset;
    private String collectionName = "";
    private static final Random r = new Random();

    public PCAPanel() {
	setAlignmentY(0.0f);
	dataset = createDataset();
	PCAjfreechart = ChartFactory.createScatterPlot("Visio", "", collectionName, dataset, PlotOrientation.VERTICAL, true, true, false);
	counts = (NumberAxis) ((XYPlot) PCAjfreechart.getPlot()).getRangeAxis();
	// counts.setRange(0, 2500);
	PCApanel = new ChartPanel(PCAjfreechart);
	PCApanel.setBackground(Color.black);
	((XYPlot) PCAjfreechart.getPlot()).setRangeGridlinePaint(Color.white);
	((XYPlot) PCAjfreechart.getPlot()).setBackgroundPaint(Color.black);
	setLayout(new BorderLayout());
	add(PCApanel, BorderLayout.CENTER);

	PCAjfreechart.setBackgroundPaint(Color.black);
	((XYPlot) PCAjfreechart.getPlot()).getRenderer().setSeriesPaint(0, Color.green);
	((XYPlot) PCAjfreechart.getPlot()).setDomainCrosshairPaint(Color.white);
	((XYPlot) PCAjfreechart.getPlot()).setDomainGridlinePaint(Color.white);
    }


    private static XYDataset createDataset() {
	XYSeriesCollection result = new XYSeriesCollection();
	XYSeries series = new XYSeries("Random");
	for (int i = 0; i <= 100; i++) {
	    double x = r.nextDouble();
	    double y = r.nextDouble();
	    series.add(x, y);
	}
	result.addSeries(series);
	return result;
    }

    public void updateChart(int x, int y, Matrix PCA) {
	// XYSeriesCollection result = new XYSeriesCollection();
	XYSeries series = new XYSeries("PCA");
	for (int i = 0; i <= x; i++) {
	    for (int j = 0; j < y; j++) {
		series.add(x, PCA.get(x, y));
	    }
	}
	if (dataset.getSeriesCount() > 0)
	    ((XYSeriesCollection) dataset).removeAllSeries();
	((XYSeriesCollection) dataset).addSeries(series);
	PCApanel.updateUI();
	System.out.println("Updated");
    }
}
