package br.iesb.VIS2048.chart;

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

import br.iesb.VIS2048.comm.Harvester;
import br.iesb.VIS2048.database.DBChartCollection;

public class ChartContainer extends JPanel{
	private boolean get = false;
	private Thread RSpec = new Thread(new updateChart(), "Spectrometer");
	private XYSeriesCollection dataset;
	private JFreeChart chart;
	private ChartPanel panel;
	private NumberAxis counts;
	private DBChartCollection chartCollection;
	private Harvester harvester;
	
	private static final long serialVersionUID = -8773563746572880829L;

	public ChartContainer(){
		setBackground(new Color(0, 0, 102));
		
		dataset = new XYSeriesCollection();
		chart = ChartFactory.createXYLineChart("", "Comprimento de Onda", "Teste", dataset, 
				PlotOrientation.VERTICAL,true,true,false);
		
		counts = (NumberAxis) ((XYPlot) chart.getPlot()).getRangeAxis();
		counts.setRange(0, 2500);
		
		panel = new ChartPanel(chart);
		panel.setPreferredSize(new Dimension(600, 300));
		((XYPlot) chart.getPlot()).setRangeGridlinePaint(Color.white);
		this.setLayout(new BorderLayout());
		this.add(panel, BorderLayout.CENTER);
				
		harvester = new Harvester();
		RSpec.setDaemon(true);
		RSpec.start();		
	}
	
	private synchronized void checkIfGet() throws InterruptedException{ //Verifica se há ordem de Adquirir
		while(!get) wait();
	}
	
	public synchronized void setGet(boolean get){ //Controla ordem de adquirir
		this.get = get;
		if(this.get) notifyAll();
	}
	
	public NumberAxis getCounts() {
		return counts;
	}

	private class updateChart implements Runnable{
		XYSeries series;
		public void run() {
			while(true){
				setGet(true);
				try {
					checkIfGet();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				series = new XYSeries("Teste");
				chartCollection = new DBChartCollection();
				chartCollection.addChart(harvester.getDataset("+", series));
				if(dataset.getSeriesCount() > 0)
					dataset.removeSeries(dataset.getSeries("Teste"));
				dataset.addSeries(series);
			}
		}
	}
}
