package br.iesb.VIS2048.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
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

// TODO: Auto-generated Javadoc
/**
 * Class Visio.
 */
public class Visio extends BaseFrame {

    // ///////////////////////////////////////
    // ///////// ATRIBUTOS ///////////////////
    // ///////////////////////////////////////

    /** Atributo lbl conectado. */
    JLabel lblConectado;

    /** Atributo label. */
    JLabel label;

    /** Atributo btn adquirir. */
    JButton btnAdquirir;

    /** Atributo combo box. */
    JComboBox<String> comboBox = new JComboBox<String>();

    /** Atributo harvester. */
    Harvester harvester = new Harvester();

    /** Atributo baud rate. */
    protected int baudRate = 225200;

    /** Atributo data bits. */
    protected int dataBits = 8;

    /** Atributo stop bits. */
    protected int stopBits = 0;

    /** Atributo parity. */
    protected int parity = 0;

    /** Atributo frame. */
    private JFrame frame;

    /** Atributo R spec. */
    public Thread RSpec = new Thread(new updateChart(), "Spectrometer");

    /** Atributo read once. */
    private boolean readOnce = false;

    /** Atributo get. */
    private boolean get = false;
    // public Thread RSpec = new Thread(new updateChart(), "Spectrometer");
    /** Atributo dataset. */
    private XYSeriesCollection dataset;

    /** Atributo jfreechart. */
    private JFreeChart jfreechart;
    // private ChartPanel panel;
    /** Atributo counts. */
    private NumberAxis counts = new NumberAxis();

    /** Atributo m v. */
    protected NumberAxis mV = new NumberAxis();

    /** Atributo chart collection. */
    private DBChartCollection chartCollection;
    // private String port;
    /** Atributo scroll pane. */
    private JScrollPane scrollPane;

    /** The collection name. */
    private String collectionName = COUNTS;

    /** The file name. */
    private String fileName;

    /** The slider panel. */
    private JPanel sliderPanel;

    /** Atributo db. */
    private DBHandler db = new DBHandler();

    /** The check port. */
    private Thread checkPort;

    /** The cleaner. */
    private Thread cleaner;

    /** The panel_8. */
    private JPanel espectroPanel;

    /** The clear. */
    private boolean clear = false;

    /** Atributo tabbed pane. */
    private JTabbedPane tabbedPane;

    /** Atributo pca panel. */
    private JPanel pcaPanel;

    /** Atributo espectro fieldset. */
    private JPanel espectroFieldset;

    /** Atributo btn parar. */
    private JButton btnParar;

    /** Atributo visio panel. */
    private JPanel visioPanel;

    /** Atributo prime chart. */
    private JPanel primeChart;

    /** Atributo panel. */
    private JPanel panel;

    /** Atributo group unit. */
    private ButtonGroup groupUnit = new ButtonGroup();

    /** Atributo graphics panel. */
    private JPanel graphicsPanel;

    /** Atributo connection field set. */
    private JPanel connectionFieldSet;

    /** Atributo opcoes field set. */
    private JPanel opcoesFieldSet;

    /** Atributo solucao field set. */
    private JPanel solucaoFieldSet;

    /** Atributo group modo op. */
    ButtonGroup groupModoOp = new ButtonGroup();

    /** Atributo flag. */
    private boolean flag;

    // ///////////////////////////////////////
    // ///////// MÉTODOS /////////////////////
    // ///////////////////////////////////////

    /**
     * The main method.
     *
     * @param args
     *            the arguments
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
	initFrame();
	initMenuBar();
	initTabbedPane();

	// ///////////////////
	// / Tab Espectro ////
	// ///////////////////
	initEspectroPanel();
	addEspectroTab();

	// ////////////////////////
	// / Tab Espectro -> FieldSet Espectro ////
	// ////////////////////////
	addEspectroFieldset();

	// ////////////////////////////////////////////////////////
	// / Tab Espectro -> Painel com o gráfico a ser gerado ////
	// ////////////////////////////////////////////////////////
	initVisioPanel();
	checkForPorts();

	// ///////////////////
	// / Tab Pca /////////
	// ///////////////////
	addPcaTab();
	addGraphicsPanel();

	// /////////////////////////////////////////////////////////
	// / Tab Espectro -> FieldSet Conexão, Opções e Solução ////
	// /////////////////////////////////////////////////////////
	addConnectionFieldSet();
	// addOpcoesFieldSet();
	addSolucaoFieldSet();

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
	checkComboBoxInit();
    }

    // ////////////////////////////////////////////////
    // ///////////// MÉTODOS DO INITIALIZE ////////////
    // ////////////////////////////////////////////////
    /**
     * Check combo box init.
     */
    private void checkComboBoxInit() {
	if (!"".equals(((String) comboBox.getSelectedItem()))) {
	    timeToClear(true);
	}
    }

    /**
     * Adds the solucao field set.
     */
    private void addSolucaoFieldSet() {
    }

    /**
     * Adds the connection field set.
     */
    private void addConnectionFieldSet() {

	checkPort = new Thread(new checkForPorts(comboBox));
	comboBox.setForeground(new Color(211, 211, 211));
	comboBox.setBackground(new Color(0, 0, 51));
	comboBox.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	comboBox.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		timeToClear(true);
	    }
	});
    }

    /**
     * Adds the graphics panel.
     */
    private void addGraphicsPanel() {
	graphicsPanel = new JPanel();
	espectroPanel.add(graphicsPanel, "cell 2 0,grow");
	graphicsPanel.setBackground(new Color(0, 0, 51));
	graphicsPanel.setLayout(new MigLayout("", "0[grow]0", "0[grow]0"));

	scrollPane = new JScrollPane();
	scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	scrollPane.setBorder(null);
	graphicsPanel.add(scrollPane, "cell 0 0,grow");

	sliderPanel = new JPanel();
	sliderPanel.setBackground(new Color(0, 0, 51));
	scrollPane.setViewportView(sliderPanel);
	sliderPanel.setLayout(new MigLayout("", "[]", "[]"));
    }

    /**
     * Check for ports.
     */
    private void checkForPorts() {
	checkPort = new Thread(new checkForPorts(comboBox));
	checkPort.setDaemon(true);
	checkPort.start();
	fileName = db.getMainDBFileName();
	collectionName = db.getMainDB();
    }

    /**
     * Adds the pca tab.
     */
    private void addPcaTab() {
	pcaPanel = new JPanel();
	pcaPanel.setBackground(new Color(0, 0, 51));
	tabbedPane.addTab("New tab", null, pcaPanel, null);
	tabbedPane.setEnabledAt(1, true);
	tabbedPane.setBackgroundAt(1, new Color(255, 255, 255));

	JLabel labTab2 = new JLabel(PCA);
	labTab2.setUI(new VerticalLabelUI(false));
	tabbedPane.setTabComponentAt(1, labTab2);
    }

    /**
     * Inits the visio panel.
     */
    private void initVisioPanel() {
	visioPanel = new JPanel();
	visioPanel.setBackground(new Color(0, 0, 51));
	espectroPanel.add(visioPanel, "cell 1 0,grow");
	visioPanel.setLayout(new MigLayout("", "0[grow]0", "0[grow]0"));

	primeChart = new JPanel();
	primeChart.setBackground(Color.black);
	primeChart.setAlignmentY(0.0f);
	dataset = new XYSeriesCollection();
	jfreechart = ChartFactory.createXYLineChart(VISIO, "", collectionName, dataset, PlotOrientation.VERTICAL, true, true, false);
	counts = (NumberAxis) ((XYPlot) jfreechart.getPlot()).getRangeAxis();
	counts.setRange(0, 2500);
	panel = new ChartPanel(jfreechart);
	panel.setBackground(Color.black);
	((XYPlot) jfreechart.getPlot()).setRangeGridlinePaint(Color.white);
	((XYPlot) jfreechart.getPlot()).setBackgroundPaint(Color.black);
	primeChart.setLayout(new BorderLayout());
	primeChart.add(panel, BorderLayout.CENTER);

	jfreechart.setBackgroundPaint(Color.black);
	((XYPlot) jfreechart.getPlot()).getRenderer().setSeriesPaint(0, Color.green);
	((XYPlot) jfreechart.getPlot()).setDomainCrosshairPaint(Color.white);
	((XYPlot) jfreechart.getPlot()).setDomainGridlinePaint(Color.white);

	chartCollection = new DBChartCollection();
	visioPanel.add(primeChart, "cell 0 0,grow");
	cleaner = new Thread(new clearComm());
	cleaner.setDaemon(true);
	cleaner.start();
    }

    /**
     * Adds the espectro tab.
     */
    private void addEspectroTab() {
	tabbedPane.addTab(null, espectroPanel);
	tabbedPane.setEnabledAt(0, true);
	tabbedPane.setBackgroundAt(0, new Color(255, 255, 255));
	espectroPanel.setLayout(new MigLayout("", "0[240px:240px:240px]0[grow]0[142px:142px:142px]0", "0[grow]0"));

	JLabel labTab1 = new JLabel(ESPECTRO);
	labTab1.setUI(new VerticalLabelUI(false));
	tabbedPane.setTabComponentAt(0, labTab1);
    }

    /**
     * Inits the espectro panel.
     */
    private void initEspectroPanel() {
	espectroPanel = new JPanel();
	espectroPanel.setBackground(new Color(0, 0, 51));
    }

    /**
     * Inits the tabbed pane.
     */
    private void initTabbedPane() {
	tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
	tabbedPane.setBackground(new Color(240, 248, 255));
	frame.getContentPane().add(tabbedPane);
    }

    /**
     * Inits the menu bar.
     */
    private void initMenuBar() {
	JMenuBar menuBar = new JMenuBar();
	frame.setJMenuBar(menuBar);
	menuBar.add(arquivoMenu());
	menuBar.add(opcoesMenu());
	menuBar.add(ajudaMenu());
	frame.getContentPane().setLayout(new BorderLayout(0, 0));
    }

    /**
     * Inits the frame.
     */
    private void initFrame() {
	frame = new JFrame();
	frame.getContentPane().setBackground(new Color(204, 204, 255));
	frame.setBounds(100, 100, 940, 600);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Adds the espectro fieldset.
     */
    private void addEspectroFieldset() {

	JPanel panel_1 = new JPanel();
	panel_1.setBackground(new Color(0, 0, 51));
	espectroPanel.add(panel_1, "cell 0 0,grow");
	panel_1.setLayout(new MigLayout("", "[240px]", "[][][][]"));
	espectroFieldset = new JPanel();
	panel_1.add(espectroFieldset, "cell 0 0,grow");
	espectroFieldset.setBackground(new Color(0, 0, 51));
	espectroFieldset.setForeground(new Color(211, 211, 211));
	espectroFieldset.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), ESPECTRO, TitledBorder.LEADING, TitledBorder.TOP, null, new Color(211, 211, 211)));
	espectroFieldset.setLayout(new MigLayout("", "[100px,grow][100px,grow]", "[20px]"));

	btnAdquirir = new JButton(ADQUIRIR);
	btnAdquirir.setFont(new Font("Tahoma", Font.PLAIN, 11));
	btnAdquirir.setEnabled(false);
	espectroFieldset.add(btnAdquirir, "cell 0 0,grow");

	btnParar = new JButton(PARAR);
	btnParar.setFont(new Font("Tahoma", Font.PLAIN, 11));
	espectroFieldset.add(btnParar, "cell 1 0,grow");

	btnParar.setAlignmentX(Component.RIGHT_ALIGNMENT);
	btnParar.setActionCommand("disable");
	btnParar.setEnabled(false);
	btnParar.requestFocus();
	btnParar.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent event) {
		if ("disable".equals(event.getActionCommand())) {
		    btnAdquirir.setEnabled(true);
		    btnParar.setEnabled(false);
		    btnParar.setFocusable(false);
		    setGet(false);
		    double time = System.nanoTime();
		    time = System.nanoTime() - time;
		    System.out.println("Time elapsed: " + time + ". Number of Charts: " + chartCollection.count());
		} else {
		    btnAdquirir.setEnabled(false);
		    btnParar.setEnabled(true);
		    btnParar.setFocusable(true);
		}
	    }
	});

	btnAdquirir.setActionCommand("enable");
	btnAdquirir.setToolTipText(ADQUIRIR_LEITURAS);
	connectionFieldSet = new JPanel();
	panel_1.add(connectionFieldSet, "cell 0 1,growx");
	connectionFieldSet.setBackground(new Color(0, 0, 51));
	connectionFieldSet.setForeground(new Color(211, 211, 211));
	connectionFieldSet.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), CONEXAO, TitledBorder.LEADING, TitledBorder.TOP, null, new Color(211, 211, 211)));
	connectionFieldSet.setLayout(new MigLayout("", "[100px,grow][][100px,grow]", "[20px,grow]"));

	connectionFieldSet.add(comboBox, "cell 0 0,growx,aligny center");

	JButton button = new JButton("Reset");
	button.setToolTipText(RECUPERAR_CONEXÃO);
	button.setFont(new Font("Tahoma", Font.PLAIN, 11));
	button.setEnabled(true);
	connectionFieldSet.add(button, "cell 1 0");
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

	lblConectado = new JLabel(INDISPONIVEL);
	lblConectado.setForeground(new Color(255, 0, 0));
	connectionFieldSet.add(lblConectado, "cell 2 0,alignx center,aligny center");
	opcoesFieldSet = new JPanel();
	panel_1.add(opcoesFieldSet, "cell 0 2,growx");
	opcoesFieldSet.setBackground(new Color(0, 0, 51));
	opcoesFieldSet.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Op\u00E7\u00F5es", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(211, 211, 211)));
	opcoesFieldSet.setLayout(new MigLayout("", "[100px,grow][100px,grow]", "[30px][20px][1px::][30px][20px][1px::][30px][20px][1px::][30px][30px][1px::][30px][20px]"));

	JLabel lblModoDeOperao = new JLabel("Modo de Operação");
	lblModoDeOperao.setHorizontalAlignment(SwingConstants.CENTER);
	lblModoDeOperao.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
	opcoesFieldSet.add(lblModoDeOperao, "cell 0 0 2 1,grow");
	lblModoDeOperao.setFont(new Font("Dialog", Font.PLAIN, 12));
	lblModoDeOperao.setForeground(new Color(211, 211, 211));
	lblModoDeOperao.setBackground(new Color(0, 0, 102));
	lblModoDeOperao.setOpaque(true);

	JRadioButton radioButton = new JRadioButton("\u00DAnico");
	opcoesFieldSet.add(radioButton, "cell 0 1,alignx center,growy");
	radioButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
	radioButton.setForeground(new Color(211, 211, 211));
	radioButton.setBackground(new Color(0, 0, 51));

	JRadioButton rdbtnNewRadioButton = new JRadioButton("Cont\u00EDnuo");
	opcoesFieldSet.add(rdbtnNewRadioButton, "cell 1 1,alignx center,growy");
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
	opcoesFieldSet.add(separator, "cell 0 2 2 1,grow");

	JLabel lblNewLabel = new JLabel("Unidade");
	lblNewLabel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
	lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
	opcoesFieldSet.add(lblNewLabel, "cell 0 3 2 1,grow");
	lblNewLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
	lblNewLabel.setForeground(new Color(211, 211, 211));
	lblNewLabel.setBackground(new Color(0, 0, 102));
	lblNewLabel.setOpaque(true);

	JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("Counts");
	opcoesFieldSet.add(rdbtnNewRadioButton_1, "cell 0 4,alignx center,growy");
	rdbtnNewRadioButton_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
	rdbtnNewRadioButton_1.setForeground(new Color(211, 211, 211));
	rdbtnNewRadioButton_1.setBackground(new Color(0, 0, 51));
	rdbtnNewRadioButton_1.setActionCommand("Counts");
	rdbtnNewRadioButton_1.setSelected(true);

	groupUnit.add(rdbtnNewRadioButton_1);
	rdbtnNewRadioButton_1.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent event) {
		if ("Counts".equals(event.getActionCommand()) && flag) {
		    counts = (NumberAxis) ((XYPlot) jfreechart.getPlot()).getRangeAxis();
		    counts.setRange(0, (mV.getUpperBound() / 3300) * 4096);
		    flag = false;
		}
	    }
	});

	JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("mV");
	opcoesFieldSet.add(rdbtnNewRadioButton_2, "cell 1 4,alignx center,growy");
	rdbtnNewRadioButton_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
	rdbtnNewRadioButton_2.setForeground(new Color(211, 211, 211));
	rdbtnNewRadioButton_2.setBackground(new Color(0, 0, 51));
	rdbtnNewRadioButton_2.setActionCommand("mV");
	groupUnit.add(rdbtnNewRadioButton_2);
	rdbtnNewRadioButton_2.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent event) {
		if ("mV".equals(event.getActionCommand()) && !flag) {
		    mV = (NumberAxis) ((XYPlot) jfreechart.getPlot()).getRangeAxis();
		    mV.setRange(0, (counts.getUpperBound() / 4096) * 3300);
		    flag = true;
		}
	    }
	});

	JSeparator separator_1 = new JSeparator();
	separator_1.setForeground(new Color(0, 0, 51));
	separator_1.setBackground(new Color(0, 0, 51));
	opcoesFieldSet.add(separator_1, "cell 0 5 2 1,grow");

	JLabel lblFaixaEspectral = new JLabel("N\u00FAmero de Amostras");
	lblFaixaEspectral.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
	lblFaixaEspectral.setHorizontalAlignment(SwingConstants.CENTER);
	opcoesFieldSet.add(lblFaixaEspectral, "cell 0 6 2 1,grow");
	lblFaixaEspectral.setFont(new Font("Dialog", Font.PLAIN, 12));
	lblFaixaEspectral.setForeground(new Color(211, 211, 211));
	lblFaixaEspectral.setBackground(new Color(0, 0, 102));
	lblFaixaEspectral.setOpaque(true);

	JSlider slider = new JSlider(0, 2048, 2048);
	opcoesFieldSet.add(slider, "flowx,cell 0 7 2 1,grow");
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
	opcoesFieldSet.add(separator_2, "cell 0 8 2 1,grow");

	JLabel lblTempoDeIntegrao = new JLabel("Tempo de Integra\u00E7\u00E3o");
	lblTempoDeIntegrao.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
	lblTempoDeIntegrao.setHorizontalAlignment(SwingConstants.CENTER);
	opcoesFieldSet.add(lblTempoDeIntegrao, "cell 0 9 2 1,grow");
	lblTempoDeIntegrao.setFont(new Font("Dialog", Font.PLAIN, 12));
	lblTempoDeIntegrao.setForeground(new Color(211, 211, 211));
	lblTempoDeIntegrao.setBackground(new Color(0, 0, 102));
	lblTempoDeIntegrao.setOpaque(true);

	JComboBox<String> comboBox_1 = new JComboBox<String>();
	comboBox_1.setBackground(new Color(0, 0, 51));
	comboBox_1.setForeground(new Color(211, 211, 211));
	opcoesFieldSet.add(comboBox_1, "cell 0 10 2 1,growx");

	JSeparator separator_3 = new JSeparator();
	separator_3.setForeground(new Color(0, 0, 51));
	separator_3.setBackground(new Color(0, 0, 51));
	opcoesFieldSet.add(separator_3, "cell 0 11 2 1,grow");

	JLabel lblFaixaEspectral_1 = new JLabel("Faixa Espectral");
	lblFaixaEspectral_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
	lblFaixaEspectral_1.setHorizontalAlignment(SwingConstants.CENTER);
	opcoesFieldSet.add(lblFaixaEspectral_1, "cell 0 12 2 1,grow");
	lblFaixaEspectral_1.setFont(new Font("Dialog", Font.PLAIN, 12));
	lblFaixaEspectral_1.setForeground(new Color(211, 211, 211));
	lblFaixaEspectral_1.setBackground(new Color(0, 0, 102));
	lblFaixaEspectral_1.setOpaque(true);

	JSpinner spinner = new JSpinner();
	opcoesFieldSet.add(spinner, "flowx,cell 0 13,grow");
	spinner.setFont(new Font("Tahoma", Font.PLAIN, 11));
	spinner.setBackground(new Color(0, 0, 51));
	spinner.setForeground(new Color(211, 211, 211));

	JSpinner spinner_1 = new JSpinner();
	opcoesFieldSet.add(spinner_1, "flowx,cell 1 13,grow");
	spinner_1.setFont(new Font("Dialog", Font.PLAIN, 11));
	spinner_1.setBackground(new Color(0, 0, 51));
	spinner_1.setForeground(new Color(211, 211, 211));

	label = new JLabel("2048");
	label.setFont(new Font("Dialog", Font.PLAIN, 11));
	label.setForeground(new Color(211, 211, 211));
	opcoesFieldSet.add(label, "cell 0 7 2 1,grow");

	JLabel lblA = new JLabel("a");
	opcoesFieldSet.add(lblA, "cell 0 13,alignx right,growy");
	lblA.setFont(new Font("Dialog", Font.PLAIN, 11));
	lblA.setForeground(new Color(211, 211, 211));

	JLabel lblNm = new JLabel("nm");
	opcoesFieldSet.add(lblNm, "cell 1 13,alignx right,growy");
	lblNm.setFont(new Font("Dialog", Font.PLAIN, 11));
	lblNm.setForeground(new Color(211, 211, 211));
	solucaoFieldSet = new JPanel();
	panel_1.add(solucaoFieldSet, "cell 0 3,growx");
	solucaoFieldSet.setForeground(new Color(211, 211, 211));
	solucaoFieldSet.setBackground(new Color(0, 0, 51));
	solucaoFieldSet.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Solu\u00E7\u00E3o", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(211, 211, 211)));
	solucaoFieldSet.setLayout(new MigLayout("", "[grow]", "[18px,grow][16,grow]"));

	JLabel lblConjunto = new JLabel(db.getMainDB());
	lblConjunto.setForeground(new Color(211, 211, 211));
	lblConjunto.setHorizontalAlignment(SwingConstants.CENTER);
	solucaoFieldSet.add(lblConjunto, "flowx,cell 0 0 2 1,growx,aligny top");

	JButton btnEscolher = new JButton("Adicionar");
	solucaoFieldSet.add(btnEscolher, "cell 0 1,growx");

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
	solucaoFieldSet.add(btnSalvar, "cell 1 1,growx");
	btnSalvar.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent event) {
		DBHandler.saveGZipObject(chartCollection, db.getMainDBFileName());
	    }
	});
    }

    // ////////////////////////////
    // // MÉTODOS DO MENU BAR /////
    // ////////////////////////////
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
	return ajudaMenu;
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

	JMenuItem portuguesItem = new JMenuItem("- Português");
	portuguesItem.setMnemonic('p');
	portuguesItem.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent event) {

	    }
	});

	JMenuItem englishItem = new JMenuItem("- English");
	englishItem.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent event) {

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

    // //////////////////////////////////
    // ////// GRÁFICO ///////////////////
    // //////////////////////////////////
    /**
     * Wait to clear.
     *
     * @throws InterruptedException
     *             the interrupted exception
     */
    private synchronized void waitToClear() throws InterruptedException {
	while (!clear)
	    wait();
    }

    /**
     * Time to clear.
     *
     * @param get
     *            the get
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
     * @param readOnce
     *            the new read once
     */
    public void setReadOnce(boolean readOnce) {
	this.readOnce = readOnce;
    }

    /**
     * Check if get.
     *
     * @throws InterruptedException
     *             the interrupted exception
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
     * @param get
     *            the new gets the
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
     * The Class updateChart.
     */
    public class updateChart implements Runnable {

	/** The series. */
	XYSeries series;

	/** Atributo k. */
	int j = 0, k = 0;

	/*
	 * (non-Javadoc)
	 * 
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
			if (chart.getXyseries() == dataset.getSeries(0))
			    return;
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
		chart.setToolTipText("<html>"
		// + "<p><b>Nome:</b> " + chart.getName()
			+ "</p>" + "<p><b>Data:</b> " + new java.util.Date((long) chart.getTimestamp())
			// + "</p>" + "<p><b>Resolução:</b> " +
			// chart.getNumberOfSamples()
			// + "</p>" + "<p><b>Descrição:</b> " +
			// chart.getDescription() + "</p>"
			+ "</html>");
		sliderPanel.add(chart, "cell 0 " + k++);
		sliderPanel.updateUI();
		if (readOnce) {
		    setGet(false);
		}
	    }
	}
    }

    // ////////////////////////
    // /// BANCO DE DADOS /////
    // ////////////////////////
    /**
     * Retorna file name.
     *
     * @return file name
     */
    public String getFileName() {
	return fileName;
    }

    /**
     * Sets the file name.
     *
     * @param fileName
     *            the new file name
     */
    public void setFileName(String fileName) {
	this.fileName = fileName;
    }

    /**
     * Retorna db.
     *
     * @return db
     */
    public DBHandler getDb() {
	return db;
    }

    /**
     * Atribui o valor db.
     *
     * @param db
     *            novo db
     */
    public void setDb(DBHandler db) {
	this.db = db;
    }

    // ///////////////////////////////////
    // ///// COMUNICAÇÃO /////////////////
    // ///////////////////////////////////
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
	 * @param comboBox
	 *            the combo box
	 */
	checkForPorts(JComboBox<String> comboBox) {
	    this.comboBox = comboBox;
	}

	/*
	 * (non-Javadoc)
	 * 
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

	/*
	 * (non-Javadoc)
	 * 
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
