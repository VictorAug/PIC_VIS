package br.iesb.VIS2048.comm;

import org.jfree.data.xy.XYSeries;

import br.iesb.VIS2048.chart.Chart;

public class Harvester {

	private SerialComm comm;
	private Thread launch = new Thread(new Harvest(), "Spectrometer");
	public Harvester() {
		comm = new SerialComm("COM6");
		if(comm.isConnected()){
			comm.OpenComm();
			comm.OpenReading();
			comm.updatePortSettings(335200, 8, 0, 0);
			comm.readyToGet(true);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//launch();
		}		
	}
	
	public void launch(){
		launch.setDaemon(true);
		launch.start();
	}
	
	private class Harvest implements Runnable{
		@Override
		public void run() {
			while(true){
			}			
		}
		
	}
	
	public Chart getDataset(String str, XYSeries series){
		String buffer = "";
		comm.readyToGet(false);
		comm.sendString(str);
		double time = System.nanoTime();
		try {
			comm.waitToGet();
			String data = comm.getData(); 
			Double[] leitura = new Double[comm.getNumberOfSamples()];
			int i = 0;
			char[] charArray = data.toCharArray();
			for(char Char : charArray){
				if(Char!=13 && Char!=10){
					buffer += Char;
				}
				else if(buffer!=""){
					leitura[i] = Double.parseDouble(buffer);
					series.add(i, leitura[i]);
					buffer = "";
					i++;
				}
			}
			time = System.nanoTime() - time;
			System.out.println("Harvester getDataset: "+time + " elapsed");
			Chart chart = new Chart(leitura, "Teste", "Teste", comm.getNumberOfSamples(), System.currentTimeMillis());
			
			chart.setXyseries(series);
			return chart;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return null;		
	}
	
}
