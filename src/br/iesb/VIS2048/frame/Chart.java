package br.iesb.VIS2048.frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Collections;
import java.util.StringJoiner;

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
import org.jfree.data.ComparableObjectSeries;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.util.StringUtils;

/**
 * Class Chart.
 */
@SuppressWarnings("unused")
public class Chart extends JToggleButton implements Serializable {

    /** Constante serialVersionUID. */
    private static final long serialVersionUID = -914154144556108519L;

    /** Atributo double series. */
    private transient Double[] doubleSeries;

    /** Atributo nome. */
    private String nome;

    /** Atributo description. */
    private String description;

    /** Atributo timestamp. */
    private Long timeStamp;

    /** Atributo number of samples. */
    private Integer numberOfSamples;

    /** Atributo xyseries. */
    private XYSeries xySeries;

    /** Atributo Image. */
    public transient Thread image = new Thread(new genImage(), "Spectrometer");

    /** Atributo mili volt. */
    private boolean miliVolt = false;

    /**
     * Instancia um novo chart.
     *
     * @param name
     *            the name
     * @param description
     *            the description
     * @param numberOfSamples
     *            the number of samples
     * @param timestamp
     *            the timestamp
     * @param series
     *            the series
     */
    public Chart(/* Double[] doubleSeries, */String name, String description, int numberOfSamples, long timestamp, XYSeries series) {
	setBorderPainted(false);
	setBackground(new Color(0, 0, 51));
	setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(102, 204, 204), new Color(102, 204, 204), Color.CYAN, Color.CYAN));
	// this.setDoubleSeries(doubleSeries);
	this.xySeries = series;
	this.setName(name);
	this.setDescription(description);
	this.setTimestamp(timestamp);
	this.setNumberOfSamples(numberOfSamples);
	this.setSize(112, 84);
	this.setMargin(new Insets(0, 0, 0, 0));
	// DBHandler.logVector(doubleSeries, this.nome, this.description,
	// this.numberOfSamples, this.timestamp);
	// this.setPicture(picture);
	// this.setXyseries(xyseries);
    }

    /**
     * Class genImage.
     */
    public class genImage implements Runnable {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
	    XYSeriesCollection dataset;
	    JFreeChart jfreechart;
	    ChartPanel panel;
	    NumberAxis counts;
	    dataset = new XYSeriesCollection();
	    jfreechart = ChartFactory.createXYLineChart("", "", nome, dataset, PlotOrientation.VERTICAL, true, true, false);

	    counts = (NumberAxis) ((XYPlot) jfreechart.getPlot()).getRangeAxis();
	    counts.setRange(0, 2500);

	    panel = new ChartPanel(jfreechart);
	    panel.setPreferredSize(new Dimension(640, 480));
	    ((XYPlot) jfreechart.getPlot()).setRangeGridlinesVisible(false);
	    ((XYPlot) jfreechart.getPlot()).setDomainGridlinesVisible(false);
	    ((XYPlot) jfreechart.getPlot()).setOutlineVisible(false);
	    ((XYPlot) jfreechart.getPlot()).setBackgroundPaint(new Color(0, 0, 51));
	    ((XYPlot) jfreechart.getPlot()).setBackgroundPaint(Color.black);
	    // jfreechart.setBackgroundPaint(Color.black);
	    counts.setTickMarksVisible(false);
	    counts.setTickLabelsVisible(false);
	    // jfreechart.setBorderPaint(new Color(255,255,255));

	    dataset.addSeries(xySeries);
	    ((XYPlot) jfreechart.getPlot()).getRenderer().setSeriesPaint(0, Color.white);
	    // picture = new JLabel(new
	    // ImageIcon(resizeImg(jfreechart.createBufferedImage( 640, 480,
	    // null), 112, 84)));
	    // picture =new JLabel(new
	    // ImageIcon(jfreechart.createBufferedImage(640,
	    // 480).getScaledInstance(112, 84, 2)));

	    // add(picture);
	    setIcon(new ImageIcon(jfreechart.createBufferedImage(640, 480).getScaledInstance(112, 84, 2)));
	    // JFrame frame = new JFrame();
	    // frame.setVisible(true);
	    // frame.setBounds(100, 100, 112, 112);
	    // frame.add(picture);
	}

	/**
	 * Resize img.
	 *
	 * @param img
	 *            the img
	 * @param newW
	 *            the new w
	 * @param newH
	 *            the new h
	 * @return buffered image
	 */
	public BufferedImage resizeImg(BufferedImage img, int newW, int newH) {
	    int w = img.getWidth();
	    int h = img.getHeight();
	    BufferedImage dimg = new BufferedImage(newW, newH, img.getType());
	    Graphics2D g = dimg.createGraphics();
	    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);
	    g.dispose();
	    return dimg;
	}
    }

    /**
     * Retorna double series.
     *
     * @return double series
     */
    public Double[] getDoubleSeries() {
	return doubleSeries == null ? null : doubleSeries.clone();
    }

    /**
     * Atribui o valor double series.
     *
     * @param doubleSeries
     *            novo double series
     */
    public void setDoubleSeries(Double[] doubleSeries) {
	if (doubleSeries == null) {
	    this.doubleSeries = null;
	} else {
	    this.doubleSeries = doubleSeries.clone();
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.Component#getName()
     */
    public String getName() {
	return nome == null ? null : new String(nome);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.Component#setName(java.lang.String)
     */
    public void setName(String nome) {
	if (nome == null) {
	    this.nome = null; 
	} else {
	    this.nome = new String(nome);
	}
    }

    /**
     * Retorna description.
     *
     * @return description
     */
    public String getDescription() {
	return description == null ? null : new String(description);
    }

    /**
     * Atribui o valor description.
     *
     * @param description
     *            novo description
     */
    public void setDescription(String description) {
	if (description == null) {
	    this.description = null;
	} else {
	    this.description = new String(description);
	}
    }

    /**
     * Retorna timestamp.
     *
     * @return timestamp
     */
    public Long getTimestamp() {
	return timeStamp == null ? null : Long.valueOf(timeStamp);
    }

    /**
     * Atribui o valor timestamp.
     *
     * @param timeStamp
     *            novo timestamp
     */
    public void setTimestamp(Long timeStamp) {
	if (timeStamp == null) {
	    this.timeStamp = null;
	} else {
	    this.timeStamp = Long.valueOf(timeStamp);
	}
    }

    /**
     * Retorna number of samples.
     *
     * @return number of samples
     */
    public Integer getNumberOfSamples() {
	return numberOfSamples == null ? null : Integer.valueOf(numberOfSamples);
    }

    /**
     * Atribui o valor number of samples.
     *
     * @param numberOfSamples
     *            novo number of samples
     */
    public void setNumberOfSamples(Integer numberOfSamples) {
	if (numberOfSamples == null) {
	    this.numberOfSamples = null;
	} else {
	    this.numberOfSamples = Integer.valueOf(numberOfSamples);
	}
    }

    /**
     * Sets the picture.
     */
    public void setPicture(/* JLabel picture */) {
	// this.picture = picture;
	image.setDaemon(true);
	image.start();
    }

    /**
     * Retorna xyseries.
     *
     * @return xyseries
     */
    public XYSeries getXyseries() {
	return xySeries == null ? null : xySeries;
    }

    /**
     * Atribui o valor xyseries.
     *
     * @param xyseries
     *            novo xyseries
     */
    public void setXyseries(XYSeries xyseries) {
	if (xyseries == null) {
	    this.xySeries = null;
	} else {
	    this.xySeries = xyseries;
	}
    }

    /**
     * Atribui o valor mili volt.
     *
     * @param b
     *            novo mili volt
     */
    public void setMiliVolt(boolean b) {
	this.miliVolt = b;
    }

}
