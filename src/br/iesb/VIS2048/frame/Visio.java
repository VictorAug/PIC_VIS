package br.iesb.VIS2048.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import jssc.SerialPortList;
import net.miginfocom.swing.MigLayout;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import br.iesb.VIS2048.comm.Harvester;
import br.iesb.VIS2048.database.DBChartCollection;
import br.iesb.VIS2048.database.DBHandler;

public class Visio {
	JLabel lblConectado;
	JLabel label;
	JButton btnAdquirir;
	JComboBox<String> comboBox;
	Harvester harvester;
	protected int baudRate = 225200;
	protected int dataBits = 8;
	protected int stopBits = 0;
	protected int parity = 0;
	private JFrame frame;
	public Thread RSpec = new Thread(new updateChart(), "Spectrometer");
	private boolean readOnce = false;
	private boolean get = false;
	//public Thread RSpec = new Thread(new updateChart(), "Spectrometer");
	private XYSeriesCollection dataset;
	private JFreeChart jfreechart;
	//private ChartPanel panel;
	private NumberAxis counts;
	private DBChartCollection chartCollection;
	//private String port;
	private JScrollPane scrollPane;
	private String collectionName;
	private String fileName;
	private JPanel sliderPanel;
	public static void main(String[] args) {
		System.setProperty("sun.java2d.d3d", "false");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Visio window = new Visio();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Visio() {
		initialize();
	}

	private void initialize() {
		DBHandler db = new DBHandler();
		harvester = new Harvester();
		comboBox = new JComboBox<String>();
		
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(204, 204, 255));
		frame.setBounds(100, 100, 840, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnArquivo = new JMenu("Arquivo");
		menuBar.add(mnArquivo);
		
		JMenuItem menuItem = new JMenuItem("New menu item");
		mnArquivo.add(menuItem);
		
		JMenu mnOpes = new JMenu("Opções");
		menuBar.add(mnOpes);
		
		JMenu mnAjuda = new JMenu("Ajuda");
		menuBar.add(mnAjuda);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		tabbedPane.setBackground(new Color(240, 248, 255));
		frame.getContentPane().add(tabbedPane);
		
		JPanel panel;
		panel_8 = new JPanel();
		panel_8.setBackground(new Color(0, 0, 51));
		tabbedPane.addTab(null, panel_8);
		tabbedPane.setEnabledAt(0, true);
		tabbedPane.setBackgroundAt(0, new Color(255, 255, 255));
		panel_8.setLayout(new MigLayout("", "[15%,grow][85%,grow]", "[grow][grow][grow][80px:n,grow]"));
		
		JLabel labTab1 = new JLabel("Espectro");
		labTab1.setUI(new VerticalLabelUI(false));
		tabbedPane.setTabComponentAt(0, labTab1);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(new Color(0, 0, 51));
		panel_5.setForeground(new Color(211, 211, 211));
		panel_5.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Espectro", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(211, 211, 211)));
		panel_8.add(panel_5, "cell 0 0,grow");
		panel_5.setLayout(new MigLayout("", "[100px,grow][100px,grow]", "[20px]"));
		
		btnAdquirir = new JButton("Adquirir");
		btnAdquirir.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnAdquirir.setEnabled(false);
		panel_5.add(btnAdquirir, "cell 0 0,grow");
		
		JButton btnParar = new JButton("Parar");
		btnParar.setFont(new Font("Tahoma", Font.PLAIN, 11));
		panel_5.add(btnParar, "cell 1 0,grow");
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(0, 0, 51));
		panel_8.add(panel_1, "cell 1 0 1 4,grow");
		panel_1.setLayout(new MigLayout("", "0[grow]0", "0[grow]0[115px:115px]0"));
		
		JPanel primeChart = new JPanel();
		primeChart.setBackground(new Color(0, 0, 51));
		primeChart.setAlignmentY(0.0f);
		dataset = new XYSeriesCollection();
		jfreechart = ChartFactory.createXYLineChart("", "Counts", collectionName, dataset, 
				PlotOrientation.VERTICAL,true,true,false);
		counts = (NumberAxis) ((XYPlot) jfreechart.getPlot()).getRangeAxis();
		counts.setRange(0, 2500);
		panel = new ChartPanel(jfreechart);
		//panel.setPreferredSize(new Dimension(300, 150));
		((XYPlot) jfreechart.getPlot()).setRangeGridlinePaint(Color.white);
		((XYPlot) jfreechart.getPlot()).setBackgroundPaint(new Color(0, 0, 51));
		primeChart.setLayout(new BorderLayout());
		primeChart.add(panel, BorderLayout.CENTER);
		chartCollection = new DBChartCollection();
		panel_1.add(primeChart, "cell 0 0,grow");
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(0, 0, 51));
		panel_1.add(panel_3, "cell 0 1,grow");
		panel_3.setLayout(new MigLayout("", "0[8px][grow]0", "[grow]0[grow]0[grow]0"));
		
		JButton button_1 = new JButton("+");
		button_1.setToolTipText("Adicionar Pacote");
		button_1.setMargin(new Insets(2, 6, 2, 6));
		panel_3.add(button_1, "cell 0 0,grow");
		
		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(null);
		panel_3.add(scrollPane, "cell 1 0 1 3,grow");
		
		sliderPanel = new JPanel();
		sliderPanel.setBackground(new Color(0, 0, 51));
		scrollPane.setViewportView(sliderPanel);
		sliderPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		
		JButton button_2 = new JButton("-");
		button_2.setToolTipText("Remover Pacote");
		button_2.setMargin(new Insets(2, 6, 2, 6));
		panel_3.add(button_2, "cell 0 1,grow");
		
		JButton button_3 = new JButton("0");
		button_3.setToolTipText("Deviation");
		button_3.setMargin(new Insets(2, 6, 2, 6));
		panel_3.add(button_3, "cell 0 2,grow");
		cleaner = new Thread(new clearComm());
		cleaner.setDaemon(true);
		cleaner.start();
		
		ButtonGroup groupModoOp = new ButtonGroup();
		
		ButtonGroup groupUnit = new ButtonGroup();
		checkPort = new Thread(new checkForPorts(comboBox));
		checkPort.setDaemon(true);
		checkPort.start();
		fileName = db.getMainDBFileName();
		collectionName = db.getMainDB();
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(0, 0, 51));
		tabbedPane.addTab("New tab", null, panel_2, null);
		tabbedPane.setEnabledAt(1, true);
		tabbedPane.setBackgroundAt(1, new Color(255, 255, 255));
		
		JLabel labTab2 = new JLabel("PCA");
		labTab2.setUI(new VerticalLabelUI(false));
		tabbedPane.setTabComponentAt(1, labTab2);

		btnParar.setAlignmentX(Component.RIGHT_ALIGNMENT);
		btnParar.setActionCommand("disable");
		btnParar.setEnabled(false);
		btnParar.requestFocus();
		btnParar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				if ("disable".equals(event.getActionCommand())) {
					//Zipper zipper = new Zipper();
					//zipper.generateFileList(null);
					//zipper.zipIt(null);
					btnAdquirir.setEnabled(true);
					btnParar.setEnabled(false);
					btnParar.setFocusable(false);
					setGet(false);
					double time = System.nanoTime();
					//DBHandler.saveGZipObject(chart.getChartCollection(), chart.getChartCollection().getFileName());
					time = System.nanoTime() - time;
					System.out.println("Time elapsed: "+time + ". Number of Charts: "+chartCollection.count());
				} else {
					btnAdquirir.setEnabled(false);
					btnParar.setEnabled(true);
					btnParar.setFocusable(true);
					//specPanel.reloadTitle();
				}
			}
		});

		btnAdquirir.setActionCommand("enable");
		btnAdquirir.setToolTipText("Adquirir leituras");
		
		JPanel panel_6 = new JPanel();
		panel_8.add(panel_6, "cell 0 1");
		panel_6.setBackground(new Color(0, 0, 51));
		panel_6.setForeground(new Color(211, 211, 211));
		panel_6.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Conex\u00E3o", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(211, 211, 211)));
		panel_6.setLayout(new MigLayout("", "[100px,grow][][100px,grow]", "[20px,grow]"));
		
		
		checkPort = new Thread(new checkForPorts(comboBox));
		comboBox.setForeground(new Color(211, 211, 211));
		comboBox.setBackground(new Color(0, 0, 51));
		comboBox.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		comboBox.addActionListener (new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				timeToClear(true);
			}
		});
		
		panel_6.add(comboBox, "cell 0 0,growx,aligny center");
		
		JButton button = new JButton("Reset");
		button.setToolTipText("Recuperar Conexão");
		button.setFont(new Font("Tahoma", Font.PLAIN, 11));
		button.setEnabled(true);
		panel_6.add(button, "cell 1 0");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnAdquirir.setEnabled(false);
				btnParar.setEnabled(false);
				
				if(RSpec.isAlive()){
					launchThread();
					RSpec.setDaemon(true);
					RSpec.start();
				}
				timeToClear(true);	
			}
		});		
		
		lblConectado = new JLabel("Indisponível");
		lblConectado.setForeground(new Color(255, 0, 0));
		panel_6.add(lblConectado, "cell 2 0,alignx center,aligny center");
		
		JPanel panel_4 = new JPanel();
		panel_8.add(panel_4, "cell 0 2");
		panel_4.setBackground(new Color(0, 0, 51));
		panel_4.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Op\u00E7\u00F5es", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(211, 211, 211)));
		panel_4.setLayout(new MigLayout("", "[100px,grow][100px,grow]", "[30px][20px][1px::][30px][20px][1px::][30px][20px][1px::][30px][30px][1px::][30px][20px]"));
		
		JLabel lblModoDeOperao = new JLabel("Modo de Operação");
		lblModoDeOperao.setHorizontalAlignment(SwingConstants.CENTER);
		lblModoDeOperao.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_4.add(lblModoDeOperao, "cell 0 0 2 1,grow");
		lblModoDeOperao.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblModoDeOperao.setForeground(new Color(211, 211, 211));
		lblModoDeOperao.setBackground(new Color(0, 0, 102));
		lblModoDeOperao.setOpaque(true);
		
		JRadioButton radioButton = new JRadioButton("\u00DAnico");
		panel_4.add(radioButton, "cell 0 1,alignx center,growy");
		radioButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		radioButton.setForeground(new Color(211, 211, 211));
		radioButton.setBackground(new Color(0, 0, 51));
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Cont\u00EDnuo");
		panel_4.add(rdbtnNewRadioButton, "cell 1 1,alignx center,growy");
		rdbtnNewRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnNewRadioButton.setForeground(new Color(211, 211, 211));
		rdbtnNewRadioButton.setBackground(new Color(0, 0, 51));
		groupModoOp.add(radioButton);
		groupModoOp.add(rdbtnNewRadioButton);
		radioButton.setActionCommand("Unico");
		rdbtnNewRadioButton.setActionCommand("Continuo");
		rdbtnNewRadioButton.setSelected(true);
		radioButton.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent event) {
		    	if ("Unico".equals(event.getActionCommand())) {
				    btnParar.setEnabled(false);
				    setReadOnce(true);
				    btnAdquirir.setEnabled(true);
				} else {
				    btnParar.setEnabled(false);
				    setReadOnce(false);
				}
		    }
		});
		rdbtnNewRadioButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent event) {
			    	if ("Continuo".equals(event.getActionCommand())) {
					    btnParar.setEnabled(true);
					    setReadOnce(false);
					    btnParar.setEnabled(true);
					} else {
					    btnParar.setEnabled(false);
					    setReadOnce(true);
					}
			    }
			});
		
		JSeparator separator = new JSeparator();
		separator.setForeground(new Color(0, 0, 51));
		separator.setBackground(new Color(0, 0, 51));
		panel_4.add(separator, "cell 0 2 2 1,grow");
		
		JLabel lblNewLabel = new JLabel("Unidade");
		lblNewLabel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel_4.add(lblNewLabel, "cell 0 3 2 1,grow");
		lblNewLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblNewLabel.setForeground(new Color(211, 211, 211));
		lblNewLabel.setBackground(new Color(0, 0, 102));
		lblNewLabel.setOpaque(true);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("Counts");
		panel_4.add(rdbtnNewRadioButton_1, "cell 0 4,alignx center,growy");
		rdbtnNewRadioButton_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnNewRadioButton_1.setForeground(new Color(211, 211, 211));
		rdbtnNewRadioButton_1.setBackground(new Color(0, 0, 51));
		rdbtnNewRadioButton_1.setActionCommand("Counts");
		rdbtnNewRadioButton_1.setSelected(true);
		groupUnit.add(rdbtnNewRadioButton_1);
		rdbtnNewRadioButton_1.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent event) {
				if ("Counts".equals(event.getActionCommand())) {
				    //specPanel.setImage("Counts");
				} else {
				    //specPanel.setImage("mV");
				}
		    }
		});
		
		JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("mV");
		panel_4.add(rdbtnNewRadioButton_2, "cell 1 4,alignx center,growy");
		rdbtnNewRadioButton_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnNewRadioButton_2.setForeground(new Color(211, 211, 211));
		rdbtnNewRadioButton_2.setBackground(new Color(0, 0, 51));
		rdbtnNewRadioButton_2.setActionCommand("mV");
		groupUnit.add(rdbtnNewRadioButton_2);
		rdbtnNewRadioButton_2.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent event) {
					if ("mV".equals(event.getActionCommand())) {
					    //specPanel.setImage("mV");
					} else {
					    //specPanel.setImage("Counts");
					}
			   }
		});
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(new Color(0, 0, 51));
		separator_1.setBackground(new Color(0, 0, 51));
		panel_4.add(separator_1, "cell 0 5 2 1,grow");
		
		JLabel lblFaixaEspectral = new JLabel("N\u00FAmero de Amostras");
		lblFaixaEspectral.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		lblFaixaEspectral.setHorizontalAlignment(SwingConstants.CENTER);
		panel_4.add(lblFaixaEspectral, "cell 0 6 2 1,grow");
		lblFaixaEspectral.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblFaixaEspectral.setForeground(new Color(211, 211, 211));
		lblFaixaEspectral.setBackground(new Color(0, 0, 102));
		lblFaixaEspectral.setOpaque(true);
		
		JSlider slider = new JSlider(0,2048,2048);
		panel_4.add(slider, "flowx,cell 0 7 2 1,grow");
		slider.setBackground(new Color(0, 0, 51));
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				label.setText(""+slider.getValue());
			}
		});
		slider.setSnapToTicks(true);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setForeground(new Color(0, 0, 51));
		separator_2.setBackground(new Color(0, 0, 51));
		panel_4.add(separator_2, "cell 0 8 2 1,grow");
		
		JLabel lblTempoDeIntegrao = new JLabel("Tempo de Integra\u00E7\u00E3o");
		lblTempoDeIntegrao.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		lblTempoDeIntegrao.setHorizontalAlignment(SwingConstants.CENTER);
		panel_4.add(lblTempoDeIntegrao, "cell 0 9 2 1,grow");
		lblTempoDeIntegrao.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblTempoDeIntegrao.setForeground(new Color(211, 211, 211));
		lblTempoDeIntegrao.setBackground(new Color(0, 0, 102));
		lblTempoDeIntegrao.setOpaque(true);
		
		JComboBox<String> comboBox_1 = new JComboBox<String>();
		comboBox_1.setBackground(new Color(0, 0, 51));
		comboBox_1.setForeground(new Color(211, 211, 211));
		panel_4.add(comboBox_1, "cell 0 10 2 1,growx");
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setForeground(new Color(0, 0, 51));
		separator_3.setBackground(new Color(0, 0, 51));
		panel_4.add(separator_3, "cell 0 11 2 1,grow");
		
		JLabel lblFaixaEspectral_1 = new JLabel("Faixa Espectral");
		lblFaixaEspectral_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		lblFaixaEspectral_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_4.add(lblFaixaEspectral_1, "cell 0 12 2 1,grow");
		lblFaixaEspectral_1.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblFaixaEspectral_1.setForeground(new Color(211, 211, 211));
		lblFaixaEspectral_1.setBackground(new Color(0, 0, 102));
		lblFaixaEspectral_1.setOpaque(true);
		
		JSpinner spinner = new JSpinner();
		panel_4.add(spinner, "flowx,cell 0 13,grow");
		spinner.setFont(new Font("Tahoma", Font.PLAIN, 11));
		spinner.setBackground(new Color(0, 0, 51));
		spinner.setForeground(new Color(211, 211, 211));
		
		JSpinner spinner_1 = new JSpinner();
		panel_4.add(spinner_1, "flowx,cell 1 13,grow");
		spinner_1.setFont(new Font("Dialog", Font.PLAIN, 11));
		spinner_1.setBackground(new Color(0, 0, 51));
		spinner_1.setForeground(new Color(211, 211, 211));
		
		label = new JLabel("2048");
		label.setFont(new Font("Dialog", Font.PLAIN, 11));
		label.setForeground(new Color(211, 211, 211));
		panel_4.add(label, "cell 0 7 2 1,grow");
		
		JLabel lblA = new JLabel("a");
		panel_4.add(lblA, "cell 0 13,alignx right,growy");
		lblA.setFont(new Font("Dialog", Font.PLAIN, 11));
		lblA.setForeground(new Color(211, 211, 211));
		
		JLabel lblNm = new JLabel("nm");
		panel_4.add(lblNm, "cell 1 13,alignx right,growy");
		lblNm.setFont(new Font("Dialog", Font.PLAIN, 11));
		lblNm.setForeground(new Color(211, 211, 211));
		
		JPanel panel_7 = new JPanel();
		panel_8.add(panel_7, "cell 0 3");
		panel_7.setForeground(new Color(211, 211, 211));
		panel_7.setBackground(new Color(0, 0, 51));
		panel_7.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Solu\u00E7\u00E3o", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(211, 211, 211)));
		panel_7.setLayout(new MigLayout("", "[grow]", "[18px,grow][16,grow]"));
		
		JLabel lblConjunto = new JLabel(db.getMainDB());
		lblConjunto.setForeground(new Color(211, 211, 211));
		lblConjunto.setHorizontalAlignment(SwingConstants.CENTER);
		panel_7.add(lblConjunto, "flowx,cell 0 0,growx,aligny top");
		
		JButton btnEscolher = new JButton("Escolher");
		panel_7.add(btnEscolher, "flowx,cell 0 1,growx");
		
		JButton btnNovo = new JButton("Novo");
		panel_7.add(btnNovo, "cell 0 1");
		
		JButton btnLimpar = new JButton("Limpar");
		panel_7.add(btnLimpar, "cell 0 1,growx");
		btnAdquirir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				if ("enable".equals(event.getActionCommand())) {
					setGet(true);
					if (isReadOnce())
						btnParar.setEnabled(false);
					else {
						btnParar.setEnabled(true);
						btnAdquirir.setEnabled(false);
					}
				} else {
					btnAdquirir.setEnabled(true);
					btnParar.setEnabled(false);
				}
			}
		});
		if( !((String) comboBox.getSelectedItem()).equals("") || comboBox.getSelectedItem() != null) timeToClear(true);
	}
	private boolean clear = false;
	
	private synchronized void waitToClear() throws InterruptedException{ 
		while(!clear) wait();
	}
	
	public synchronized void timeToClear(boolean get){ 
		this.clear = get;
		if(this.clear) notifyAll();
	}
	public boolean isReadOnce() {
		return readOnce;
	}

	public void setReadOnce(boolean readOnce) {
		this.readOnce = readOnce;
	}
	private synchronized void checkIfGet() throws InterruptedException{ //Verifica se há ordem de Adquirir
		while(!get) wait();
	}
	
	public synchronized void setGet(boolean get){ //Controla ordem de adquirir
		this.get = get;
		if(this.get) notifyAll();
	}
	public void launchThread(){
		RSpec = new Thread(new updateChart(), "Spectrometer");
	}
	private Thread checkPort;
	private Thread cleaner;
	private JPanel panel_8;
	//private boolean changeOnPort = false;
	public class updateChart implements Runnable{
		XYSeries series;
		public void run() {
			while(true){
				try {
					System.out.println("CheckIfGet");
					checkIfGet();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				series = new XYSeries(collectionName);
				Chart chart = harvester.getDataset("+");
				series = chart.getXyseries();
				chartCollection.addChart(chart);
				System.out.println(chartCollection.count());
				if(dataset.getSeriesCount() > 0)
					dataset.removeAllSeries();
				dataset.addSeries(series);
				chart.addItemListener(new ItemListener() {
					   public void itemStateChanged(ItemEvent ev) {
						  if(chart.getXyseries() == dataset.getSeries(0)) return;
					      if(ev.getStateChange()==ItemEvent.SELECTED){
					        System.out.println(chart.getTimestamp() + " is selected");
					        dataset.addSeries(chart.getXyseries());
					        chart.setBorderPainted(true);
					        
					      } else if(ev.getStateChange()==ItemEvent.DESELECTED){
					        System.out.println(chart.getTimestamp() + " is not selected");
					        dataset.removeSeries(chart.getXyseries());
					        chart.setBorderPainted(false);
					      }
					   }
					});
				chart.setPicture();
				sliderPanel.add(chart);
				sliderPanel.updateUI();
								
				if (readOnce) {
					setGet(false);
				}
			}
		}
	}
	private class checkForPorts implements Runnable{
		JComboBox<String> comboBox;
		String[] bufferList;
		String[] ports = SerialPortList.getPortNames();
		
		checkForPorts(JComboBox<String> comboBox){
			this.comboBox = comboBox;
		}

		public void run() {
			while(true){
				ports = SerialPortList.getPortNames();
				String actualPort = (String) comboBox.getSelectedItem();
				
				if(!Arrays.equals(ports, bufferList)){ //houve mudança na lista de portas
					boolean sumiu = true;
					if(ports.length > 0){
						for(String port : ports){
							if(port.equals(actualPort) && !port.equals("")) {
								sumiu = false;
							}
						}
					}
					if(sumiu){
						comboBox.removeAllItems();
					}
					for(String port : ports){
						System.out.println("port: "+port+"\nactualPort: "+actualPort);
						if(!port.equals(actualPort)) comboBox.addItem(port);
					}
					bufferList = ports;						
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}else{
					try {
						Thread.sleep(4000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	private class clearComm implements Runnable{
		public void run(){
			while(true){
				try {
					waitToClear();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Time to close comm");
				if(harvester != null){
					lblConectado.setText("Indisponível");
					lblConectado.setForeground(new Color(255, 0, 0));
					btnAdquirir.setEnabled(false);
					harvester.closeComm();
				}
				lblConectado.setText("Conectando");
				lblConectado.setForeground(Color.ORANGE);

				if(harvester.tryConnection(((String) comboBox.getSelectedItem()))){
					lblConectado.setText("Conectado");
					lblConectado.setForeground(new Color(0, 211, 0));
					btnAdquirir.setEnabled(true);
					System.out.println("Conectado");
					launchThread();
					RSpec.setDaemon(true);
					RSpec.start();
				}else{
					lblConectado.setText("Indisponível");
					lblConectado.setForeground(new Color(255, 0, 0));
				}
				timeToClear(false);
			}
	    }
	}
}
