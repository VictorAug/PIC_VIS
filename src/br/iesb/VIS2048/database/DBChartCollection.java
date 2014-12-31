package br.iesb.VIS2048.database;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.iesb.VIS2048.chart.Chart;


public class DBChartCollection implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8532379948568202426L;
	private List<Chart> chartQueue;
	private String fileName = "";
	public DBChartCollection() {
		chartQueue = new ArrayList<Chart>();
	}
	public void addChart(Chart chart){
		chartQueue.add(chart);
		//DBHandler.saveGZipObject(this, "teste.gz");
	}
	public int count(){
		return chartQueue.size();
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
