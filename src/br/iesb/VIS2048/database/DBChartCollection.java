package br.iesb.VIS2048.database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import br.iesb.VIS2048.frame.Chart;

/**
 * Class DBChartCollection.
 */
public class DBChartCollection implements Serializable {

	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 8532379948568202426L;

	/** Atributo chart queue. */
	private ArrayList<Chart> chartQueue;

	/** Atributo nome. */
	private String nome;

	/**
	 * Instancia um novo DB chart collection.
	 */
	public DBChartCollection() {
		chartQueue = new ArrayList<Chart>();
	}

	/**
	 * Adds the chart.
	 *
	 * @param chart
	 *            the chart
	 */
	public void addChart(Chart chart) {
		chartQueue.add(chart);
		// DBHandler.saveGZipObject(this, "teste.gz");
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
	 * Retorna nome.
	 *
	 * @return nome
	 */
	public String getNome() {
		return nome == null ? null : new String(nome);
	}

	/**
	 * Atribui o valor nome.
	 *
	 * @param fileName
	 *            novo nome
	 */
	public void setNome(String fileName) {
		if (fileName == null) {
			this.nome = null;
		} else {
			this.nome = new String(fileName);
		}
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
