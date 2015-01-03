//package br.iesb.VIS2048.comm;
//
//import org.jfree.data.xy.XYSeries;
//
//import br.iesb.VIS2048.chart.Chart;
//
//public class HarvesterBackup{
//	private SerialComm comm;
//	private String port;
//	private int baudRate = 335200;
//	private int dataBits = 8;
//	private int stopBits = 0;
//	private int parity = 0;
//	private boolean isConnected;
//	private Thread launch = new Thread(new Harvest(), "Spectrometer");
//	
//	public HarvesterBackup(String port) {
//		this.port = port;
//		//if(tryConnection(port)) setConnected(true);
//		//else setConnected(false);
//		
//	}
//	
//	public void launch(){
//		launch.setDaemon(true);
//		launch.start();
//	}
//	
//	public boolean tryConnection(String port){
//		this.port = port;
//		setDevice(new SerialComm(this.port));
//		if(getDevice().isConnected()){
//			getDevice().OpenComm();
//			getDevice().OpenReading();
//			getDevice().updatePortSettings(baudRate, dataBits, stopBits, parity);
//			getDevice().readyToGet(true);
//			try {
//				Thread.sleep(2000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			//launch();
//			return true;
//		}
//		return false;
//	}
//	public void updatePortSettings(int baudRate, int dataBits, int stopBits, int parity){
//		getDevice().updatePortSettings(baudRate, dataBits, stopBits, parity);
//	}
//	private class Harvest implements Runnable{
//		@Override
//		public void run() {
//			while(true){
//			}			
//		}
//	}
//	
//	public Chart getDataset(String str, XYSeries series){
//		String buffer = "";
//		getDevice().readyToGet(false);
//		getDevice().sendString(str);
//		double time = System.nanoTime();
//		try {
//			getDevice().waitToGet();
//			String data = getDevice().getData(); 
//			Double[] leitura = new Double[getDevice().getNumberOfSamples()];
//			int i = 0;
//			char[] charArray = data.toCharArray();
//			for(char Char : charArray){
//				if(Char!=13 && Char!=10){
//					buffer += Char;
//				}
//				else if(buffer!=""){
//					leitura[i] = Double.parseDouble(buffer);
//					series.add(i, leitura[i]);
//					buffer = "";
//					i++;
//				}
//			}
//			time = System.nanoTime() - time;
//			System.out.println("Harvester getDataset: "+time + " elapsed");
//			Chart chart = new Chart(/*leitura,*/ "Teste", "Teste", getDevice().getNumberOfSamples(), System.currentTimeMillis());
//			
//			chart.setXyseries(series);
//			return chart;
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		
//		return null;		
//	}
//
//	public boolean isConnected() {
//		return isConnected;
//	}
//
//	public void setConnected(boolean isConnected) {
//		this.isConnected = isConnected;
//	}
//
//	public SerialComm getDevice() {
//		return comm;
//	}
//
//	public void setDevice(SerialComm comm) {
//		this.comm = comm;
//	}
//	
//}
