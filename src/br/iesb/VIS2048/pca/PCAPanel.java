package br.iesb.VIS2048.pca;

import java.awt.BorderLayout;
import java.awt.Color;

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

    /** Constante serialVersionUID. */
    private static final long serialVersionUID = -792937870442306359L;
    private ChartPanel pcaPanel;
    private JFreeChart pcaFreeChart;
    private XYDataset dataSet;
    // private static final Random r = new Random();

    public PCAPanel() {
	setAlignmentY(0.0f);
	dataSet = new XYSeriesCollection();
	pcaFreeChart = ChartFactory.createScatterPlot("Visio", "", "", dataSet, PlotOrientation.VERTICAL, true, true, false);
	NumberAxis counts = (NumberAxis) ((XYPlot) pcaFreeChart.getPlot()).getRangeAxis();
	System.out.println(counts);
	// counts.setRange(0, 2500);
	pcaPanel = new ChartPanel(pcaFreeChart);
	pcaPanel.setBackground(Color.black);
	((XYPlot) pcaFreeChart.getPlot()).setRangeGridlinePaint(Color.white);
	((XYPlot) pcaFreeChart.getPlot()).setBackgroundPaint(Color.black);
	setLayout(new BorderLayout());
	add(pcaPanel, BorderLayout.CENTER);

	pcaFreeChart.setBackgroundPaint(Color.black);
	((XYPlot) pcaFreeChart.getPlot()).getRenderer().setSeriesPaint(0, Color.green);
	((XYPlot) pcaFreeChart.getPlot()).setDomainCrosshairPaint(Color.white);
	((XYPlot) pcaFreeChart.getPlot()).setDomainGridlinePaint(Color.white);
    }

    // private static XYDataset createDataset() {
    // XYSeriesCollection result = new XYSeriesCollection();
    // XYSeries series = new XYSeries("Random");
    // for (int i = 0; i <= 100; i++) {
    // double x = r.nextDouble();
    // double y = r.nextDouble();
    // series.add(x, y);
    // }
    // result.addSeries(series);
    // return result;
    // }

    public void updateChart(int x, int y, int nOfSamples, Matrix PCA) {
	// XYSeriesCollection result = new XYSeriesCollection();
	y = y - 1;
	x = x - 1;
	XYSeries series = new XYSeries("PCA");
	for (int i = 0; i < nOfSamples; i++) {
	    series.add(PCA.get(i, x), PCA.get(i, y));
	}
	if (dataSet.getSeriesCount() > 0)
	    ((XYSeriesCollection) dataSet).removeAllSeries();
	((XYSeriesCollection) dataSet).addSeries(series);
	pcaPanel.updateUI();
	System.out.println("Updated");
    }
}
