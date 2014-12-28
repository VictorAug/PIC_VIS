package br.iesb.VIS2048.database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.iesb.VIS2048.chart.Chart;

public class DBChartCollection implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1315150314911159697L;
	private List<Chart> chartQueue;
	
	public DBChartCollection() {
		chartQueue = new ArrayList<Chart>();
		
	}
	public void addChart(Chart chart){
		chartQueue.add(chart);
		DBHandler.saveGZipObject(this, "teste.gz");
	}

}
