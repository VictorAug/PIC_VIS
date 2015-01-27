package br.iesb.VIS2048.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
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
    /** Atributo btn conectar. */
    JButton btnConectar = new JButton("Conectar");
    /** Atributo lbl conectado. */
    JLabel commLabel;

    /** Atributo label. */
    JLabel label;

    /** Atributo btn adquirir. */
    JButton btnAdquirir;

    /** Atributo combo box. */
    JComboBox<String> commComboBox = new JComboBox<String>();

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

    /** Atributo conexao panel. */
    private JPanel conexaoPanel;

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
	addSolucaoFieldSet();
	syncronizeBtnAdquirir();
	addConexaoTab();

    }

    /**
     * Syncronize btn adquirir.
     */
    private void syncronizeBtnAdquirir() {
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
    }

    /**
     * Adds the conexao tab.
     */
    private void addConexaoTab() {
	conexaoPanel = new JPanel();
	backgroundColor = new Color(0, 0, 51);
	conexaoPanel.setBackground(backgroundColor);
	tabbedPane.addTab(CONEXAO, null, conexaoPanel, null);
	conexaoPanel.setLayout(new MigLayout("", "[][100px][][100px][][grow]", "[][][][][][grow][grow]"));

	JPanel panel_1 = new JPanel();
	panel_1.setForeground(new Color(211, 211, 211));
	panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), CONFIGURACOES_DE_PORTA, TitledBorder.LEADING, TitledBorder.TOP, null, new Color(211, 211, 211)));
	panel_1.setBackground(backgroundColor);
	conexaoPanel.add(panel_1, "cell 0 0 4 6,grow");
	panel_1.setLayout(new MigLayout("", "[62px][62px][62px][86px][86px,grow]", "[21px][21px][21px][21px][21px]"));

	JPanel panel_2 = new JPanel();
	panel_2.setBackground(backgroundColor);
	panel_1.add(panel_2, "cell 0 0 5 1,grow");
	panel_2.setLayout(new MigLayout("", "[][][][][][][][][][grow][][][][][][][]", "[][][][]"));

	JLabel label_3 = new JLabel("Boud Rate");
	label_3.setOpaque(true);
	label_3.setHorizontalAlignment(SwingConstants.CENTER);
	label_3.setForeground(new Color(211, 211, 211));
	label_3.setFont(new Font("Dialog", Font.PLAIN, 12));
	label_3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
	label_3.setBackground(new Color(0, 0, 102));
	panel_2.add(label_3, "cell 0 0 17 1,growx");

	textField = new JTextField();
	textField.setHorizontalAlignment(SwingConstants.CENTER);
	panel_2.add(textField, "cell 1 3 16 1,alignx center,growy");
	textField.addKeyListener(new KeyListener() {

	    @Override
	    public void keyTyped(KeyEvent event) throws NullPointerException {
		try {
		    int key = Integer.parseInt(textField.getText());
		    harvester.setBaudRate(key);
		} catch (Exception e) {
		    System.out.println("Not an integer number");
		}

	    }

	    @Override
	    public void keyReleased(KeyEvent arg0) {
	    }

	    @Override
	    public void keyPressed(KeyEvent arg0) {
	    }
	});
	textField.setColumns(10);

	JPanel panel_3 = new JPanel();
	panel_3.setBackground(backgroundColor);
	panel_1.add(panel_3, "cell 0 1 5 1,grow");
	panel_3.setLayout(new MigLayout("", "[][][][][][][][][][][][][][][grow][][]", "[][][][][]"));

	JLabel label_2 = new JLabel("Data Bits");
	label_2.setOpaque(true);
	label_2.setHorizontalAlignment(SwingConstants.CENTER);
	label_2.setForeground(new Color(211, 211, 211));
	label_2.setFont(new Font("Dialog", Font.PLAIN, 12));
	label_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
	label_2.setBackground(new Color(0, 0, 102));
	panel_3.add(label_2, "cell 0 1 17 1,growx");

	textField_1 = new JTextField();
	textField_1.setHorizontalAlignment(SwingConstants.CENTER);
	textField_1.setColumns(10);
	textField_1.addKeyListener(new KeyListener() {

	    @Override
	    public void keyTyped(KeyEvent event) throws NullPointerException {
		try {
		    int key = Integer.parseInt(textField_1.getText());
		    harvester.setDataBits(key);
		} catch (Exception e) {
		    System.out.println("Not an integer number");
		}
	    }

	    @Override
	    public void keyReleased(KeyEvent arg0) {
	    }

	    @Override
	    public void keyPressed(KeyEvent arg0) {
	    }
	});
	panel_3.add(textField_1, "cell 0 4 17 1,alignx center");

	JPanel panel_4 = new JPanel();
	panel_4.setBackground(backgroundColor);
	panel_1.add(panel_4, "cell 0 2 5 1,grow");
	panel_4.setLayout(new MigLayout("", "[][][][][][][][][][][][][grow][][]", "[][][]"));

	JLabel lblStopBits = new JLabel("Stop Bits");
	lblStopBits.setOpaque(true);
	lblStopBits.setHorizontalAlignment(SwingConstants.CENTER);
	lblStopBits.setForeground(new Color(211, 211, 211));
	lblStopBits.setFont(new Font("Dialog", Font.PLAIN, 12));
	lblStopBits.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
	lblStopBits.setBackground(new Color(0, 0, 102));
	panel_4.add(lblStopBits, "cell 0 0 15 1,growx");

	textField_2 = new JTextField();
	textField_2.setHorizontalAlignment(SwingConstants.CENTER);
	textField_2.setColumns(10);
	textField_2.addKeyListener(new KeyListener() {

	    @Override
	    public void keyTyped(KeyEvent event) throws NullPointerException {
		try {
		    int key = Integer.parseInt(textField_2.getText());
		    harvester.setStopBits(key);
		} catch (Exception e) {
		    System.out.println("Not an integer number");
		}

	    }

	    @Override
	    public void keyReleased(KeyEvent arg0) {
	    }

	    @Override
	    public void keyPressed(KeyEvent arg0) {
	    }
	});
	panel_4.add(textField_2, "cell 0 2 15 1,alignx center,aligny center");

	JPanel panel_5 = new JPanel();
	panel_5.setBackground(backgroundColor);
	panel_1.add(panel_5, "cell 0 3 5 1,grow");
	panel_5.setLayout(new MigLayout("", "[][][][][][][][][][][][][grow][][]", "[][][]"));

	JLabel lblParity = new JLabel("Parity");
	lblParity.setOpaque(true);
	lblParity.setHorizontalAlignment(SwingConstants.CENTER);
	lblParity.setForeground(new Color(211, 211, 211));
	lblParity.setFont(new Font("Dialog", Font.PLAIN, 12));
	lblParity.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
	lblParity.setBackground(new Color(0, 0, 102));
	panel_5.add(lblParity, "cell 0 0 15 1,grow");

	textField_3 = new JTextField();
	textField_3.setHorizontalAlignment(SwingConstants.CENTER);
	textField_3.setColumns(10);
	textField_3.addKeyListener(new KeyListener() {

	    @Override
	    public void keyTyped(KeyEvent event) throws NullPointerException {
		try {
		    int key = Integer.parseInt(textField_3.getText());
		    harvester.setParity(key);
		} catch (Exception e) {
		    System.out.println("Not an integer number");
		}

	    }

	    @Override
	    public void keyReleased(KeyEvent arg0) {
	    }

	    @Override
	    public void keyPressed(KeyEvent arg0) {
	    }
	});
	panel_5.add(textField_3, "cell 0 2 15 1,alignx center");

	JPanel panel_6 = new JPanel();
	panel_6.setForeground(new Color(211, 211, 211));
	panel_6.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), PROTOCOLO, TitledBorder.LEADING, TitledBorder.TOP, null, new Color(211, 211, 211)));
	panel_6.setBackground(backgroundColor);
	conexaoPanel.add(panel_6, "cell 4 1 2 3,grow");
	panel_6.setLayout(new MigLayout("", "[][][][][][][][][][][][][][][grow]", "[][][][][][][][][][][]"));

	JPanel panel_7 = new JPanel();
	panel_7.setBackground(backgroundColor);
	panel_6.add(panel_7, "cell 0 0 15 5,grow");
	panel_7.setLayout(new MigLayout("", "[][][][][][][][][][][][][][][grow][]", "[][][]"));

	JLabel lblNmeroDeAmostras = new JLabel("Número de Amostras");
	lblNmeroDeAmostras.setOpaque(true);
	lblNmeroDeAmostras.setHorizontalAlignment(SwingConstants.CENTER);
	lblNmeroDeAmostras.setForeground(new Color(211, 211, 211));
	lblNmeroDeAmostras.setFont(new Font("Dialog", Font.PLAIN, 12));
	lblNmeroDeAmostras.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
	lblNmeroDeAmostras.setBackground(new Color(0, 0, 102));
	panel_7.add(lblNmeroDeAmostras, "cell 0 0 16 1,grow");

	textField_4 = new JTextField();
	textField_4.setHorizontalAlignment(SwingConstants.CENTER);
	textField_4.setColumns(10);
	textField_4.addKeyListener(new KeyListener() {

	    @Override
	    public void keyTyped(KeyEvent event) throws NullPointerException {
		try {
		    int key = Integer.parseInt(textField_4.getText());
		    harvester.setParity(key);
		} catch (Exception e) {
		    System.out.println("Not an integer number");
		}

	    }

	    @Override
	    public void keyReleased(KeyEvent arg0) {
	    }

	    @Override
	    public void keyPressed(KeyEvent arg0) {
	    }
	});
	panel_7.add(textField_4, "cell 0 2 16 1,alignx center");

	JPanel panel_8 = new JPanel();
	panel_8.setBackground(new Color(0, 0, 51));
	panel_6.add(panel_8, "cell 0 5 15 6,grow");
	panel_8.setLayout(new MigLayout("", "[][][][][][][][][grow][][][][][][grow]", "[][][]"));

	JLabel lblData = new JLabel("Data");
	lblData.setOpaque(true);
	lblData.setHorizontalAlignment(SwingConstants.CENTER);
	lblData.setForeground(new Color(211, 211, 211));
	lblData.setFont(new Font("Dialog", Font.PLAIN, 12));
	lblData.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
	lblData.setBackground(new Color(0, 0, 102));
	panel_8.add(lblData, "cell 0 0 15 1,growx");

	textField_5 = new JTextField();
	textField_5.setHorizontalAlignment(SwingConstants.CENTER);
	textField_5.setColumns(10);
	panel_8.add(textField_5, "cell 0 2 15 1,alignx center");

	JPanel panel_9 = new JPanel();
	panel_9.setForeground(new Color(211, 211, 211));
	panel_9.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Vari\u00E1veis de Estado", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(211, 211, 211)));
	panel_9.setBackground(new Color(0, 0, 51));
	conexaoPanel.add(panel_9, "cell 4 4 2 3,grow");
	panel_9.setLayout(new MigLayout("", "[][][][][][grow][grow]", "[][grow][grow][grow][grow]"));

	JPanel panel_10 = new JPanel();
	panel_10.setBackground(new Color(0, 0, 51));
	panel_9.add(panel_10, "cell 0 0 7 1,grow");
	panel_10.setLayout(new MigLayout("", "[][][][grow][][][][37.00][][][][grow][][grow]", "[28.00][]"));

	JLabel lblSerialPort = new JLabel("Serial Port");
	lblSerialPort.setOpaque(true);
	lblSerialPort.setHorizontalAlignment(SwingConstants.CENTER);
	lblSerialPort.setForeground(new Color(211, 211, 211));
	lblSerialPort.setFont(new Font("Dialog", Font.PLAIN, 12));
	lblSerialPort.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
	lblSerialPort.setBackground(new Color(0, 0, 102));
	panel_10.add(lblSerialPort, "flowy,cell 0 0 14 1,growx");

	JComboBox<String> comboBox = new JComboBox<String>();
	panel_10.add(comboBox, "cell 7 1 2 1,alignx right");

	JButton button = new JButton("Conectar");
	button.setMargin(new Insets(2, 5, 2, 5));
	panel_10.add(button, "cell 9 1 2 1");

	JPanel panel_11 = new JPanel();
	panel_11.setBackground(new Color(0, 0, 51));
	panel_9.add(panel_11, "cell 0 1 7 1,grow");
	panel_11.setLayout(new MigLayout("", "[][][][][][][grow]", "[grow]"));

	JPanel panel_12 = new JPanel();
	panel_12.setBackground(new Color(0, 0, 51));
	panel_11.add(panel_12, "cell 0 0 7 1,grow");
	panel_12.setLayout(new MigLayout("", "[][][][][][grow][][][][][][][grow][][][grow][]", "[][]"));

	JLabel lblConectado = new JLabel("Conectar");
	lblConectado.setOpaque(true);
	lblConectado.setHorizontalAlignment(SwingConstants.CENTER);
	lblConectado.setForeground(new Color(211, 211, 211));
	lblConectado.setFont(new Font("Dialog", Font.PLAIN, 12));
	lblConectado.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
	lblConectado.setBackground(new Color(0, 0, 102));
	panel_12.add(lblConectado, "cell 0 0 17 1,growx");

	JRadioButton rdbtnSim = new JRadioButton("Sim");
	rdbtnSim.setForeground(new Color(211, 211, 211));
	rdbtnSim.setFont(new Font("Tahoma", Font.PLAIN, 11));
	rdbtnSim.setBackground(new Color(0, 0, 51));
	rdbtnSim.setActionCommand("Unico");
	panel_12.add(rdbtnSim, "cell 6 1");

	JRadioButton rdbtnNo = new JRadioButton("Não");
	rdbtnNo.setForeground(new Color(211, 211, 211));
	rdbtnNo.setFont(new Font("Tahoma", Font.PLAIN, 11));
	rdbtnNo.setBackground(new Color(0, 0, 51));
	rdbtnNo.setActionCommand("Unico");
	panel_12.add(rdbtnNo, "cell 9 1");

	JPanel panel_13 = new JPanel();
	panel_13.setBackground(new Color(0, 0, 51));
	panel_9.add(panel_13, "cell 0 2 7 1,grow");
	panel_13.setLayout(new MigLayout("", "[][][][][grow][][][][][][][grow][]", "[][]"));

	JLabel lblOpenPort = new JLabel("Abrir Porta");
	lblOpenPort.setOpaque(true);
	lblOpenPort.setHorizontalAlignment(SwingConstants.CENTER);
	lblOpenPort.setForeground(new Color(211, 211, 211));
	lblOpenPort.setFont(new Font("Dialog", Font.PLAIN, 12));
	lblOpenPort.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
	lblOpenPort.setBackground(new Color(0, 0, 102));
	panel_13.add(lblOpenPort, "cell 0 0 13 1,growx");

	JRadioButton radioButton = new JRadioButton("Sim");
	radioButton.setForeground(new Color(211, 211, 211));
	radioButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
	radioButton.setBackground(new Color(0, 0, 51));
	radioButton.setActionCommand("Unico");
	panel_13.add(radioButton, "cell 6 1");

	JRadioButton radioButton_1 = new JRadioButton("Não");
	radioButton_1.setForeground(new Color(211, 211, 211));
	radioButton_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
	radioButton_1.setBackground(new Color(0, 0, 51));
	radioButton_1.setActionCommand("Unico");
	panel_13.add(radioButton_1, "cell 9 1");

	JPanel panel_14 = new JPanel();
	panel_14.setBackground(new Color(0, 0, 51));
	panel_9.add(panel_14, "cell 0 3 7 2,grow");
	panel_14.setLayout(new MigLayout("", "[][][][][][grow][][][][][][][][grow][]", "[][][]"));

	JLabel lblReadyToGet = new JLabel("Ready To Get");
	lblReadyToGet.setOpaque(true);
	lblReadyToGet.setHorizontalAlignment(SwingConstants.CENTER);
	lblReadyToGet.setForeground(new Color(211, 211, 211));
	lblReadyToGet.setFont(new Font("Dialog", Font.PLAIN, 12));
	lblReadyToGet.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
	lblReadyToGet.setBackground(new Color(0, 0, 102));
	panel_14.add(lblReadyToGet, "cell 0 0 15 1,growx");

	JRadioButton radioButton_2 = new JRadioButton("Sim");
	radioButton_2.setForeground(new Color(211, 211, 211));
	radioButton_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
	radioButton_2.setBackground(new Color(0, 0, 51));
	radioButton_2.setActionCommand("Unico");
	panel_14.add(radioButton_2, "cell 6 1");

	JRadioButton radioButton_3 = new JRadioButton("Não");
	radioButton_3.setForeground(new Color(211, 211, 211));
	radioButton_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
	radioButton_3.setBackground(new Color(0, 0, 51));
	radioButton_3.setActionCommand("Unico");
	panel_14.add(radioButton_3, "cell 9 1");

	JButton btnCancelar = new JButton("Cancelar");
	conexaoPanel.add(btnCancelar, "cell 2 6");

	JButton btnA = new JButton("Aplicar");
	conexaoPanel.add(btnA, "cell 3 6");
	tabbedPane.setEnabledAt(1, true);
	tabbedPane.setBackgroundAt(1, backgroundColor);
	JLabel labTabCalibracao = new JLabel(CONEXAO);
	labTabCalibracao.setUI(new VerticalLabelUI(false));
	tabbedPane.setTabComponentAt(2, labTabCalibracao);
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

	checkPort = new Thread(new checkForPorts());

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
	checkPort = new Thread(new checkForPorts());
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
	tabbedPane.addTab(PCA, null, pcaPanel, null);
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
	cleaner = new Thread(new connector());
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
	espectroPanel.setLayout(new MigLayout("", "[245px:245px:245px]0[grow]0[142px:142px:142px]0", "0[grow]0"));

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
	panel_1.setLayout(new MigLayout("", "0[245px]", "0[][][][]"));
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
	connectionFieldSet.setLayout(new MigLayout("", "[70px:70px:70px][70px:70px:70px][60px:60px:60px]", "[25px:25px:25px]"));

	connectionFieldSet.add(commComboBox, "cell 0 0,grow");
	btnConectar.setMargin(new Insets(2, 5, 2, 5));
	connectionFieldSet.add(btnConectar, "cell 1 0,grow");

	commLabel = new JLabel(INDISPONIVEL);
	connectionFieldSet.add(commLabel, "cell 2 0,alignx center,growy");
	opcoesFieldSet = new JPanel();
	panel_1.add(opcoesFieldSet, "cell 0 2,growx");
	opcoesFieldSet.setBackground(new Color(0, 0, 51));
	opcoesFieldSet.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Op\u00E7\u00F5es", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(211, 211, 211)));
	opcoesFieldSet.setLayout(new MigLayout("", "[100px,grow][100px,grow]", "[20px:20px:20px][20px][20px:20px:20px][20px][20px:20px:20px][20px][20px:20px:20px][30px][20px:20px:20px][20px]"));

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

	JLabel lblNewLabel = new JLabel("Unidade");
	lblNewLabel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
	lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
	opcoesFieldSet.add(lblNewLabel, "cell 0 2 2 1,grow");
	lblNewLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
	lblNewLabel.setForeground(new Color(211, 211, 211));
	lblNewLabel.setBackground(new Color(0, 0, 102));
	lblNewLabel.setOpaque(true);

	JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("Counts");
	opcoesFieldSet.add(rdbtnNewRadioButton_1, "cell 0 3,alignx center,growy");
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
	opcoesFieldSet.add(rdbtnNewRadioButton_2, "cell 1 3,alignx center,growy");
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

	JLabel lblFaixaEspectral = new JLabel("N\u00FAmero de Amostras");
	lblFaixaEspectral.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
	lblFaixaEspectral.setHorizontalAlignment(SwingConstants.CENTER);
	opcoesFieldSet.add(lblFaixaEspectral, "cell 0 4 2 1,grow");
	lblFaixaEspectral.setFont(new Font("Dialog", Font.PLAIN, 12));
	lblFaixaEspectral.setForeground(new Color(211, 211, 211));
	lblFaixaEspectral.setBackground(new Color(0, 0, 102));
	lblFaixaEspectral.setOpaque(true);

	JSlider slider = new JSlider(0, 2048, 2048);
	opcoesFieldSet.add(slider, "flowx,cell 0 5 2 1,grow");
	slider.setBackground(new Color(0, 0, 51));
	slider.addChangeListener(new ChangeListener() {
	    @Override
	    public void stateChanged(ChangeEvent e) {
		label.setText("" + slider.getValue());
	    }
	});
	slider.setSnapToTicks(true);

	JLabel lblTempoDeIntegrao = new JLabel("Tempo de Integra\u00E7\u00E3o");
	lblTempoDeIntegrao.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
	lblTempoDeIntegrao.setHorizontalAlignment(SwingConstants.CENTER);
	opcoesFieldSet.add(lblTempoDeIntegrao, "cell 0 6 2 1,grow");
	lblTempoDeIntegrao.setFont(new Font("Dialog", Font.PLAIN, 12));
	lblTempoDeIntegrao.setForeground(new Color(211, 211, 211));
	lblTempoDeIntegrao.setBackground(new Color(0, 0, 102));
	lblTempoDeIntegrao.setOpaque(true);

	JComboBox<String> comboBox_1 = new JComboBox<String>();
	comboBox_1.setBackground(new Color(0, 0, 51));
	comboBox_1.setForeground(new Color(211, 211, 211));
	opcoesFieldSet.add(comboBox_1, "cell 0 7 2 1,growx");

	JLabel lblFaixaEspectral_1 = new JLabel("Faixa Espectral");
	lblFaixaEspectral_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
	lblFaixaEspectral_1.setHorizontalAlignment(SwingConstants.CENTER);
	opcoesFieldSet.add(lblFaixaEspectral_1, "cell 0 8 2 1,grow");
	lblFaixaEspectral_1.setFont(new Font("Dialog", Font.PLAIN, 12));
	lblFaixaEspectral_1.setForeground(new Color(211, 211, 211));
	lblFaixaEspectral_1.setBackground(new Color(0, 0, 102));
	lblFaixaEspectral_1.setOpaque(true);

	JSpinner spinner = new JSpinner();
	opcoesFieldSet.add(spinner, "flowx,cell 0 9,grow");
	spinner.setFont(new Font("Tahoma", Font.PLAIN, 11));
	spinner.setBackground(new Color(0, 0, 51));
	spinner.setForeground(new Color(211, 211, 211));

	JSpinner spinner_1 = new JSpinner();
	opcoesFieldSet.add(spinner_1, "flowx,cell 1 9,grow");
	spinner_1.setFont(new Font("Dialog", Font.PLAIN, 11));
	spinner_1.setBackground(new Color(0, 0, 51));
	spinner_1.setForeground(new Color(211, 211, 211));

	label = new JLabel("2048");
	label.setFont(new Font("Dialog", Font.PLAIN, 11));
	label.setForeground(new Color(211, 211, 211));
	opcoesFieldSet.add(label, "cell 0 5 2 1,grow");

	JLabel lblA = new JLabel("a");
	opcoesFieldSet.add(lblA, "cell 0 9,alignx right,growy");
	lblA.setFont(new Font("Dialog", Font.PLAIN, 11));
	lblA.setForeground(new Color(211, 211, 211));

	JLabel lblNm = new JLabel("nm");
	opcoesFieldSet.add(lblNm, "cell 1 9,alignx right,growy");
	lblNm.setFont(new Font("Dialog", Font.PLAIN, 11));
	lblNm.setForeground(new Color(211, 211, 211));
	solucaoFieldSet = new JPanel();
	panel_1.add(solucaoFieldSet, "cell 0 3,growx");
	solucaoFieldSet.setForeground(new Color(211, 211, 211));
	solucaoFieldSet.setBackground(new Color(0, 0, 51));
	solucaoFieldSet.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Solu\u00E7\u00E3o", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(211, 211, 211)));
	solucaoFieldSet.setLayout(new MigLayout("", "[grow][grow]", "0[20px:20px:20px][20px:20px:20px]"));

	JLabel lblConjunto = new JLabel(db.getMainDB());
	lblConjunto.setForeground(new Color(211, 211, 211));
	lblConjunto.setHorizontalAlignment(SwingConstants.CENTER);
	solucaoFieldSet.add(lblConjunto, "flowx,cell 0 0 2 1,growx,aligny center");

	JButton btnEscolher = new JButton("Adicionar");
	solucaoFieldSet.add(btnEscolher, "cell 0 1,growx");
	btnEscolher.setFont(new Font("Tahoma", Font.PLAIN, 11));

	btnEscolher.setToolTipText("");
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
	btnSalvar.setFont(new Font("Tahoma", Font.PLAIN, 11));
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
		JOptionPane.showMessageDialog(frame, "Este é o submenu 'Exportar imagem...'. Em breve você poderá visualizar em português.", "Português	(Alt+)", JOptionPane.PLAIN_MESSAGE);
	    }
	});

	JMenuItem englishItem = new JMenuItem("- English");
	englishItem.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent event) {
		JOptionPane.showMessageDialog(frame, "Este é o submenu 'English'. Em breve você poderá visualizar em inglês.", "English	(Alt+e)", JOptionPane.PLAIN_MESSAGE);
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
		for(int i = chartCollection.count(); i>0; i--){
			Component comp = chartCollection.getChart(i-1);
			sliderPanel.remove(comp);
			sliderPanel.add(comp, "cell 0 " + (chartCollection.count()-i+1));
		}
		for(int i = chartCollection.count(); i>0; i--){
			Component comp = chartCollection.getChart(i-1);
			sliderPanel.remove(comp);
			sliderPanel.add(comp, "cell 0 " + (chartCollection.count()-i+1));
		}
		sliderPanel.add(chart, "cell 0 0");// + k++);
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
    private class connector implements Runnable {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
	    while (true) {
		try {
		    checkCommEvent();
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
		if (harvester != null)
		    if (harvester.tryConnection(((String) commComboBox.getSelectedItem()))) {
			commLabel.setText("Conectado");
			commLabel.setForeground(Color.GREEN);
			conectado = true;
			setCommEvent(false);
			launchThread();
			RSpec.setDaemon(true);
			RSpec.start();
			btnAdquirir.setEnabled(true);
		    }
	    }
	}
    }

    /**
     * Check comm event.
     *
     * @throws InterruptedException
     *             the interrupted exception
     */
    private synchronized void checkCommEvent() throws InterruptedException { // Verifica
	while (!commEvent)
	    wait();
    }

    /** Atributo comm event. */
    boolean commEvent = false;

    /** Atributo conectado. */
    boolean conectado = false;

    /** Atributo text field. */
    private JTextField textField;

    /** Atributo text field_1. */
    private JTextField textField_1;

    /** Atributo text field_2. */
    private JTextField textField_2;

    /** Atributo text field_3. */
    private JTextField textField_3;

    /** Atributo background color. */
    private Color backgroundColor;
    private JTextField textField_4;
    private JTextField textField_5;

    /**
     * Atribui o valor comm event.
     *
     * @param commEvent
     *            novo comm event
     */
    public synchronized void setCommEvent(boolean commEvent) { // Controla ordem
							       // de adquirir
	this.commEvent = commEvent;
	if (this.commEvent)
	    notifyAll();
    }

    /**
     * Class checkForPorts.
     */
    private class checkForPorts implements Runnable {

	/** Atributo porta atual. */
	String portaAtual = null;

	/**
	 * Find item.
	 *
	 * @param port
	 *            the port
	 * @return true, se bem-sucedido
	 */
	boolean findItem(String port) {
	    for (int i = 0; i < commComboBox.getItemCount(); i++) {
		if (commComboBox.getItemAt(i).equals(port))
		    return true;
	    }
	    return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
	    commLabel.setText("Indisponível");
	    commLabel.setForeground(new Color(255, 0, 0));
	    btnConectar.setFont(new Font("Tahoma", Font.PLAIN, 11));
	    commComboBox.setFont(new Font("Tahoma", Font.PLAIN, 11));
	    btnConectar.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
		    System.out.println("Iniciando conexão");
		    commLabel.setText("Conectando");
		    commLabel.setForeground(Color.ORANGE);
		    btnConectar.setEnabled(false);
		    setCommEvent(true);
		}
	    });
	    commComboBox.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
		    if (arg0.getModifiers() > 0) {
			System.err.println(arg0.getModifiers());
			System.out.println(commComboBox.getSelectedItem());
			portaAtual = (String) commComboBox.getSelectedItem();
			if (conectado) {
			    System.out.println("Encerra conexão");
			    if (harvester != null)
				harvester.closeComm();
			    conectado = false;
			}
			commLabel.setText("Indisponível");
			commLabel.setForeground(new Color(255, 0, 0));
		    }
		}
	    });
	    while (true) {
		String portList[] = SerialPortList.getPortNames();

		if (portList.length == 0) {
		    btnConectar.setEnabled(false);
		    commComboBox.removeAllItems();
		    portaAtual = null;
		    System.out.println("Nenhuma porta encontrada");
		    commLabel.setText("Indisponível");
		    commLabel.setForeground(new Color(255, 0, 0));
		    if (conectado) {
			System.out.println("Encerra conexão");
			if (harvester != null)
			    harvester.closeComm();
			conectado = false;
		    }
		} else {
		    if (!conectado) {
			btnConectar.setEnabled(true);
		    }
		    commComboBox.removeAllItems();
		    for (String port : portList) {
			commComboBox.addItem(port);
		    }
		    if (portaAtual != null) {
			System.out.println("Há valor");
			if (findItem(portaAtual)) {
			    commComboBox.setSelectedItem(portaAtual);
			} else {
			    System.out.println("Perdeu porta");
			    portaAtual = null;
			    if (conectado) {
				System.out.println("Encerra conexão");
				conectado = false;
			    }
			    commLabel.setText("Indisponível");
			    commLabel.setForeground(new Color(255, 0, 0));
			}
		    } else {
			System.out.println(">> Recebeu um valor");
			portaAtual = (String) commComboBox.getSelectedItem();
		    }
		}
		try {
		    Thread.sleep(5000);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
	    }
	}

    }
}
