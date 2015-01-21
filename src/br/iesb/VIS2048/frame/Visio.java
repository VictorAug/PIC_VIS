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
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
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

import br.iesb.VIS2048.action.AbrirAction;
import br.iesb.VIS2048.action.SalvarAction;
import br.iesb.VIS2048.comm.Harvester;
import br.iesb.VIS2048.database.DBChartCollection;
import br.iesb.VIS2048.database.DBHandler;
import br.iesb.VIS2048.database.DBViewer;

import java.awt.GridLayout;
import java.awt.GridBagLayout;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

// TODO: Auto-generated Javadoc
/**
 * The Class Visio.
 */
public class Visio {
    
    /** The lbl conectado. */
    JLabel lblConectado;
    
    /** The label. */
    JLabel label;
    
    /** The btn adquirir. */
    JButton btnAdquirir;
    
    /** The combo box. */
    JComboBox<String> comboBox;
    
    /** The harvester. */
    Harvester harvester;
    
    /** The baud rate. */
    protected int baudRate = 225200;
    
    /** The data bits. */
    protected int dataBits = 8;
    
    /** The stop bits. */
    protected int stopBits = 0;
    
    /** The parity. */
    protected int parity = 0;
    
    /** The frame. */
    private JFrame frame;
    
    /** The R spec. */
    public Thread RSpec = new Thread(new updateChart(), "Spectrometer");
    
    /** The read once. */
    private boolean readOnce = false;
    
    /** The get. */
    private boolean get = false;
    // public Thread RSpec = new Thread(new updateChart(), "Spectrometer");
    /** The dataset. */
    private XYSeriesCollection dataset;
    
    /** The jfreechart. */
    private JFreeChart jfreechart;
    // private ChartPanel panel;
    /** The counts. */
    private NumberAxis counts;
    
    /** The chart collection. */
    private DBChartCollection chartCollection;
    // private String port;
    /** The scroll pane. */
    private JScrollPane scrollPane;
    
    /** The collection name. */
    private String collectionName = "Counts";
    
    /** The file name. */
    private String fileName;
    
    /** The slider panel. */
    private JPanel sliderPanel;

    /**
     * The main method.
     *
     * @param args the arguments
     */
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

    /**
     * Instantiates a new visio.
     */
    public Visio() {
	initialize();
    }

    /**
     * Initialize.
     */
    private void initialize() {
	DBHandler db = new DBHandler();
	harvester = new Harvester();
	comboBox = new JComboBox<String>();

	frame = new JFrame();
	frame.getContentPane().setBackground(new Color(204, 204, 255));
	frame.setBounds(100, 100, 940, 600);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	JMenuBar menuBar = new JMenuBar();
	frame.setJMenuBar(menuBar);

	menuBar.add(arquivoMenu());
	menuBar.add(opcoesMenu());
	//menuBar.add(ajudaMenu());

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
	panel_8.setLayout(new MigLayout("", "[240px][grow]0[142px:142px:142px]0", "0[grow][grow][grow][80px:n,grow]0"));

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
	panel_1.setLayout(new MigLayout("", "0[grow]0", "0[grow]0"));

	JPanel primeChart = new JPanel();
	primeChart.setBackground(Color.black);
	primeChart.setAlignmentY(0.0f);
	dataset = new XYSeriesCollection();
	jfreechart = ChartFactory.createXYLineChart("Visio", "", collectionName, dataset, PlotOrientation.VERTICAL, true, true, false);
	counts = (NumberAxis) ((XYPlot) jfreechart.getPlot()).getRangeAxis();
	counts.setRange(0, 2500);
	panel = new ChartPanel(jfreechart);
	panel.setBackground(Color.black);
	// panel.setPreferredSize(new Dimension(300, 150));
	((XYPlot) jfreechart.getPlot()).setRangeGridlinePaint(Color.white);
	((XYPlot) jfreechart.getPlot()).setBackgroundPaint(Color.black);
	primeChart.setLayout(new BorderLayout());
	primeChart.add(panel, BorderLayout.CENTER);
	
	jfreechart.setBackgroundPaint(Color.black);
	((XYPlot) jfreechart.getPlot()).getRenderer().setSeriesPaint(0, Color.green);
	((XYPlot) jfreechart.getPlot()).setDomainCrosshairPaint(Color.white);
	((XYPlot) jfreechart.getPlot()).setDomainGridlinePaint(Color.white);
	
	chartCollection = new DBChartCollection();
	panel_1.add(primeChart, "cell 0 0,grow");
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
		    // Zipper zipper = new Zipper();
		    // zipper.generateFileList(null);
		    // zipper.zipIt(null);
		    btnAdquirir.setEnabled(true);
		    btnParar.setEnabled(false);
		    btnParar.setFocusable(false);
		    setGet(false);
		    double time = System.nanoTime();
		    // DBHandler.saveGZipObject(chart.getChartCollection(),
		    // chart.getChartCollection().getFileName());
		    time = System.nanoTime() - time;
		    System.out.println("Time elapsed: " + time + ". Number of Charts: " + chartCollection.count());
		} else {
		    btnAdquirir.setEnabled(false);
		    btnParar.setEnabled(true);
		    btnParar.setFocusable(true);
		    // specPanel.reloadTitle();
		}
	    }
	});

	btnAdquirir.setActionCommand("enable");
	btnAdquirir.setToolTipText("Adquirir leituras");
	
	JPanel panel_3 = new JPanel();
	panel_8.add(panel_3, "cell 2 0 1 4,grow");
	panel_3.setBackground(new Color(0, 0, 51));
	panel_3.setLayout(new MigLayout("", "0[grow]0", "[grow]0"));
		
	scrollPane = new JScrollPane();
	scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	scrollPane.setBorder(null);
	panel_3.add(scrollPane, "cell 0 0,grow");
			
	sliderPanel = new JPanel();
	sliderPanel.setBackground(new Color(0, 0, 51));
	scrollPane.setViewportView(sliderPanel);
	sliderPanel.setLayout(new MigLayout("", "[]", "[]"));

	JPanel panel_6 = new JPanel();
	panel_8.add(panel_6, "cell 0 1,growx");
	panel_6.setBackground(new Color(0, 0, 51));
	panel_6.setForeground(new Color(211, 211, 211));
	panel_6.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Conex\u00E3o", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(211, 211, 211)));
	panel_6.setLayout(new MigLayout("", "[100px,grow][][100px,grow]", "[20px,grow]"));

	checkPort = new Thread(new checkForPorts(comboBox));
	comboBox.setForeground(new Color(211, 211, 211));
	comboBox.setBackground(new Color(0, 0, 51));
	comboBox.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	comboBox.addActionListener(new ActionListener() {
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
	
			if (RSpec.isAlive()) {
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
	panel_8.add(panel_4, "cell 0 2,growx");
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
		    // specPanel.setImage("Counts");
		} else {
		    // specPanel.setImage("mV");
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
		    // specPanel.setImage("mV");
		} else {
		    // specPanel.setImage("Counts");
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

	JSlider slider = new JSlider(0, 2048, 2048);
	panel_4.add(slider, "flowx,cell 0 7 2 1,grow");
	slider.setBackground(new Color(0, 0, 51));
	slider.addChangeListener(new ChangeListener() {
	    @Override
	    public void stateChanged(ChangeEvent e) {
		label.setText("" + slider.getValue());
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
	panel_8.add(panel_7, "cell 0 3,growx");
	panel_7.setForeground(new Color(211, 211, 211));
	panel_7.setBackground(new Color(0, 0, 51));
	panel_7.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Solu\u00E7\u00E3o", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(211, 211, 211)));
	panel_7.setLayout(new MigLayout("", "[grow][grow]", "[18px,grow][16,grow]"));

	JLabel lblConjunto = new JLabel(db.getMainDB());
	lblConjunto.setForeground(new Color(211, 211, 211));
	lblConjunto.setHorizontalAlignment(SwingConstants.CENTER);
	panel_7.add(lblConjunto, "flowx,cell 0 0 2 1,growx,aligny top");
	
		JButton btnEscolher = new JButton("Adicionar");
		panel_7.add(btnEscolher, "cell 0 1,growx");
		
		btnEscolher.setToolTipText("click");
		btnEscolher.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					DBViewer dialog = new DBViewer();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	JButton btnSalvar = new JButton("Salvar");
	panel_7.add(btnSalvar, "cell 1 1,growx");
	btnSalvar.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent event) {
			DBHandler.saveGZipObject(chartCollection, db.getMainDBFileName());
		}
	});
	
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
	if (!"".equals(((String) comboBox.getSelectedItem())))
	    timeToClear(true);
    }

    /**
     * Ajuda menu.
     *
     * @return the j menu
     */
    private JMenu ajudaMenu() {
	JMenuItem instrucoesItem = new JMenuItem("Instru��es de uso");
	instrucoesItem.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent event) {
		JOptionPane.showMessageDialog(frame, "Este � o submenu 'instru��es de uso'", "Instru��es de uso", JOptionPane.PLAIN_MESSAGE);
	    }
	});

	JMenuItem versaoItem = new JMenuItem("Vers�o");
	versaoItem.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent event) {
		JOptionPane.showMessageDialog(frame, "Este � o submenu 'vers�o'.", "Vers�o", JOptionPane.PLAIN_MESSAGE);
	    }
	});

	JMenu ajudaMenu = new JMenu("Ajuda");
	ajudaMenu.setForeground(Color.BLACK);
	ajudaMenu.setMnemonic('h');
	ajudaMenu.add(instrucoesItem);
	ajudaMenu.add(versaoItem);
	return null;
    }

    /**
     * Opcoes menu.
     *
     * @return the j menu
     */
    private JMenu opcoesMenu() {

	JMenuItem fundoItem = new JMenuItem("- Fundo (cor)");
	fundoItem.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent event) {
		// specPanel.setBackgroundPaint(JColorChooser.showDialog(specPanel,
		// "Escolha uma cor de fundo",
		// Color.black));
	    }
	});

	JMenuItem curvaItem = new JMenuItem("- Curva (cor)");
	curvaItem.setMnemonic('c');

	curvaItem.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent event) {
		JOptionPane.showMessageDialog(frame, "Este � o submenu 'Curva'.", "- Curva (cor)", JOptionPane.PLAIN_MESSAGE);
	    }
	});

	JMenuItem gridItem = new JMenuItem("- Grid");
	gridItem.setMnemonic('g');
	gridItem.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent event) {
		JOptionPane.showMessageDialog(frame, "Este � o submenu 'Grid'.", "- Grid", JOptionPane.PLAIN_MESSAGE);
	    }
	});

	JMenuItem autoEscalarItem = new JMenuItem("- Autoescalar");
	autoEscalarItem.setMnemonic('a');
	autoEscalarItem.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent event) {
		JOptionPane.showMessageDialog(frame, "Este � o submenu 'Autoescalar'.", "- Autoescalar", JOptionPane.PLAIN_MESSAGE);
	    }
	});

	JMenu graficoMenu = new JMenu("Gráficos (Alt+g)");
	graficoMenu.setMnemonic('g');
	graficoMenu.add(fundoItem);
	graficoMenu.add(curvaItem);
	graficoMenu.add(gridItem);
	graficoMenu.add(autoEscalarItem);

	JMenuItem portuguesItem = new JMenuItem("- Portugu�s");
	portuguesItem.setMnemonic('p');
	portuguesItem.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent event) {
		JOptionPane.showMessageDialog(frame, "Este � o submenu 'portugues'", "- Portugu�s", JOptionPane.PLAIN_MESSAGE);
	    }
	});

	JMenuItem englishItem = new JMenuItem("- English");
	englishItem.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent event) {
		JOptionPane.showMessageDialog(frame, "Este � o submenu 'english'.", "- English", JOptionPane.PLAIN_MESSAGE);
	    }
	});

	JMenu idiomaMenu = new JMenu("Idioma (Alt+i)");
	idiomaMenu.setMnemonic('i');
	idiomaMenu.add(portuguesItem);
	idiomaMenu.add(englishItem);

	JMenu menuOpcoes = new JMenu("Opções");
	menuOpcoes.setForeground(new Color(0, 0, 0));
	menuOpcoes.setMnemonic('o');
	menuOpcoes.add(graficoMenu);
	menuOpcoes.add(idiomaMenu);
	return menuOpcoes;
    }

    /**
     * Arquivo menu.
     *
     * @return the j menu
     */
    private JMenu arquivoMenu() {
	JMenuItem abrirItem = new JMenuItem("Abrir... (Alt+a)");
	abrirItem.setMnemonic('a');
	abrirItem.addActionListener(new AbrirAction());

	JMenuItem salvarItem = new JMenuItem("Salvar... (Alt+s)");
	salvarItem.setMnemonic('s');
	salvarItem.addActionListener(new SalvarAction(frame));

	JMenuItem exportarItem = new JMenuItem("Exportar (csv)... (Alt+s)");
	exportarItem.setMnemonic('e');
	exportarItem.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent event) {
		JOptionPane.showMessageDialog(frame, "Este é o submenu 'Exportar...'. Em breve voc� poder� exportar arquivos csv.", "Exportar...	(Alt+e)", JOptionPane.PLAIN_MESSAGE);
	    }
	});

	JMenuItem exportarImagemItem = new JMenuItem("Exportar imagem (jpeg, PNG)... (Alt+i)");
	exportarImagemItem.setMnemonic('i');
	exportarImagemItem.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent event) {
		JOptionPane.showMessageDialog(frame, "Este é o submenu 'Exportar imagem...'. Em breve voc� poder� exportar arquivos jpeg, PNG.", "Exportar...	(Alt+e)", JOptionPane.PLAIN_MESSAGE);
	    }
	});

	JMenuItem sairItem = new JMenuItem("Sair");
	sairItem.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent event) {
		System.exit(0);
	    }
	});

	JMenu menuArquivo = new JMenu("Arquivo");
	menuArquivo.add(abrirItem);
	menuArquivo.add(salvarItem);
	menuArquivo.add(exportarItem);
	menuArquivo.add(exportarImagemItem);
	menuArquivo.add(sairItem);
	return menuArquivo;
    }

    /** The clear. */
    private boolean clear = false;

    /**
     * Wait to clear.
     *
     * @throws InterruptedException the interrupted exception
     */
    private synchronized void waitToClear() throws InterruptedException {
	while (!clear)
	    wait();
    }

    /**
     * Time to clear.
     *
     * @param get the get
     */
    public synchronized void timeToClear(boolean get) {
	this.clear = get;
	if (this.clear)
	    notifyAll();
    }

    /**
     * Checks if is read once.
     *
     * @return true, if is read once
     */
    public boolean isReadOnce() {
	return readOnce;
    }

    /**
     * Sets the read once.
     *
     * @param readOnce the new read once
     */
    public void setReadOnce(boolean readOnce) {
	this.readOnce = readOnce;
    }

    /**
     * Check if get.
     *
     * @throws InterruptedException the interrupted exception
     */
    private synchronized void checkIfGet() throws InterruptedException { // Verifica
									 // se
									 // há
									 // ordem
									 // de
									 // Adquirir
	while (!get)
	    wait();
    }

    /**
     * Sets the gets the.
     *
     * @param get the new gets the
     */
    public synchronized void setGet(boolean get) { // Controla ordem de adquirir
	this.get = get;
	if (this.get)
	    notifyAll();
    }

    /**
     * Launch thread.
     */
    public void launchThread() {
	RSpec = new Thread(new updateChart(), "Spectrometer");
    }

    /**
     * Gets the file name.
     *
     * @return the file name
     */
    public String getFileName() {
	return fileName;
    }

    /**
     * Sets the file name.
     *
     * @param fileName the new file name
     */
    public void setFileName(String fileName) {
	this.fileName = fileName;
    }

    /** The check port. */
    private Thread checkPort;
    
    /** The cleaner. */
    private Thread cleaner;
    
    /** The panel_8. */
    private JPanel panel_8;

    // private boolean changeOnPort = false;
    /**
     * The Class updateChart.
     */
    public class updateChart implements Runnable {
	
	/** The series. */
	XYSeries series;
	int j = 0, k = 0;
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
	    while (true) {
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
			if (dataset.getSeriesCount() > 0)
			    dataset.removeAllSeries();
			dataset.addSeries(series);
			chart.addItemListener(new ItemListener() {
			    public void itemStateChanged(ItemEvent ev) {
					if (chart.getXyseries() == dataset.getSeries(0)) return;
					if (ev.getStateChange() == ItemEvent.SELECTED) {
					    System.out.println(chart.getTimestamp() + " is selected");
					    dataset.addSeries(chart.getXyseries());
					    chart.setBorderPainted(true);
		
					} else if (ev.getStateChange() == ItemEvent.DESELECTED) {
					    System.out.println(chart.getTimestamp() + " is not selected");
					    dataset.removeSeries(chart.getXyseries());
					    chart.setBorderPainted(false);
					}
			    }
			});
			chart.setPicture();
			chart.setToolTipText(
					"<html>"
					+ "<p><b>Nome:</b> " + chart.getName() + "</p>"
					+ "<p><b>Data:</b> " + new java.util.Date((long)chart.getTimestamp()) + "</p>"
					+ "<p><b>Resolu��o:</b> " + chart.getNumberOfSamples() + "</p>"
					+ "<p><b>Descri��o:</b> " + chart.getDescription() + "</p>"
					+ "</html>");
			sliderPanel.add(chart, "cell 0 " + k++);
			sliderPanel.updateUI();
			if (readOnce) {
			    setGet(false);
			}
	    }
	}
    }

    /**
     * The Class checkForPorts.
     */
    private class checkForPorts implements Runnable {
	
	/** The combo box. */
	JComboBox<String> comboBox;
	
	/** The buffer list. */
	String[] bufferList;
	
	/** The ports. */
	String[] ports = SerialPortList.getPortNames();

	/**
	 * Instantiates a new check for ports.
	 *
	 * @param comboBox the combo box
	 */
	checkForPorts(JComboBox<String> comboBox) {
	    this.comboBox = comboBox;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
	    while (true) {
		ports = SerialPortList.getPortNames();
		String actualPort = (String) comboBox.getSelectedItem();

		if (!Arrays.equals(ports, bufferList)) { // houve mudança na
							 // lista de portas
		    boolean sumiu = true;
		    if (ports.length > 0) {
			for (String port : ports) {
			    if (port.equals(actualPort) && !"".equals(port)) {
				sumiu = false;
			    }
			}
		    }
		    if (sumiu) {
			comboBox.removeAllItems();
		    }
		    for (String port : ports) {
			System.out.println("port: " + port + "\nactualPort: " + actualPort);
			if (!port.equals(actualPort))
			    comboBox.addItem(port);
		    }
		    bufferList = ports;
		    try {
			Thread.sleep(2000);
		    } catch (InterruptedException e) {
			e.printStackTrace();
		    }
		} else {
		    try {
			Thread.sleep(4000);
		    } catch (InterruptedException e) {
			e.printStackTrace();
		    }
		}
	    }
	}
    }

    /**
     * The Class clearComm.
     */
    private class clearComm implements Runnable {
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
	    while (true) {
		try {
		    waitToClear();
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
		System.out.println("Time to close comm");
		if (harvester != null) {
		    lblConectado.setText("Indisponível");
		    lblConectado.setForeground(new Color(255, 0, 0));
		    btnAdquirir.setEnabled(false);
		    harvester.closeComm();
		}
		lblConectado.setText("Conectando");
		lblConectado.setForeground(Color.ORANGE);

		if (harvester.tryConnection(((String) comboBox.getSelectedItem()))) {
		    lblConectado.setText("Conectado");
		    lblConectado.setForeground(new Color(0, 211, 0));
		    btnAdquirir.setEnabled(true);
		    System.out.println("Conectado");
		    launchThread();
		    RSpec.setDaemon(true);
		    RSpec.start();
		} else {
		    lblConectado.setText("Indisponível");
		    lblConectado.setForeground(new Color(255, 0, 0));
		}
		timeToClear(false);
	    }
	}
    }
}
