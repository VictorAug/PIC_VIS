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
import br.iesb.VIS2048.comm.Protocol;
import br.iesb.VIS2048.database.DBChartCollection;
import br.iesb.VIS2048.database.DBHandler;
import br.iesb.VIS2048.database.DBViewer;

// TODO: Auto-generated Javadoc
/**
 * Class Visio.
 */
public class Visio {

    // ///////////////////////////////////////
    // ///////// ATRIBUTOS ///////////////////
    // ///////////////////////////////////////
    /** Atributo btn conectar. */
    private JButton btnConectar = new JButton("Conectar");

    /** Atributo lbl conectado. */
    private JLabel commLabel;

    /** Atributo label. */
    private JLabel label;

    /** Atributo btn adquirir. */
    private JButton btnAdquirir;

    /** Atributo combo box. */
    private JComboBox<String> commComboBox = new JComboBox<String>();

    /** Atributo harvester. */
    private Harvester harvester = new Harvester();

    /** Atributo baud rate. */
    private int baudRate = 225200;

    /** Atributo data bits. */
    private int dataBits = 8;

    /** Atributo stop bits. */
    private int stopBits = 0;

    /** Atributo parity. */
    private int parity = 0;

    /** Atributo frame. */
    private JFrame frame;

    /** Atributo R spec. */
    private Thread RSpec = new Thread(new updateChart(), "Spectrometer");

    /** Atributo read once. */
    private boolean readOnce = false;

    /** Atributo get. */
    private boolean get = false;

    /** Atributo dataset. */
    private XYSeriesCollection dataset;

    /** Atributo jfreechart. */
    private JFreeChart jfreechart;
    // private ChartPanel panel;
    /** Atributo counts. */
    private NumberAxis counts = new NumberAxis();

    /** Atributo chart collection. */
    private DBChartCollection chartCollection;
    // private String port;
    /** Atributo scroll pane. */
    private JScrollPane scrollPane;

    /** The collection name. */
    private String collectionName = "Counts";

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
    private ButtonGroup groupModoOp = new ButtonGroup();

    /** Atributo flag. */
    private boolean flag;

    /** Atributo conexao panel. */
    private JPanel conexaoPanel;

    /** Atributo numero amostras. */
    protected int numeroAmostras;

    /** Atributo radio button. */
    private JRadioButton radioButton;

    /** Atributo rdbtn new radio button. */
    private JRadioButton rdbtnNewRadioButton;

    /** Atributo rdbtn new radio button_1. */
    private JRadioButton rdbtnNewRadioButton_1;

    /** Atributo rdbtn new radio button_2. */
    private JRadioButton rdbtnNewRadioButton_2;

    /** Atributo slider. */
    private JSlider slider;

    /** Atributo slider_1. */
    private JSlider slider_1;

    /** Atributo spinner. */
    private JSpinner spinner;

    /** Atributo spinner_1. */
    private JSpinner spinner_1;

    private String confirmDialogText;

    private boolean tradutorPortugues = true;
    private String protocolString = "+";
    // private Chart selectedChart = null;
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
	System.out.println(Protocol.getParameter());
	return;
//	EventQueue.invokeLater(new Runnable() {
//	    public void run() {
//		try {
//		    Visio window = new Visio();
//		    window.frame.setVisible(true);
//		} catch (Exception e) {
//		    e.printStackTrace();
//		}
//	    }
//	});
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
	tabbedPane.addTab("Conexão", null, conexaoPanel, null);
	conexaoPanel.setLayout(new MigLayout("", "[][100px][][100px][][grow]", "[][][][][][grow][grow]"));

	JPanel portSettingsFieldSet = new JPanel();
	portSettingsFieldSet.setForeground(new Color(211, 211, 211));

	titledBorder = new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Configurações de Porta", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(211, 211, 211));
	portSettingsFieldSet.setBorder(titledBorder);

	portSettingsFieldSet.setBackground(backgroundColor);
	conexaoPanel.add(portSettingsFieldSet, "cell 0 0 4 6,grow");
	portSettingsFieldSet.setLayout(new MigLayout("", "[62px][62px][62px][86px][86px,grow]", "[21px][21px][21px][21px][21px]"));

	JPanel panel_2 = new JPanel();
	panel_2.setBackground(backgroundColor);
	portSettingsFieldSet.add(panel_2, "cell 0 0 5 1,grow");
	panel_2.setLayout(new MigLayout("", "[][][][][][][][][][grow][][][][][][][]", "[][][][]"));

	JLabel lblBaudRate = new JLabel("Baud Rate");
	lblBaudRate.setOpaque(true);
	lblBaudRate.setHorizontalAlignment(SwingConstants.CENTER);
	lblBaudRate.setForeground(new Color(211, 211, 211));
	lblBaudRate.setFont(new Font("Dialog", Font.PLAIN, 12));
	lblBaudRate.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
	lblBaudRate.setBackground(new Color(0, 0, 102));
	panel_2.add(lblBaudRate, "cell 0 0 17 1,growx");

	textField = new JTextField();
	textField.setHorizontalAlignment(SwingConstants.CENTER);
	panel_2.add(textField, "cell 1 3 16 1,alignx center,growy");
	textField.addKeyListener(new KeyListener() {

	    @Override
	    public void keyTyped(KeyEvent event) throws NullPointerException {
		try {
		    int key = Integer.parseInt(textField.getText());
		    baudRate = key;
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
	portSettingsFieldSet.add(panel_3, "cell 0 1 5 1,grow");
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
		    dataBits = key;
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
	portSettingsFieldSet.add(panel_4, "cell 0 2 5 1,grow");
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
		    stopBits = key;
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
	portSettingsFieldSet.add(panel_5, "cell 0 3 5 1,grow");
	panel_5.setLayout(new MigLayout("", "[][][][][][][][][][][][][grow][][]", "[][][]"));

	lblParity = new JLabel("Paridade");
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
		    parity = key;
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
	protocoloBorder = new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Protocolo", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(211, 211, 211));
	panel_6.setBorder(protocoloBorder);
	panel_6.setBackground(backgroundColor);
	conexaoPanel.add(panel_6, "cell 4 1 2 3,grow");
	panel_6.setLayout(new MigLayout("", "[][][][][][][][][][][][][][][grow]", "[][][][][][][][][][][]"));

	JPanel panel_7 = new JPanel();
	panel_7.setBackground(backgroundColor);
	panel_6.add(panel_7, "cell 0 0 15 5,grow");
	panel_7.setLayout(new MigLayout("", "[][][][][][][][][][][][][][][grow][]", "[][][]"));

	lblNmeroDeAmostras = new JLabel("Número de Amostras");
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
		    numeroAmostras = key;
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

	titledBorderVariavelEstado = new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Vari\u00E1veis de Estado", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(211, 211, 211));
	panel_9.setBorder(titledBorderVariavelEstado);
	panel_9.setBackground(new Color(0, 0, 51));
	conexaoPanel.add(panel_9, "cell 4 4 2 3,grow");
	panel_9.setLayout(new MigLayout("", "[][][][][][grow][grow]", "[][grow][grow][grow][grow]"));

	JPanel panel_10 = new JPanel();
	panel_10.setBackground(new Color(0, 0, 51));
	panel_9.add(panel_10, "cell 0 0 7 1,grow");
	panel_10.setLayout(new MigLayout("", "[][][][grow][][][][37.00][][][][grow][][grow]", "[28.00][]"));

	lblSerialPort = new JLabel("Porta Serial");
	lblSerialPort.setOpaque(true);
	lblSerialPort.setHorizontalAlignment(SwingConstants.CENTER);
	lblSerialPort.setForeground(new Color(211, 211, 211));
	lblSerialPort.setFont(new Font("Dialog", Font.PLAIN, 12));
	lblSerialPort.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
	lblSerialPort.setBackground(new Color(0, 0, 102));
	panel_10.add(lblSerialPort, "flowy,cell 0 0 14 1,growx");

	JComboBox<String> comboBox = new JComboBox<String>();
	panel_10.add(comboBox, "cell 7 1 2 1,alignx right");

	button = new JButton("Conectar");
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

	lblConectado = new JLabel("Conectar");
	lblConectado.setOpaque(true);
	lblConectado.setHorizontalAlignment(SwingConstants.CENTER);
	lblConectado.setForeground(new Color(211, 211, 211));
	lblConectado.setFont(new Font("Dialog", Font.PLAIN, 12));
	lblConectado.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
	lblConectado.setBackground(new Color(0, 0, 102));
	panel_12.add(lblConectado, "cell 0 0 17 1,growx");

	rdbtnSim = new JRadioButton("Sim");
	rdbtnSim.setForeground(new Color(211, 211, 211));
	rdbtnSim.setFont(new Font("Tahoma", Font.PLAIN, 11));
	rdbtnSim.setBackground(new Color(0, 0, 51));
	rdbtnSim.setActionCommand("Unico");
	panel_12.add(rdbtnSim, "cell 6 1");

	rdbtnNo = new JRadioButton("Não");
	rdbtnNo.setForeground(new Color(211, 211, 211));
	rdbtnNo.setFont(new Font("Tahoma", Font.PLAIN, 11));
	rdbtnNo.setBackground(new Color(0, 0, 51));
	rdbtnNo.setActionCommand("Unico");
	panel_12.add(rdbtnNo, "cell 9 1");

	JPanel panel_13 = new JPanel();
	panel_13.setBackground(new Color(0, 0, 51));
	panel_9.add(panel_13, "cell 0 2 7 1,grow");
	panel_13.setLayout(new MigLayout("", "[][][][][grow][][][][][][][grow][]", "[][]"));

	lblOpenPort = new JLabel("Abrir Porta");
	lblOpenPort.setOpaque(true);
	lblOpenPort.setHorizontalAlignment(SwingConstants.CENTER);
	lblOpenPort.setForeground(new Color(211, 211, 211));
	lblOpenPort.setFont(new Font("Dialog", Font.PLAIN, 12));
	lblOpenPort.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
	lblOpenPort.setBackground(new Color(0, 0, 102));
	panel_13.add(lblOpenPort, "cell 0 0 13 1,growx");

	radioButton2 = new JRadioButton("Sim");
	radioButton2.setForeground(new Color(211, 211, 211));
	radioButton2.setFont(new Font("Tahoma", Font.PLAIN, 11));
	radioButton2.setBackground(new Color(0, 0, 51));
	radioButton2.setActionCommand("Unico");
	panel_13.add(radioButton2, "cell 6 1");

	radioButton_1 = new JRadioButton("Não");
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

	radioButtonSim = new JRadioButton("Sim");
	radioButtonSim.setForeground(new Color(211, 211, 211));
	radioButtonSim.setFont(new Font("Tahoma", Font.PLAIN, 11));
	radioButtonSim.setBackground(new Color(0, 0, 51));
	radioButtonSim.setActionCommand("Unico");
	panel_14.add(radioButtonSim, "cell 6 1");

	radioButtonNao = new JRadioButton("Não");
	radioButtonNao.setForeground(new Color(211, 211, 211));
	radioButtonNao.setFont(new Font("Tahoma", Font.PLAIN, 11));
	radioButtonNao.setBackground(new Color(0, 0, 51));
	radioButtonNao.setActionCommand("Unico");
	panel_14.add(radioButtonNao, "cell 9 1");

	btnLimpar = new JButton("Limpar");
	conexaoPanel.add(btnLimpar, "cell 2 6");
	btnLimpar.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		limpar();
	    }

	});

	btnAplicar = new JButton("Aplicar");
	conexaoPanel.add(btnAplicar, "cell 3 6");
	tabbedPane.setEnabledAt(1, true);
	tabbedPane.setBackgroundAt(1, backgroundColor);
	labTabConexao = new JLabel("Conexão");
	labTabConexao.setUI(new VerticalLabelUI(false));
	tabbedPane.setTabComponentAt(2, labTabConexao);
	btnAplicar.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		confirmDialogText = "As configurações modificadas podem alterar severamente o funcionamento do software. Deseja prosseguir?";
		int showConfirmDialog = JOptionPane.showConfirmDialog(frame, confirmDialogText);
		if (showConfirmDialog > 0) {
		    harvester.updatePortSettings(baudRate, dataBits, stopBits, parity);
		    JOptionPane.showMessageDialog(frame, "Parâmetros alterados com sucesso!");
		}
	    }
	});
    }

    /**
     * Limpar.
     */
    private void limpar() {
	baudRate = 0;
	dataBits = 0;
	stopBits = 0;
	parity = 0;
	textField.setText(null);
	textField_1.setText(null);
	textField_2.setText(null);
	textField_3.setText(null);
	textField_4.setText(null);
	textField_5.setText(null);
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
	sliderPanel.setLayout(new MigLayout("", "0[grow]0", "0[25px:n:25px][0px:n:n]"));

	JPanel panel_1 = new JPanel();
	panel_1.setBackground(new Color(0, 0, 51));
	sliderPanel.add(panel_1, "cell 0 0,grow");
	panel_1.setLayout(new MigLayout("", "[grow][grow]", "[grow]0"));

	btnEditar = new JButton("Editar");
	btnEditar.setMargin(new Insets(2, 3, 2, 3));
	btnEditar.setFont(new Font("Tahoma", Font.PLAIN, 9));
	panel_1.add(btnEditar, "cell 0 0,alignx center,growy");
	btnEditar.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		if (dataset.getSeriesCount() > 0)
		    try {
			ChartEditor editor = new ChartEditor(chartCollection, 0);
			editor.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			editor.setVisible(true);
		    } catch (Exception e) {
			e.printStackTrace();
		    }
	    }
	});

	btnRemover = new JButton("Remover");
	btnRemover.setMargin(new Insets(2, 3, 2, 3));
	btnRemover.setFont(new Font("Tahoma", Font.PLAIN, 9));
	panel_1.add(btnRemover, "cell 1 0,alignx center,growy");

	JPanel panel_2 = new JPanel();
	panel_2.setBackground(new Color(105, 105, 105));
	espectroPanel.add(panel_2, "cell 0 1 3 1,grow");

	lblEspectrometroEmissao = new JLabel("<html><b>VIS2048</b> - Espectrômetro de Emissão</html>");
	lblEspectrometroEmissao.setForeground(new Color(255, 255, 255));
	lblEspectrometroEmissao.setFont(new Font("Tahoma", Font.PLAIN, 17));
	panel_2.add(lblEspectrometroEmissao);
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
	tabbedPane.addTab("PCA", null, pcaPanel, null);
	tabbedPane.setEnabledAt(1, true);
	tabbedPane.setBackgroundAt(1, new Color(255, 255, 255));

	JLabel labTab2 = new JLabel("PCA");
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
	jfreechart = ChartFactory.createXYLineChart("Visio", "", collectionName, dataset, PlotOrientation.VERTICAL, true, true, false);
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
	espectroPanel.setLayout(new MigLayout("", "0[245px:245px:245px,grow]0[grow]0[142px:142px:142px]0", "0[grow]0[30px:30px:30px]0"));

	labTabEspectro = new JLabel("Espectro");
	labTabEspectro.setUI(new VerticalLabelUI(false));
	tabbedPane.setTabComponentAt(0, labTabEspectro);
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

	titledBorderEspectro = new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Espectro", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(211, 211, 211));
	espectroFieldset.setBorder(titledBorderEspectro);
	espectroFieldset.setLayout(new MigLayout("", "[100px,grow][100px,grow]", "[20px]"));

	btnAdquirir = new JButton("Adquirir");

	btnAdquirir.setFont(new Font("Tahoma", Font.PLAIN, 11));
	btnAdquirir.setEnabled(false);
	espectroFieldset.add(btnAdquirir, "cell 0 0,grow");

	btnParar = new JButton("Parar");
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
	btnAdquirir.setToolTipText("Adquirir Leituras");
	connectionFieldSet = new JPanel();
	panel_1.add(connectionFieldSet, "cell 0 1,growx");
	connectionFieldSet.setBackground(new Color(0, 0, 51));
	connectionFieldSet.setForeground(new Color(211, 211, 211));

	titledBorderConexao = new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Conexão", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(211, 211, 211));
	connectionFieldSet.setBorder(titledBorderConexao);
	connectionFieldSet.setLayout(new MigLayout("", "[70px:70px:70px][70px:70px:70px][60px:60px:60px]", "[25px:25px:25px]"));

	connectionFieldSet.add(commComboBox, "cell 0 0,grow");
	btnConectar.setMargin(new Insets(2, 5, 2, 5));
	connectionFieldSet.add(btnConectar, "cell 1 0,grow");

	commLabel = new JLabel("Indisponível");
	connectionFieldSet.add(commLabel, "cell 2 0,alignx center,growy");
	opcoesFieldSet = new JPanel();
	panel_1.add(opcoesFieldSet, "cell 0 2,growx");
	opcoesFieldSet.setBackground(new Color(0, 0, 51));

	titledBorderOpcoes = new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Op\u00E7\u00F5es", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(211, 211, 211));
	opcoesFieldSet.setBorder(titledBorderOpcoes);
	opcoesFieldSet.setLayout(new MigLayout("", "[100px,grow][100px,grow]", "[20px:20px:20px][20px][20px:20px:20px][20px][20px:20px:20px][20px][20px:20px:20px][30px][20px:20px:20px][20px]"));

	lblModoDeOperao = new JLabel("Modo de Operação");
	lblModoDeOperao.setHorizontalAlignment(SwingConstants.CENTER);
	lblModoDeOperao.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
	opcoesFieldSet.add(lblModoDeOperao, "cell 0 0 2 1,grow");
	lblModoDeOperao.setFont(new Font("Dialog", Font.PLAIN, 12));
	lblModoDeOperao.setForeground(new Color(211, 211, 211));
	lblModoDeOperao.setBackground(new Color(0, 0, 102));
	lblModoDeOperao.setOpaque(true);

	rdbtnUnico = new JRadioButton("\u00DAnico");
	opcoesFieldSet.add(rdbtnUnico, "cell 0 1,alignx center,growy");
	rdbtnUnico.setFont(new Font("Tahoma", Font.PLAIN, 11));
	rdbtnUnico.setForeground(new Color(211, 211, 211));
	rdbtnUnico.setBackground(new Color(0, 0, 51));

	rdbtnContinuo = new JRadioButton("Cont\u00EDnuo");
	opcoesFieldSet.add(rdbtnContinuo, "cell 1 1,alignx center,growy");
	rdbtnContinuo.setFont(new Font("Tahoma", Font.PLAIN, 11));
	rdbtnContinuo.setForeground(new Color(211, 211, 211));
	rdbtnContinuo.setBackground(new Color(0, 0, 51));

	groupModoOp.add(rdbtnUnico);
	groupModoOp.add(rdbtnContinuo);

	rdbtnUnico.setActionCommand("Unico");
	rdbtnContinuo.setActionCommand("Continuo");
	rdbtnContinuo.setSelected(true);
	rdbtnUnico.addActionListener(new ActionListener() {
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
	rdbtnContinuo.addActionListener(new ActionListener() {
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

	lblUnidade = new JLabel("Unidade");
	lblUnidade.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
	lblUnidade.setHorizontalAlignment(SwingConstants.CENTER);
	opcoesFieldSet.add(lblUnidade, "cell 0 2 2 1,grow");
	lblUnidade.setFont(new Font("Dialog", Font.PLAIN, 12));
	lblUnidade.setForeground(new Color(211, 211, 211));
	lblUnidade.setBackground(new Color(0, 0, 102));
	lblUnidade.setOpaque(true);

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
		    // counts = (NumberAxis) ((XYPlot)
		    // jfreechart.getPlot()).getRangeAxis();
		    counts.setRange(0, (counts.getUpperBound() * 1.24121212));
		    for (int i = 0; i < chartCollection.count(); i++) {
			for (int j = 0; j < chartCollection.getChart(i).getXyseries().getItemCount(); j++) {
			    XYSeries serie = chartCollection.getChart(i).getXyseries();
			    serie.updateByIndex(j, (serie.getY(j)).doubleValue() * 1.24121212);
			    chartCollection.getChart(i).setMiliVolt(false);
			}
		    }
		    (jfreechart.getXYPlot()).getRangeAxis().setLabel("Counts");
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
		    // mV = (NumberAxis) ((XYPlot)
		    // jfreechart.getPlot()).getRangeAxis();
		    counts.setRange(0, (counts.getUpperBound()) * 0.8056640625);
		    for (int i = 0; i < chartCollection.count(); i++) {
			for (int j = 0; j < chartCollection.getChart(i).getXyseries().getItemCount(); j++) {
			    XYSeries serie = chartCollection.getChart(i).getXyseries();
			    serie.updateByIndex(j, (serie.getY(j)).doubleValue() * 0.8056640625);
			    chartCollection.getChart(i).setMiliVolt(true);
			}
		    }
		    flag = true;
		    (jfreechart.getXYPlot()).getRangeAxis().setLabel("mV");
		}
	    }
	});

	lblNumeroAmostras = new JLabel("N\u00FAmero de Amostras");
	lblNumeroAmostras.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
	lblNumeroAmostras.setHorizontalAlignment(SwingConstants.CENTER);
	opcoesFieldSet.add(lblNumeroAmostras, "cell 0 4 2 1,grow");
	lblNumeroAmostras.setFont(new Font("Dialog", Font.PLAIN, 12));
	lblNumeroAmostras.setForeground(new Color(211, 211, 211));
	lblNumeroAmostras.setBackground(new Color(0, 0, 102));
	lblNumeroAmostras.setOpaque(true);

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

	lblTempoDeIntegrao = new JLabel("Tempo de Integra\u00E7\u00E3o");
	lblTempoDeIntegrao.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
	lblTempoDeIntegrao.setHorizontalAlignment(SwingConstants.CENTER);
	opcoesFieldSet.add(lblTempoDeIntegrao, "cell 0 6 2 1,grow");
	lblTempoDeIntegrao.setFont(new Font("Dialog", Font.PLAIN, 12));
	lblTempoDeIntegrao.setForeground(new Color(211, 211, 211));
	lblTempoDeIntegrao.setBackground(new Color(0, 0, 102));
	lblTempoDeIntegrao.setOpaque(true);

	slider_1 = new JSlider(0, 2048, 2048);
	slider_1.setSnapToTicks(true);
	slider_1.setBackground(new Color(0, 0, 51));
	opcoesFieldSet.add(slider_1, "cell 0 7 2 1,grow");

	lblFaixaEspectral_1 = new JLabel("Faixa Espectral");
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

	lblA = new JLabel("a");
	opcoesFieldSet.add(lblA, "cell 0 9,alignx right,growy");
	lblA.setFont(new Font("Dialog", Font.PLAIN, 11));
	lblA.setForeground(new Color(211, 211, 211));

	JLabel lblNm = new JLabel("nm");
	opcoesFieldSet.add(lblNm, "cell 1 9,alignx right,growy");
	lblNm.setFont(new Font("Dialog", Font.PLAIN, 11));
	lblNm.setForeground(new Color(211, 211, 211));

	JLabel label_1 = new JLabel("2048");
	label_1.setForeground(new Color(211, 211, 211));
	label_1.setFont(new Font("Dialog", Font.PLAIN, 11));
	opcoesFieldSet.add(label_1, "cell 0 7 2 1,grow");
	solucaoFieldSet = new JPanel();
	panel_1.add(solucaoFieldSet, "cell 0 3,growx");
	solucaoFieldSet.setForeground(new Color(211, 211, 211));
	solucaoFieldSet.setBackground(new Color(0, 0, 51));

	titledBorderSolucao = new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Solu\u00E7\u00E3o", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(211, 211, 211));
	solucaoFieldSet.setBorder(titledBorderSolucao);
	solucaoFieldSet.setLayout(new MigLayout("", "[grow][grow]", "0[20px:20px:20px][20px:20px:20px]"));

	slider_1.addChangeListener(new ChangeListener() {
	    @Override
	    public void stateChanged(ChangeEvent e) {
		label_1.setText("" + slider_1.getValue());
	    }
	});

	JLabel lblConjunto = new JLabel(db.getMainDB());
	lblConjunto.setForeground(new Color(211, 211, 211));
	lblConjunto.setHorizontalAlignment(SwingConstants.CENTER);
	solucaoFieldSet.add(lblConjunto, "flowx,cell 0 0 2 1,growx,aligny center");

	btnEscolher = new JButton("Abrir");
	solucaoFieldSet.add(btnEscolher, "cell 0 1,growx");
	btnEscolher.setFont(new Font("Tahoma", Font.PLAIN, 11));

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

	btnSalvar = new JButton("Salvar");
	btnSalvar.setFont(new Font("Tahoma", Font.PLAIN, 11));
	solucaoFieldSet.add(btnSalvar, "cell 1 1,growx");
	btnSalvar.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent event) {
		DBHandler.saveGZipObject(chartCollection, db.getMainDBFileName());
	    }
	});
	disableSpecPanel();
    }

    /**
     * Traduzir.
     */
    public void traduzir() {
	if (tradutorIngles) {
	    stringVersao = "Version";
	    stringInstrucoes = "Instructions for use";
	    stringExportar = "Export...";
	    subMenuExportar = "Coming soon...";
	    instrucoesDeUso = "Coming soon...";
	    menuArquivo.setText("File");
	    menuOpcoes.setText("Options");
	    ajudaMenu.setText("Help");
	    abrirItem.setText("Open... (Alt+a)");
	    salvarItem.setText("Save... (Alt+s)");
	    exportarItem.setText("Export(csv)... (Alt+e)");
	    exportarImagemItem.setText("Export image(jpeg, PNG)... (Alt+i)");
	    sairItem.setText("Exit (Alt+F4)");
	    graficoMenu.setText("Graphics (Alt+g)");
	    idiomaMenu.setText("Language (Alt+i)");
	    curvaItem.setText("- Curve (color)");
	    fundoItem.setText("- Background (color)");
	    instrucoesItem.setText("Instructions for use");
	    versaoItem.setText("Version");
	    autoEscalarItem.setText("- Auto-scalar");
	    lblNumeroAmostras.setText("Number of Samples");
	    lblParity.setText("Parity");
	    lblNmeroDeAmostras.setText("Number of Samples");
	    button.setText("Connect");
	    lblConectado.setText("Connect");
	    btnAdquirir.setText("Acquire");
	    btnAdquirir.setToolTipText("Acquire Readings");
	    tabbedPane.setTitleAt(2, "Connection");
	    btnParar.setText("Stop");
	    btnConectar.setText("Connect");
	    titledBorder.setTitle("Port Settings");
	    commLabel.setText("Unavailable");
	    protocoloBorder.setTitle("Protocol");
	    lblNumeroAmostras.setText("Number of Samples");
	    btnEscolher.setText("Open");
	    btnSalvar.setText("Save");
	    titledBorderSolucao.setTitle("Solution");
	    lblA.setText("to");
	    lblFaixaEspectral_1.setText("Spectral Range");
	    lblTempoDeIntegrao.setText("Integration Time");
	    lblUnidade.setText("Unit");
	    rdbtnUnico.setText("Single");
	    rdbtnContinuo.setText("Continuous");
	    lblModoDeOperao.setText("Operation Mode");
	    titledBorderConexao.setTitle("Connection");
	    titledBorderOpcoes.setTitle("Options");
	    lblEspectrometroEmissao.setText("<html><b>VIS2048</b> - Spectrometer of Emission</html>");
	    titledBorderEspectro.setTitle("Spectrum");
	    labTabEspectro.setText("Spectrum");
	    btnRemover.setText("Remove");
	    btnEditar.setText("Edit");
	    labTabConexao.setText("Connection");
	    confirmDialogText = "The modified settings can severely alter the functioning of the software. Would you like to proceed?";
	    btnLimpar.setText("Clean");
	    btnAplicar.setText("Apply");
	    radioButtonNao.setText("No");
	    radioButtonSim.setText("Yes");
	    radioButton_1.setText("No");
	    radioButton2.setText("Yes");
	    lblOpenPort.setText("Open Port");
	    rdbtnNo.setText("No");
	    rdbtnSim.setText("Yes");
	    titledBorderVariavelEstado.setTitle("State Variables");
	    lblSerialPort.setText("Serial Port");
	    tradutorIngles = false;
	} else if (tradutorPortugues) {
	    stringVersao = "Versão";
	    stringInstrucoes = "Instruções de uso";
	    stringExportar = "Exportar...";
	    subMenuExportar = "Em breve...";
	    instrucoesDeUso = "Em breve...";
	    menuArquivo.setText("Arquivo");
	    menuOpcoes.setText("Opções");
	    ajudaMenu.setText("Ajuda");
	    abrirItem.setText("Abrir... (Alt+a)");
	    salvarItem.setText("Salvar... (Alt+s)");
	    exportarItem.setText("Exportar(csv)... (Alt+e)");
	    exportarImagemItem.setText("Exportar imagem(jpeg, PNG)... (Alt+i)");
	    sairItem.setText("Sair (Alt+F4)");
	    graficoMenu.setText("Gráficos (Alt+g)");
	    idiomaMenu.setText("Idioma (Alt+i)");
	    curvaItem.setText("- Curva (color)");
	    fundoItem.setText("- Fundo (color)");
	    instrucoesItem.setText("Instruções de uso");
	    versaoItem.setText("Versão");
	    lblNumeroAmostras.setText("N\u00FAmero de Amostras");
	    lblParity.setText("Paridade");
	    lblNmeroDeAmostras.setText("Número de Amostras");
	    lblSerialPort.setText("Porta Serial");
	    button.setText("Conectar");
	    lblConectado.setText("Conectar");
	    btnAdquirir.setText("Adquirir");
	    btnAdquirir.setToolTipText("Adquirir Leituras");
	    tabbedPane.setTitleAt(2, "Conexão");
	    btnParar.setText("Parar");
	    btnConectar.setText("Conectar");
	    titledBorder.setTitle("Configurações de Porta");
	    commLabel.setText("Indisponível");
	    protocoloBorder.setTitle("Protocolo");
	    lblNumeroAmostras.setText("N\u00FAmero de Amostras");
	    btnEscolher.setText("Abrir");
	    btnSalvar.setText("Salvar");
	    titledBorderSolucao.setTitle("Solução");
	    lblA.setText("a");
	    lblFaixaEspectral_1.setText("Faixa Espectral");
	    lblTempoDeIntegrao.setText("Tempo de Integra\u00E7\u00E3o");
	    lblUnidade.setText("Unidade");
	    rdbtnUnico.setText("Único");
	    rdbtnContinuo.setText("Contínuo");
	    lblModoDeOperao.setText("Modo de Operação");
	    titledBorderConexao.setTitle("Conexão");
	    titledBorderOpcoes.setTitle("Opções");
	    lblEspectrometroEmissao.setText("<html><b>VIS2048</b> - Espectrômetro de Emissão</html>");
	    titledBorderEspectro.setTitle("Espectro");
	    labTabEspectro.setText("Espectro");
	    btnRemover.setText("Remover");
	    btnEditar.setText("Editar");
	    labTabConexao.setText("Conexão");
	    confirmDialogText = "As configurações modificadas podem alterar severamente o funcionamento do software. Deseja prosseguir?";
	    btnLimpar.setText("Limpar");
	    btnAplicar.setText("Aplicar");
	    radioButtonNao.setText("Não");
	    radioButtonSim.setText("Sim");
	    radioButton_1.setText("Não");
	    radioButton2.setText("Sim");
	    lblOpenPort.setText("Abrir Porta");
	    rdbtnNo.setText("Não");
	    rdbtnSim.setText("Sim");
	    titledBorderVariavelEstado.setTitle("Variáveis de Estado");
	    tradutorPortugues = false;
	}

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
	instrucoesItem = new JMenuItem("Instruções de uso");
	instrucoesDeUso = "Em breve...";
	stringInstrucoes = "Instruções de uso";
	instrucoesItem.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent event) {
		JOptionPane.showMessageDialog(frame, instrucoesDeUso, stringInstrucoes, JOptionPane.PLAIN_MESSAGE);
	    }
	});

	versaoItem = new JMenuItem("Versão");
	versao = "VIS2048 - beta_v1.0";
	stringVersao = "Versão";
	versaoItem.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent event) {
		JOptionPane.showMessageDialog(frame, versao, stringVersao, JOptionPane.PLAIN_MESSAGE);
	    }
	});

	ajudaMenu = new JMenu("Ajuda");
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

	fundoItem = new JMenuItem("- Fundo (cor)");
	fundoItem.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent event) {
		// specPanel.setBackgroundPaint(JColorChooser.showDialog(specPanel,
		// "Escolha uma cor de fundo",
		// Color.black));
	    }
	});

	curvaItem = new JMenuItem("- Curva (cor)");
	curvaItem.setMnemonic('c');

	curvaItem.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent event) {
	    }
	});

	gridItem = new JMenuItem("- Grid");
	gridItem.setMnemonic('g');
	gridItem.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent event) {
	    }
	});

	autoEscalarItem = new JMenuItem("- Autoescalar");
	autoEscalarItem.setMnemonic('a');
	autoEscalarItem.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent event) {
	    }
	});

	graficoMenu = new JMenu("Gráficos (Alt+g)");
	graficoMenu.setMnemonic('g');
	graficoMenu.add(fundoItem);
	graficoMenu.add(curvaItem);
	graficoMenu.add(gridItem);
	graficoMenu.add(autoEscalarItem);

	portuguesItem = new JMenuItem("- Português");
	portuguesItem.setMnemonic('p');
	portuguesItem.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent event) {
		tradutorPortugues = true;
		traduzir();
		// JOptionPane.showMessageDialog(frame,
		// "Este é o submenu 'Exportar imagem...'. Em breve você poderá visualizar em português.",
		// "Português	(Alt+)", JOptionPane.PLAIN_MESSAGE);
	    }
	});

	englishItem = new JMenuItem("- English");
	englishItem.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent event) {
		tradutorIngles = true;
		traduzir();
		// JOptionPane.showMessageDialog(frame,
		// "Este é o submenu 'English'. Em breve você poderá visualizar em inglês.",
		// "English	(Alt+e)", JOptionPane.PLAIN_MESSAGE);
	    }
	});

	idiomaMenu = new JMenu("Idioma (Alt+i)");
	idiomaMenu.setMnemonic('i');
	idiomaMenu.add(portuguesItem);
	idiomaMenu.add(englishItem);

	menuOpcoes = new JMenu("Opções");
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
	abrirItem = new JMenuItem("Abrir... (Alt+a)");
	abrirItem.setMnemonic('a');
	abrirItem.addActionListener(new AbrirAction());

	salvarItem = new JMenuItem("Salvar... (Alt+s)");
	salvarItem.setMnemonic('s');
	salvarItem.addActionListener(new SalvarAction(frame));

	exportarItem = new JMenuItem("Exportar (csv)... (Alt+e)");
	exportarItem.setMnemonic('e');
	subMenuExportar = "Este é o submenu 'Exportar...'. Em breve você poderá exportar arquivos csv.";
	stringExportar = "Exportar...";
	exportarItem.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent event) {
		JOptionPane.showMessageDialog(frame, subMenuExportar, stringExportar, JOptionPane.PLAIN_MESSAGE);
	    }
	});

	exportarImagemItem = new JMenuItem("Exportar imagem (jpeg, PNG)... (Alt+i)");
	exportarImagemItem.setMnemonic('i');
	exportarImagemItem.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent event) {
		JOptionPane.showMessageDialog(frame, subMenuExportar, stringExportar, JOptionPane.PLAIN_MESSAGE);
	    }
	});

	sairItem = new JMenuItem("Sair");
	sairItem.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent event) {
		System.exit(0);
	    }
	});

	menuArquivo = new JMenu("Arquivo");
	menuArquivo.add(abrirItem);
	menuArquivo.add(salvarItem);
	menuArquivo.add(exportarItem);
	menuArquivo.add(exportarImagemItem);
	menuArquivo.add(sairItem);
	return menuArquivo;
    }

    /**
     * Disable spec panel.
     */
    void disableSpecPanel() {
	if (radioButton != null) {
	    radioButton.setEnabled(false);
	}
	if (rdbtnNewRadioButton != null) {
	    rdbtnNewRadioButton.setEnabled(false);
	}
	if (rdbtnNewRadioButton_1 != null) {
	    rdbtnNewRadioButton_1.setEnabled(false);
	}
	if (rdbtnNewRadioButton_2 != null) {
	    rdbtnNewRadioButton_2.setEnabled(false);
	}
	if (slider != null) {
	    slider.setEnabled(false);
	}
	if (slider_1 != null) {
	    slider_1.setEnabled(false);
	}
	if (spinner != null) {
	    spinner.setEnabled(false);
	}
	if (spinner_1 != null) {
	    spinner_1.setEnabled(false);
	}

    }

    /**
     * Enable spec panel.
     */
    void enableSpecPanel() {
	if (radioButton != null) {
	    radioButton.setEnabled(true);
	}
	if (rdbtnNewRadioButton != null) {
	    rdbtnNewRadioButton.setEnabled(true);
	}
	if (rdbtnNewRadioButton_1 != null) {
	    rdbtnNewRadioButton_1.setEnabled(true);
	}
	if (rdbtnNewRadioButton_2 != null) {
	    rdbtnNewRadioButton_2.setEnabled(true);
	}
	if (slider != null) {
	    slider.setEnabled(true);
	}
	if (slider_1 != null) {
	    slider_1.setEnabled(true);
	}
	if (spinner != null) {
	    spinner.setEnabled(true);
	}
	if (spinner_1 != null) {
	    spinner_1.setEnabled(true);
	}

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
		Chart chart = harvester.getDataset(protocolString);
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
		chart.setToolTipText("<html>" + "<p><b>Nome:</b> " + chart.getName() + "</p>" + "<p><b>Data:</b> " + new java.util.Date((long) chart.getTimestamp()) + "</p>" + "<p><b>Resolução:</b> "
			+ chart.getNumberOfSamples() + "</p>" + "<p><b>Descrição:</b> " + chart.getDescription() + "</p>" + "</html>");
		counts.setRange(0, 2500);
		for (int i = chartCollection.count(); i > 0; i--) {
		    Component comp = chartCollection.getChart(i - 1);
		    sliderPanel.remove(comp);
		    sliderPanel.add(comp, "cell 0 " + (chartCollection.count() - i + 2) + ", alignx center, aligny top");
		}
		sliderPanel.add(chart, "cell 0 1, alignx center, aligny top");
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
			enableSpecPanel();
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

    /** Atributo text field_4. */
    private JTextField textField_4;

    /** Atributo text field_5. */
    private JTextField textField_5;

    /** Atributo tradutor. */
    private boolean tradutorIngles = false;

    /** Atributo titled border. */
    private TitledBorder titledBorder;

    /** Atributo protocolo border. */
    private TitledBorder protocoloBorder;

    private JLabel lblNumeroAmostras;

    private JButton btnEscolher;

    private JButton btnSalvar;

    private TitledBorder titledBorderSolucao;

    private JLabel lblA;

    private JLabel lblFaixaEspectral_1;

    private JLabel lblTempoDeIntegrao;

    private JLabel lblUnidade;

    private JRadioButton rdbtnUnico;

    private JRadioButton rdbtnContinuo;

    private JLabel lblModoDeOperao;

    private TitledBorder titledBorderConexao;

    private TitledBorder titledBorderOpcoes;

    private JLabel lblEspectrometroEmissao;

    private TitledBorder titledBorderEspectro;

    private JLabel labTabEspectro;

    private JButton btnRemover;

    private JButton btnEditar;

    private JLabel labTabConexao;

    private JButton btnLimpar;

    private JButton btnAplicar;

    private JRadioButton radioButtonNao;

    private JRadioButton radioButtonSim;

    private JRadioButton radioButton_1;

    private JRadioButton radioButton2;

    private JLabel lblOpenPort;

    private JRadioButton rdbtnNo;

    private JRadioButton rdbtnSim;

    private JLabel lblConectado;

    private JButton button;

    private TitledBorder titledBorderVariavelEstado;

    private JLabel lblSerialPort;

    private JLabel lblNmeroDeAmostras;

    private JLabel lblParity;

    private JMenuItem instrucoesItem;

    private JMenuItem versaoItem;

    private JMenu ajudaMenu;

    private JMenuItem fundoItem;

    private JMenuItem curvaItem;

    private JMenuItem gridItem;

    private JMenuItem autoEscalarItem;

    private JMenu graficoMenu;

    private JMenuItem portuguesItem;

    private JMenuItem englishItem;

    private JMenu idiomaMenu;

    private JMenu menuOpcoes;

    private JMenuItem abrirItem;

    private JMenuItem salvarItem;

    private JMenuItem exportarItem;

    private JMenuItem exportarImagemItem;

    private JMenuItem sairItem;

    private JMenu menuArquivo;

    private String instrucoesDeUso;

    private String versao;

    private String subMenuExportar;

    private String stringExportar;

    private String stringInstrucoes;

    private String stringVersao;

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
     * Retorna numero amostras.
     *
     * @return numero amostras
     */
    public int getNumeroAmostras() {
	return numeroAmostras;
    }

    /**
     * Atribui o valor numero amostras.
     *
     * @param numeroAmostras
     *            novo numero amostras
     */
    public void setNumeroAmostras(int numeroAmostras) {
	this.numeroAmostras = numeroAmostras;
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
		    disableSpecPanel();
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
			disableSpecPanel();
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
		    Thread.sleep(1000);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
	    }
	}

    }
}
