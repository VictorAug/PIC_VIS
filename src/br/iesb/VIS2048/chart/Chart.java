package br.iesb.VIS2048.chart;

import java.io.Serializable;

import org.jfree.data.xy.XYSeries;

import br.iesb.VIS2048.database.DBHandler;


public class Chart implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -914154144556108519L;
	private Double[] doubleSeries;
	private String nome;
	private String description;
	private long timestamp;
	private int numberOfSamples;
	private byte[] picture;
	private XYSeries xyseries;
	
	public Chart(Double[] doubleSeries, String name, String description, int numberOfSamples, long timestamp/*, byte[] picture, XYSeries xyseries*/) {
		this.setDoubleSeries(doubleSeries);
		this.setName(name);
		this.setDescription(description);
		this.setTimestamp(timestamp);
		this.setNumberOfSamples(numberOfSamples);
		
		DBHandler.logVector(doubleSeries, this.nome, this.description, this.numberOfSamples, this.timestamp);
		//this.setPicture(picture);
		//this.setXyseries(xyseries);
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

	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	public XYSeries getXyseries() {
		return xyseries;
	}

	public void setXyseries(XYSeries xyseries) {
		this.xyseries = xyseries;
	}
	
}
