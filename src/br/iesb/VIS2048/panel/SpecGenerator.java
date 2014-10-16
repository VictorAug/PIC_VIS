package br.iesb.VIS2048.panel;

import org.jfree.data.xy.XYSeries;

public class SpecGenerator implements Runnable{

	XYSeries genStart() throws InterruptedException{
		while(true){
			System.out.println("Hello");		
			Thread.sleep(1*1000);
		}
	}

	//@Override
	public void run() {
		try {
			genStart();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}
