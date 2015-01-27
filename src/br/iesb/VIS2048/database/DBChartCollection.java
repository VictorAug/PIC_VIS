package br.iesb.VIS2048.database;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.iesb.VIS2048.frame.Chart;


public class DBChartCollection implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8532379948568202426L;
	private ArrayList<Chart> chartQueue;
	private String nome;
	
	public DBChartCollection() {
		setChartQueue(new ArrayList<Chart>());
	}
	public void addChart(Chart chart){
		getChartQueue().add(chart);
		//DBHandler.saveGZipObject(this, "teste.gz");
	}
	public Chart getChart(int i){
		return getChartQueue().get(i);
	}
	public int count(){
		return getChartQueue().size();
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String fileName) {
		this.nome = fileName;
	}
	public ArrayList<Chart> getChartQueue() {
		return chartQueue;
	}
	public void setChartQueue(ArrayList<Chart> chartQueue) {
		this.chartQueue = chartQueue;
	}

}
