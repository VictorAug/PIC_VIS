package br.iesb.VIS2048.panel;

import org.jfree.data.xy.XYSeries;

// TODO: Auto-generated Javadoc
/**
 * The Class SpecGenerator.
 */
public class SpecGenerator implements Runnable {

    /**
     * Gen start.
     *
     * @return the XY series
     * @throws InterruptedException the interrupted exception
     */
    XYSeries genStart() throws InterruptedException {
	while (true) {
	    System.out.println("Hello");
	    Thread.sleep(1 * 1000);
	}
    }

    public void run() {
	try {
	    genStart();
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}

    }
}
