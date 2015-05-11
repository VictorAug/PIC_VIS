package br.iesb.VIS2048.database;

import java.io.Serializable;
import java.util.ArrayList;

import br.iesb.VIS2048.frame.Chart;

/**
 * Classe DBChartCollection.
 */
public class DBChartCollection implements Serializable {

    /** Constante de serialização do objeto. */
    private static final long serialVersionUID = 8532379948568202426L;

    private ArrayList<Chart> chartQueue;

    /**
     * Instancia um novo objeto <code>DBChartCollection</code>.
     */
    public DBChartCollection() {
	chartQueue = new ArrayList<Chart>();
    }

    /**
     * Adiciona o chart.
     *
     * @param chart
     *            the chart
     */
    public void addChart(Chart chart) {
	chartQueue.add(chart);
    }

    /**
     * Retorna chart.
     *
     * @param i
     *            the i
     * @return chart
     */
    public Chart getChart(int i) {
	return chartQueue.get(i);
    }

    /**
     * Count.
     *
     * @return int
     */
    public int count() {
	return chartQueue.size();
    }

    /**
     * Retorna chart queue.
     *
     * @return chart queue
     */
    public ArrayList<Chart> getChartQueue() {
	return chartQueue == null ? null : new ArrayList<>(chartQueue);
    }

}
