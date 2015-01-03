//package br.iesb.VIS2048.frame;
//
//import java.awt.BorderLayout;
//import java.awt.Color;
//import java.awt.Dimension;
//import java.awt.image.BufferedImage;
//
//import javax.swing.ImageIcon;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartPanel;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.axis.NumberAxis;
//import org.jfree.chart.plot.PlotOrientation;
//import org.jfree.chart.plot.XYPlot;
//import org.jfree.data.xy.XYSeries;
//import org.jfree.data.xy.XYSeriesCollection;
//
//import br.iesb.VIS2048.comm.Harvester;
//import br.iesb.VIS2048.database.DBChartCollection;
//
//public class ChartContainerBackup extends JPanel{
//	private boolean readOnce = false;
//	private boolean get = false;
//	public Thread RSpec = new Thread(new updateChart(), "Spectrometer");
//	private XYSeriesCollection dataset;
//	private JFreeChart jfreechart;
//	private ChartPanel panel;
//	private NumberAxis counts;
//	private DBChartCollection chartCollection;
//	private Harvester harvester;
//	private String port;
//	private JScrollPane container;
//	private int baudRate = 335200;
//	private int dataBits = 8;
//	private int stopBits = 0;
//	private int parity = 0;
//	private String collectionName;
//	private String fileName;
//	private static final long serialVersionUID = -8773563746572880829L;
//
//	public ChartContainerBackup(String port){
//		setBackground(new Color(0, 0, 102));
//		
//		dataset = new XYSeriesCollection();
//		jfreechart = ChartFactory.createXYLineChart("", "Counts", collectionName, dataset, 
//				PlotOrientation.VERTICAL,true,true,false);
//		
//		counts = (NumberAxis) ((XYPlot) jfreechart.getPlot()).getRangeAxis();
//		counts.setRange(0, 2500);
//		
//		panel = new ChartPanel(jfreechart);
//		panel.setPreferredSize(new Dimension(600, 300));
//		((XYPlot) jfreechart.getPlot()).setRangeGridlinePaint(Color.white);
//		((XYPlot) jfreechart.getPlot()).setBackgroundPaint(new Color(0, 0, 51));
//		this.setLayout(new BorderLayout());
//		this.add(panel, BorderLayout.CENTER);
//		setChartCollection(new DBChartCollection());
//		setHarvester(new Harvester(port));
//		RSpec.setDaemon(true);
//		RSpec.start();		
//		
//	}
//	public void setContainer(JScrollPane container){
//		this.container = container;
//	}
//	public void launchThread(){
//		RSpec = new Thread(new updateChart(), "Spectrometer");
//	}
//	public void updatePortSettings(int baudRate, int dataBits, int stopBits, int parity){
//		getHarvester().updatePortSettings(baudRate, dataBits, stopBits, parity);
//	}
//	private synchronized void checkIfGet() throws InterruptedException{ //Verifica se há ordem de Adquirir
//		while(!get) wait();
//	}
//	
//	public synchronized void setGet(boolean get){ //Controla ordem de adquirir
//		this.get = get;
//		if(this.get) notifyAll();
//	}
//	
//	public NumberAxis getCounts() {
//		return counts;
//	}
//
//	public boolean isReadOnce() {
//		return readOnce;
//	}
//
//	public void setReadOnce(boolean readOnce) {
//		this.readOnce = readOnce;
//	}
//
//	public int getBaudRate() {
//		return baudRate;
//	}
//
//	public void setBaudRate(int baudRate) {
//		this.baudRate = baudRate;
//	}
//
//	public int getDataBits() {
//		return dataBits;
//	}
//
//	public void setDataBits(int dataBits) {
//		this.dataBits = dataBits;
//	}
//
//	public int getStopBits() {
//		return stopBits;
//	}
//
//	public void setStopBits(int stopBits) {
//		this.stopBits = stopBits;
//	}
//
//	public int getParity() {
//		return parity;
//	}
//
//	public void setParity(int parity) {
//		this.parity = parity;
//	}
//
//	public String getPort() {
//		return port;
//	}
//	public void setPort(String port) {
//		this.port = port;
//	}
//	
//	public void slideContainer(BufferedImage img){
//		ImageIcon ii = new ImageIcon(img);
//		container.setViewportView(new JLabel(ii)); 
//	}
//	
//	public class updateChart implements Runnable{
//		XYSeries series;
//		public void run() {
//			while(true){
//				try {
//					checkIfGet();
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				series = new XYSeries(collectionName);
//				Chart chart = getHarvester().getDataset("+", series);
//				
//				getChartCollection().addChart(chart);
//				System.out.println(getChartCollection().count());
//				if(dataset.getSeriesCount() > 0)
//					dataset.removeAllSeries();
//				dataset.addSeries(series);
//				
//				
//				chart.setPicture();
//				container.add(chart);
//				if (readOnce) {
//					setGet(false);
//				}
//			}
//		}
//	}
//
//	public Object getDevice() {
//		return getHarvester();
//	}
//	public Harvester getHarvester() {
//		return harvester;
//	}
//	public void setHarvester(Harvester harvester) {
//		this.harvester = harvester;
//	}
//	public String getFileName() {
//		return fileName;
//	}
//	public void setFileName(String fileName) {
//		this.fileName = fileName;
//		getChartCollection().setFileName(fileName);
//	}
//	public DBChartCollection getChartCollection() {
//		return chartCollection;
//	}
//	public void setChartCollection(DBChartCollection chartCollection) {
//		this.chartCollection = chartCollection;
//	}
//	public void setCollectionName(String collectionName) {
//		this.collectionName = collectionName;		
//	}
//}
