package br.iesb.VIS2048.frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JToggleButton;
import javax.swing.border.BevelBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


@SuppressWarnings("unused")
public class Chart extends JToggleButton implements Serializable{
	private static final long serialVersionUID = -914154144556108519L;
	private transient Double[] doubleSeries;
	private String nome;
	private String description;
	private long timestamp;
	private int numberOfSamples;
	private XYSeries xyseries;
	private JLabel picture;
	public transient Thread Image = new Thread(new genImage(), "Spectrometer");
	private boolean miliVolt = false;
	public Chart(/*Double[] doubleSeries, */String name, String description, int numberOfSamples, long timestamp, XYSeries series) {
		setBorderPainted(false);
		setBackground(new Color(0, 0, 51));
		setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(102, 204, 204), new Color(102, 204, 204), Color.CYAN, Color.CYAN));
		//this.setDoubleSeries(doubleSeries);
		this.xyseries = series;
		this.setName(name);
		this.setDescription(description);
		this.setTimestamp(timestamp);
		this.setNumberOfSamples(numberOfSamples);
		this.setSize(112, 84);
		this.setMargin(new Insets(0, 0, 0, 0));
		//DBHandler.logVector(doubleSeries, this.nome, this.description, this.numberOfSamples, this.timestamp);
		//this.setPicture(picture);
		//this.setXyseries(xyseries);
	}
	public class genImage implements Runnable{

		@Override
		public void run() {
			XYSeriesCollection dataset;
			JFreeChart jfreechart;
			ChartPanel panel;
			NumberAxis counts;
			dataset = new XYSeriesCollection();
			jfreechart = ChartFactory.createXYLineChart("", "", nome, dataset, PlotOrientation.VERTICAL,true,true,false);
			
			counts = (NumberAxis) ((XYPlot) jfreechart.getPlot()).getRangeAxis();
			counts.setRange(0, 2500);
			
			panel = new ChartPanel(jfreechart);
			panel.setPreferredSize(new Dimension(640, 480));
			((XYPlot) jfreechart.getPlot()).setRangeGridlinesVisible(false);
			((XYPlot) jfreechart.getPlot()).setDomainGridlinesVisible(false);
			((XYPlot) jfreechart.getPlot()).setOutlineVisible(false);
			((XYPlot) jfreechart.getPlot()).setBackgroundPaint(new Color(0, 0, 51));
			((XYPlot) jfreechart.getPlot()).setBackgroundPaint(Color.black);
			//jfreechart.setBackgroundPaint(Color.black);
			counts.setTickMarksVisible(false);
			counts.setTickLabelsVisible(false);
			//jfreechart.setBorderPaint(new Color(255,255,255));
			
			dataset.addSeries(xyseries);
			((XYPlot) jfreechart.getPlot()).getRenderer().setSeriesPaint(0, Color.white);
			//picture = new JLabel(new ImageIcon(resizeImg(jfreechart.createBufferedImage( 640, 480, null), 112, 84)));
			//picture =new JLabel(new ImageIcon(jfreechart.createBufferedImage(640,  480).getScaledInstance(112, 84, 2)));
			
			//add(picture);
			setIcon(new ImageIcon(jfreechart.createBufferedImage(640,  480).getScaledInstance(112, 84, 2)));
			//JFrame frame = new JFrame();
			//frame.setVisible(true);
			//frame.setBounds(100, 100, 112, 112);
			//frame.add(picture);
		}
		public BufferedImage resizeImg(BufferedImage img, int newW, int newH){
		    int w = img.getWidth();
		    int h = img.getHeight();
		    BufferedImage dimg = new BufferedImage(newW, newH, img.getType());
		    Graphics2D g = dimg.createGraphics();
		    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
		            RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		    g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);
		    g.dispose();
		    return dimg;      
	    }
	}
	
	
	public Double[] getDoubleSeries() {
		return doubleSeries;
	}

	public void setDoubleSeries(Double[] doubleSeries) {
		this.doubleSeries = doubleSeries;
	}

	public String getName() {
		return nome;
	}

	public void setName(String nome) {
		this.nome = nome;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public int getNumberOfSamples() {
		return numberOfSamples;
	}

	public void setNumberOfSamples(int numberOfSamples) {
		this.numberOfSamples = numberOfSamples;
	}

	public JLabel getPicture() {
		return picture;
	}

	public void setPicture(/*JLabel picture*/) {
		//this.picture = picture;
		Image.setDaemon(true);
		Image.start();	
		
	}

	public XYSeries getXyseries() {
		return xyseries;
	}

	public void setXyseries(XYSeries xyseries) {
		this.xyseries = xyseries;
	}

	public void setMiliVolt(boolean b) {
		this.miliVolt  = b;		
	}


}
