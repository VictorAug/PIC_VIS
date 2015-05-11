package br.iesb.VIS2048.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
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
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;

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

import Jama.Matrix;
import br.iesb.VIS2048.action.AbrirAction;
import br.iesb.VIS2048.action.SalvarAction;
import br.iesb.VIS2048.comm.Harvester;
import br.iesb.VIS2048.database.DBChartCollection;
import br.iesb.VIS2048.database.DBHandler;
import br.iesb.VIS2048.database.DBSelectDialog;
import br.iesb.VIS2048.database.DBViewerDialog;
import br.iesb.VIS2048.pca.PCAPanel;
import br.iesb.VIS2048.pca.PCA;

/**
 * Classe Visio.
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class Visio {

    private JFrame frame;

    private JButton btnConectar = new JButton("Conectar");
    private JButton btnParar;
    private JButton btnAdquirir;
    private JButton btnEscolher;
    private JButton btnSalvar;
    private JButton btnLimpar;
    private JButton btnAplicar;
    private JButton btnStart;

    private JLabel lblConjunto;
    private JLabel commLabel;
    private JLabel label;

    private JComboBox<String> comboBoxX;
    private JComboBox<String> comboBoxY;
    private JComboBox<String> commComboBox = new JComboBox<String>();

    private Harvester harvester = new Harvester();

    private int baudRate = harvester.getBaudRate();
    private int dataBits = harvester.getDataBits();
    private int stopBits = harvester.getStopBits();
    private int parity = harvester.getParity();
    private int numeroAmostras = harvester.getNumberOfSamples();

    private boolean readOnce = false;
    private boolean get = false;
    private boolean flag;
    private boolean tradutorPortugues = true;
    private boolean tradutorIngles = false;
    private boolean commEvent = false;
    private boolean conectado = false;

    private JRadioButton radioButton;
    private JRadioButton rdbtnNewRadioButton;
    private JRadioButton rdbtnNewRadioButton_1;
    private JRadioButton rdbtnNewRadioButton_2;

    private JMenu arquivoMenu;
    private JMenu ajudaMenu;
    private JMenu graficoMenu;
    private JMenu idiomaMenu;
    private JMenu menuOpcoes;

    private JTextField textFieldBaudRate;
    private JTextField textFieldDataBits;
    private JTextField textFieldStopBits;
    private JTextField textFieldParity;
    private JTextField textFieldNumeroAmostras;

    private TitledBorder titledBorderPortSettings;
    private TitledBorder protocoloBorder;
    private TitledBorder titledBorderSolucao;
    private TitledBorder titledBorderConexao;
    private TitledBorder titledBorderOpcoes;
    private TitledBorder titledBorderEspectro;
    private TitledBorder titledBorderVariavelEstado;

    private JLabel lblNumeroAmostras;
    private JLabel lblA;
    private JLabel lblFaixaEspectral_1;
    private JLabel lblTempoDeIntegrao;
    private JLabel lblUnidade;
    private JLabel lblModoDeOperao;
    private JLabel lblEspectrometroEmissao;
    private JLabel labTabEspectro;
    private JLabel labTabConexao;
    private JLabel lblNmeroDeAmostras;
    private JLabel lblParity;
    private JLabel lblRealizarPca;
    private JLabel lblComponentsValue;

    private JRadioButton rdbtnUnico;
    private JRadioButton rdbtnContinuo;
    private JRadioButton rdbtnL;
    private JRadioButton rdbtnT;

    private JMenuItem instrucoesItem;
    private JMenuItem versaoItem;
    private JMenuItem fundoItem;
    private JMenuItem curvaItem;
    private JMenuItem autoEscalarItem;
    private JMenuItem abrirItem;
    private JMenuItem salvarItem;
    private JMenuItem exportarItem;
    private JMenuItem exportarImagemItem;
    private JMenuItem sairItem;

    private String instrucoesDeUso;
    private String versao;
    private String subMenuExportar;
    private String stringExportar;
    private String stringInstrucoes;
    private String stringVersao;
    private String collectionName = "Counts";
    private String confirmDialogText;

    private JList<String> modelList;

    private JPanel panel_17;
    private JPanel panel_16;
    private JPanel espectroPanel;
    private JPanel conexaoPanel;

    private JSlider slider;
    private JSlider sliderTempoDeIntegracao;

    private JSpinner spinner;
    private JSpinner spinner_1;

    private XYSeriesCollection dataSet;
    private JFreeChart jfreechart;
    private NumberAxis counts = new NumberAxis();
    private DBChartCollection chartCollection;
    private DBHandler dbHandler;
    private Thread checkPort;
    private JTabbedPane tabbedPane;
    private DefaultListModel model = new DefaultListModel();
    private PCA pca = null;
    private Matrix pcaMatrix = null;

    /**
     * Método main.
     *
     * @param args
     *            the arguments
     */
    public static void main(String[] args) {
	System.setProperty("sun.java2d.d3d", "false");
	// System.out.println(Protocol.getParameter(16, 1000, 1, -1));
	EventQueue.invokeLater(() -> {
	    try {
		Visio window = new Visio();
		window.frame.setVisible(true);

	    } catch (Exception e) {
		e.printStackTrace();
	    }
	});
    }

    /**
     * Instancia um novo objeto <code>Visio</code>.
     * 
     * @wbp.parser.entryPoint
     */
    private Visio() {
	initialize();
    }

    /**
     * Constroi a aplicação.
     */
    private void initialize() {
	initFrame();
	initMenuBar();
	initTabbedPane();

	// Tab Espectro
	initEspectroPanel();
	addEspectroTab();

	// Tab Espectro -> FieldSet Espectro
	addEspectroFieldset();

	// Tab Espectro -> Painel com o gráfico a ser gerado
	initVisioPanel();
	checkForPorts();

	// Tab Pca
	addPcaTab();
	addGraphicsPanel();

	// Tab Espectro -> FieldSet Conexão, Opções e Solução
	syncronizeBtnAdquirir();
	addConexaoTab();
	
	extracted();
    }

    private void extracted() {
	dbHandler = new DBHandler();
	final DBSelectDialog selector = new DBSelectDialog(dbHandler);
	selector.setVisible(true);
	selector.setModalityType(Dialog.ModalityType.DOCUMENT_MODAL);
	selector.setAlwaysOnTop(true);
	dbHandler.setMainDB(dbHandler.getMainDB());
	collectionName = DBHandler.getDBFileCollection() + dbHandler.getMainDB();
	lblConjunto.setText(DBHandler.getDBFileCollection() + dbHandler.getMainDB());
    }

    /**
     * Sincroniza a ação do botão Adquirir.
     */
    private void syncronizeBtnAdquirir() {
	checkPort = new Thread(new CheckForPorts());
	btnAdquirir.addActionListener((ActionEvent actionEvent) -> {
	    setGet(true);
	    if (isReadOnce())
		btnParar.setEnabled(false);
	    else {
		btnParar.setEnabled(true);
		btnAdquirir.setEnabled(false);
	    }
	});
    }

    /**
     * Adiciona a tab de configuração de parâmetros da conexão.
     */
    private void addConexaoTab() {
	Color backgroundColor = new Color(0, 0, 51);
	conexaoPanel = new JPanel();
	conexaoPanel.setBackground(backgroundColor);
	tabbedPane.addTab("Conexão", null, conexaoPanel, null);
	conexaoPanel.setLayout(new MigLayout("", "[][100px][][100px][][grow]", "[][][][][][grow][grow]"));

	JPanel portSettingsFieldSet = new JPanel();
	portSettingsFieldSet.setForeground(new Color(211, 211, 211));

	titledBorderPortSettings = new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Configurações de Porta", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(211, 211, 211));
	portSettingsFieldSet.setBorder(titledBorderPortSettings);

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

	textFieldBaudRate = new JTextField();
	textFieldBaudRate.setHorizontalAlignment(SwingConstants.CENTER);
	textFieldBaudRate.setText(String.valueOf(baudRate));
	panel_2.add(textFieldBaudRate, "cell 1 3 16 1,alignx center,growy");
	textFieldBaudRate.addKeyListener(new KeyListener() {
	    @Override
	    public void keyTyped(KeyEvent event) throws NullPointerException {
	    }

	    @Override
	    public void keyReleased(KeyEvent arg0) {
		try {
		    baudRate = Integer.parseInt(textFieldBaudRate.getText());
		} catch (Exception e) {
		    System.out.println("Not an integer number");
		}
	    }

	    @Override
	    public void keyPressed(KeyEvent arg0) {
	    }
	});
	baudRate = Integer.parseInt(textFieldBaudRate.getText());
	textFieldBaudRate.setColumns(10);

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

	textFieldDataBits = new JTextField();
	textFieldDataBits.setHorizontalAlignment(SwingConstants.CENTER);
	textFieldDataBits.setColumns(10);
	textFieldDataBits.setText(String.valueOf(dataBits));
	textFieldDataBits.addKeyListener(new KeyListener() {
	    @Override
	    public void keyTyped(KeyEvent event) throws NullPointerException {
	    }

	    @Override
	    public void keyReleased(KeyEvent arg0) {
		try {
		    dataBits = Integer.parseInt(textFieldDataBits.getText());
		} catch (Exception e) {
		    System.out.println("Not an integer number");
		}
	    }

	    @Override
	    public void keyPressed(KeyEvent arg0) {
	    }
	});
	panel_3.add(textFieldDataBits, "cell 0 4 17 1,alignx center");

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

	textFieldStopBits = new JTextField();
	textFieldStopBits.setHorizontalAlignment(SwingConstants.CENTER);
	textFieldStopBits.setColumns(10);
	textFieldStopBits.setText(String.valueOf(stopBits));
	textFieldStopBits.addKeyListener(new KeyListener() {
	    @Override
	    public void keyTyped(KeyEvent event) throws NullPointerException {
	    }

	    @Override
	    public void keyReleased(KeyEvent arg0) {
		try {
		    stopBits = Integer.parseInt(textFieldStopBits.getText());
		} catch (Exception e) {
		    System.out.println("Not an integer number");
		}
	    }

	    @Override
	    public void keyPressed(KeyEvent arg0) {
	    }
	});
	panel_4.add(textFieldStopBits, "cell 0 2 15 1,alignx center,aligny center");

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

	textFieldParity = new JTextField();
	textFieldParity.setHorizontalAlignment(SwingConstants.CENTER);
	textFieldParity.setColumns(10);
	textFieldParity.setText(String.valueOf(parity));
	textFieldParity.addKeyListener(new KeyListener() {
	    @Override
	    public void keyTyped(KeyEvent event) throws NullPointerException {
	    }

	    @Override
	    public void keyReleased(KeyEvent arg0) {
		try {
		    parity = Integer.parseInt(textFieldParity.getText());
		} catch (Exception e) {
		    System.out.println("Not an integer number");
		}
	    }

	    @Override
	    public void keyPressed(KeyEvent arg0) {
	    }
	});
	panel_5.add(textFieldParity, "cell 0 2 15 1,alignx center");

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

	textFieldNumeroAmostras = new JTextField();
	textFieldNumeroAmostras.setHorizontalAlignment(SwingConstants.CENTER);
	textFieldNumeroAmostras.setColumns(10);
	textFieldNumeroAmostras.setText(String.valueOf(numeroAmostras));
	textFieldNumeroAmostras.addKeyListener(new KeyListener() {
	    @Override
	    public void keyTyped(KeyEvent event) throws NullPointerException {
	    }

	    @Override
	    public void keyReleased(KeyEvent arg0) {
		try {
		    numeroAmostras = Integer.parseInt(textFieldNumeroAmostras.getText());
		} catch (Exception e) {
		    System.out.println("Not an integer number");
		}
	    }

	    @Override
	    public void keyPressed(KeyEvent arg0) {
	    }
	});
	panel_7.add(textFieldNumeroAmostras, "cell 0 2 16 1,alignx center");

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

	JPanel panel_11 = new JPanel();
	panel_11.setBackground(new Color(0, 0, 51));
	panel_9.add(panel_11, "cell 0 1 7 1,grow");
	panel_11.setLayout(new MigLayout("", "[][][][][][][grow]", "[grow]"));

	JPanel panel_12 = new JPanel();
	panel_12.setBackground(new Color(0, 0, 51));
	panel_11.add(panel_12, "cell 0 0 7 1,grow");
	panel_12.setLayout(new MigLayout("", "[][][][][][grow][][][][][][][grow][][][grow][]", "[][]"));

	btnLimpar = new JButton("Limpar");
	conexaoPanel.add(btnLimpar, "cell 2 6");
	btnLimpar.addActionListener((ActionEvent actionEvent) -> limpar());

	btnAplicar = new JButton("Aplicar");
	conexaoPanel.add(btnAplicar, "cell 3 6");
	tabbedPane.setEnabledAt(1, true);
	tabbedPane.setBackgroundAt(1, Color.LIGHT_GRAY);
	labTabConexao = new JLabel("Conexão");
	labTabConexao.setUI(new VerticalLabelUI(false));
	tabbedPane.setTabComponentAt(2, labTabConexao);
	tabbedPane.setBackgroundAt(2, Color.GRAY);

	btnAplicar.addActionListener((ActionEvent actionEvent) -> {
	    this.confirmDialogText = "As configurações modificadas podem alterar severamente o funcionamento do software. Deseja prosseguir?";
	    if (harvester.isSerialPortNull()) {
		JOptionPane.showMessageDialog(frame, "Conecte o dispositivo.");
	    } else if (JOptionPane.showConfirmDialog(frame, confirmDialogText) == 0 && !harvester.verifyParameters(baudRate, dataBits, stopBits, parity)) {
		harvester.updatePortSettings(baudRate, dataBits, stopBits, parity);
		harvester.setNumberOfSamples(numeroAmostras);
		JOptionPane.showMessageDialog(frame, "Parâmetros alterados com sucesso!");
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
	textFieldBaudRate.setText(null);
	textFieldDataBits.setText(null);
	textFieldStopBits.setText(null);
	textFieldParity.setText(null);
	textFieldNumeroAmostras.setText(null);
    }

    /**
     * Adds the graphics panel.
     */
    private void addGraphicsPanel() {
	JPanel panel_2 = new JPanel();
	panel_2.setBackground(new Color(105, 105, 105));
	espectroPanel.add(panel_2, "cell 0 1 2 1,grow");
	lblEspectrometroEmissao = new JLabel("<html><b>VIS2048</b> - Espectrômetro de Emissão</html>");
	lblEspectrometroEmissao.setForeground(new Color(255, 255, 255));
	lblEspectrometroEmissao.setFont(new Font("Tahoma", Font.PLAIN, 17));
	panel_2.add(lblEspectrometroEmissao);
    }

    /**
     * Check for ports.
     */
    private void checkForPorts() {
	checkPort = new Thread(new CheckForPorts());
	checkPort.setDaemon(true);
	checkPort.start();
    }

    /**
     * Pca.
     *
     * @param M
     *            the m
     * @param comp
     *            the comp
     */
    void PCA(Matrix M, int comp) {
	Matrix P = M.copy();
//	Matrix Q = P.copy();

	for (int i = 0; i < M.getRowDimension(); i++)
	    for (int j = 0; j < M.getColumnDimension(); j++)
		P.set(i, j, Math.random() * M.get(i, j));

//	Q = P.transpose();
	for (int i = 1; i < comp + 1; i++) {
	    comboBoxX.addItem(String.valueOf(i));
	    comboBoxY.addItem(String.valueOf(i));
	}

    }

    /**
     * Adds the pca tab.
     */
    private void addPcaTab() {
	JPanel pcaPanel = new JPanel();
	pcaPanel.setBackground(new Color(0, 0, 51));
	tabbedPane.addTab("PCA", null, pcaPanel, null);
	tabbedPane.addChangeListener(e -> {
	    dbHandler.updateCollectionList();
	    ArrayList collectionList = dbHandler.getCollectionList();
	    model.clear();
	    for (int i = 0; i < collectionList.size(); i++) {
		model.add(i, collectionList.get(i));
	    }
	});
	pcaPanel.setLayout(new MigLayout("", "0[245px:245px:245px,grow]0[grow]0", "0[grow]0[30px:30px:30px]0"));

	JPanel panel_8 = new JPanel();
	panel_8.setBackground(new Color(0, 0, 51));
	pcaPanel.add(panel_8, "cell 0 0,growx,aligny top");
	panel_8.setLayout(new MigLayout("", "[grow]", "[grow][][]"));

	JPanel panel_15 = new JPanel();
	panel_15.setForeground(Color.WHITE);
	panel_15.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Amostras", TitledBorder.LEADING, TitledBorder.TOP, null, Color.WHITE));
	panel_15.setBackground(new Color(0, 0, 51));
	panel_8.add(panel_15, "cell 0 0,grow");
	panel_15.setLayout(new MigLayout("", "[20px][90px][10px]", "[][120px,grow][][]"));

	JLabel lblSelecioneOsArquivos = new JLabel("Selecione os Arquivos: (Ctrl + Click)");
	lblSelecioneOsArquivos.setForeground(Color.WHITE);
	panel_15.add(lblSelecioneOsArquivos, "cell 0 0 3 1");

	modelList = new JList(model);
	JScrollPane jscroll = new JScrollPane(modelList);
	modelList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	modelList.addListSelectionListener(e -> {
	    if (!modelList.getSelectedValuesList().isEmpty())
		btnStart.setEnabled(true);
	    else
		btnStart.setEnabled(false);
	});
	panel_15.add(jscroll, "cell 0 1 3 1,grow");

	JLabel lblComponentes = new JLabel("Componentes:");
	lblComponentes.setForeground(Color.WHITE);
	panel_15.add(lblComponentes, "cell 0 2");

	JSlider sliderComponentes = new JSlider(1, 20, 10);
	sliderComponentes.setMinimumSize(new Dimension(90, 26));
	sliderComponentes.setMaximumSize(new Dimension(90, 26));
	sliderComponentes.setBackground(new Color(0, 0, 51));
	lblComponentsValue = new JLabel(String.valueOf(sliderComponentes.getValue()));
	sliderComponentes.addChangeListener(arg0 -> lblComponentsValue.setText(String.valueOf(sliderComponentes.getValue())));
	lblComponentsValue.setMinimumSize(new Dimension(25, 14));
	lblComponentsValue.setMaximumSize(new Dimension(25, 14));
	lblComponentsValue.setForeground(Color.WHITE);

	panel_15.add(lblComponentsValue, "cell 2 2");
	panel_15.add(sliderComponentes, "flowx,cell 1 2,growx");

	lblRealizarPca = new JLabel("Realizar PCA:");
	lblRealizarPca.setForeground(Color.WHITE);
	panel_15.add(lblRealizarPca, "cell 0 3,alignx right");

	btnStart = new JButton("Start");
	btnStart.setEnabled(false);
	btnStart.addActionListener(arg0 -> {
	    List selectedValues = modelList.getSelectedValuesList();
	    DBChartCollection colBuffer = null;
	    DBChartCollection pcaCollection = new DBChartCollection();
	    BufferedReader br = null;
	    String currentLine, path;
	    dbHandler.updateCollectionList();
	    for (int j = 0; j < selectedValues.size(); j++) {
		path = DBHandler.getDBFileCollection() + selectedValues.get(j);
		System.out.println(path);

		if (Files.exists(Paths.get(path + "/index.txt")))
		    try {
			br = new BufferedReader(new FileReader(path + "/" + "index.txt"));
			while ((currentLine = br.readLine()) != null) {
			    colBuffer = (DBChartCollection) DBHandler.loadGZipObject(path + "/" + currentLine + ".vis");
			    System.out.println(colBuffer.count());
			    for (int k = 0; k < colBuffer.count(); k++)
				pcaCollection.addChart(colBuffer.getChart(k));
			}
		    } catch (IOException e) {
			e.printStackTrace();
		    }
	    }
	    double[][] array = new double[pcaCollection.count()][2048];

	    for (int k = 0; k < pcaCollection.count(); k++)
		for (int j = 0; j < 2048; j++)
		    array[k][j] = (double) pcaCollection.getChart(k).getXyseries().getY(j);

	    System.out.println(array.length);
	    pcaMatrix = new Matrix(array);

	    pca = new PCA(pcaMatrix, (int) sliderComponentes.getValue());
	    System.out.println(pca);
	    comboBoxX.removeAllItems();
	    comboBoxY.removeAllItems();
	    for (int i = 1; i < (int) sliderComponentes.getValue() + 1; i++) {
		comboBoxX.addItem(String.valueOf(i));
		comboBoxY.addItem(String.valueOf(i));
	    }
	    comboBoxX.setEnabled(true);
	    comboBoxY.setEnabled(true);
	});
	panel_15.add(btnStart, "cell 1 3 2 1,alignx center");

	panel_17 = new JPanel();
	panel_17.setBackground(new Color(0, 0, 51));
	panel_17.setForeground(Color.WHITE);
	panel_17.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Componentes", TitledBorder.LEADING, TitledBorder.TOP, null, Color.WHITE));
	panel_8.add(panel_17, "cell 0 1,grow");
	panel_17.setLayout(new MigLayout("", "[][grow]", "[][]"));

	JLabel lblX = new JLabel("X:");
	lblX.setForeground(Color.WHITE);
	panel_17.add(lblX, "cell 0 0,alignx trailing");

	comboBoxX = new JComboBox();
	comboBoxX.setEnabled(false);
	panel_17.add(comboBoxX, "cell 1 0,growx");

	JLabel lblY = new JLabel("Y:");
	lblY.setForeground(Color.WHITE);
	panel_17.add(lblY, "cell 0 1,alignx trailing");

	comboBoxY = new JComboBox();
	comboBoxY.setEnabled(false);
	panel_17.add(comboBoxY, "cell 1 1,growx");

	panel_16 = new JPanel();
	panel_16.setToolTipText("");
	panel_16.setForeground(Color.WHITE);
	panel_16.setBackground(new Color(0, 0, 51));
	panel_16.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Matrizes", TitledBorder.LEADING, TitledBorder.TOP, null, Color.WHITE));
	panel_8.add(panel_16, "cell 0 2,grow");
	panel_16.setLayout(new MigLayout("", "[grow][grow]", "[]"));

	rdbtnL = new JRadioButton("L");
	rdbtnL.setBackground(new Color(0, 0, 51));
	rdbtnL.setForeground(Color.WHITE);
	panel_16.add(rdbtnL, "cell 0 0,alignx center");
	rdbtnL.setSelected(true);

	rdbtnT = new JRadioButton("T");
	rdbtnT.setBackground(new Color(0, 0, 51));
	rdbtnT.setForeground(Color.WHITE);
	panel_16.add(rdbtnT, "cell 1 0,alignx center");
	rdbtnT.setSelected(false);

	ButtonGroup matrixSelection = new ButtonGroup();
	matrixSelection.add(rdbtnL);
	matrixSelection.add(rdbtnT);

	for (int i = 1; i < (int) sliderComponentes.getValue() + 1; i++) {
	    comboBoxX.addItem(String.valueOf(i));
	    comboBoxY.addItem(String.valueOf(i));
	}
	PCAPanel panel_13 = new PCAPanel();
	panel_13.setBackground(new Color(0, 0, 0));
	pcaPanel.add(panel_13, "cell 1 0,grow");

	comboBoxX.addActionListener(e -> {
	    if (comboBoxX == null || comboBoxY == null || comboBoxX.getItemCount() == 0 || comboBoxY.getItemCount() == 0)
		return;
	    else
		panel_13.updateChart(Integer.parseInt((String) comboBoxX.getSelectedItem()), Integer.parseInt((String) comboBoxY.getSelectedItem()), pcaMatrix.getRowDimension(),
			(rdbtnL.isSelected() ? pca.getL() : pca.getT()));

	});

	comboBoxY.addActionListener(e -> {
	    if (comboBoxX == null || comboBoxY == null || comboBoxX.getItemCount() == 0 || comboBoxY.getItemCount() == 0)
		return;
	    else
		panel_13.updateChart(Integer.parseInt((String) comboBoxX.getSelectedItem()), Integer.parseInt((String) comboBoxY.getSelectedItem()), pcaMatrix.getRowDimension(),
			(rdbtnL.isSelected() ? pca.getL() : pca.getT()));
	});

	rdbtnL.addActionListener(e -> panel_13.updateChart(Integer.parseInt((String) comboBoxX.getSelectedItem()), Integer.parseInt((String) comboBoxY.getSelectedItem()), pcaMatrix.getRowDimension(),
		(rdbtnL.isSelected() ? pca.getL() : pca.getT())));

	rdbtnT.addActionListener(e -> panel_13.updateChart(Integer.parseInt((String) comboBoxX.getSelectedItem()), Integer.parseInt((String) comboBoxY.getSelectedItem()), pcaMatrix.getRowDimension(),
		(rdbtnL.isSelected() ? pca.getL() : pca.getT())));

	JPanel panel_14 = new JPanel();
	panel_14.setBackground(SystemColor.controlDkShadow);
	pcaPanel.add(panel_14, "cell 0 1 2 1,grow");

	JLabel lblvisPca = new JLabel("<html><b>VIS2048</b> - PCA</html>");
	lblvisPca.setForeground(Color.WHITE);
	lblvisPca.setFont(new Font("Tahoma", Font.PLAIN, 17));
	panel_14.add(lblvisPca);
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
	JPanel visioPanel = new JPanel();
	visioPanel.setBackground(new Color(0, 0, 51));
	espectroPanel.add(visioPanel, "cell 1 0,grow");
	visioPanel.setLayout(new MigLayout("", "0[grow]0", "0[grow]0"));

	JPanel primeChart = new JPanel();
	primeChart.setBackground(Color.black);
	primeChart.setAlignmentY(0.0f);
	dataSet = new XYSeriesCollection();
	jfreechart = ChartFactory.createXYLineChart("Visio", "", collectionName, dataSet, PlotOrientation.VERTICAL, true, true, false);
	counts = (NumberAxis) ((XYPlot) jfreechart.getPlot()).getRangeAxis();
	counts.setRange(0, 2500);
	ChartPanel panel = new ChartPanel(jfreechart);
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

	Thread cleaner = new Thread(() -> {
	    while (true) {
		try {
		    checkCommEvent();
		    tryConnection();
		} catch (InterruptedException e) {
		    e.printStackTrace();
		    btnConectar.setEnabled(false);
		    commComboBox.removeAllItems();
		}
	    }
	});
	cleaner.setDaemon(true);
	cleaner.start();
    }

    /**
     * Try connection.
     */
    private void tryConnection() {
	if (harvester != null && harvester.tryConnection(((String) commComboBox.getSelectedItem()))) {
	    commLabel.setText("Conectado");
	    commLabel.setForeground(Color.GREEN);
	    conectado = true;
	    setCommEvent(false);
	    Thread rSpec = launchThread();
	    rSpec.setDaemon(true);
	    rSpec.start();
	    btnAdquirir.setEnabled(true);
	    enableSpecPanel();
	}
    }

    /**
     * Adds the espectro tab.
     */
    private void addEspectroTab() {
	tabbedPane.addTab(null, espectroPanel);
	tabbedPane.setEnabledAt(0, true);
	tabbedPane.setBackgroundAt(0, new Color(255, 255, 255));
	espectroPanel.setLayout(new MigLayout("", "0[245px:245px:245px,grow]0[grow]0", "0[grow]0[30px:30px:30px]0"));
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

	JPanel espectroFieldset = new JPanel();
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
	btnParar.setEnabled(false);
	btnParar.requestFocus();
	btnParar.addActionListener((ActionEvent actionEvent) -> {
	    btnAdquirir.setEnabled(true);
	    btnParar.setEnabled(false);
	    btnParar.setFocusable(false);
	    setGet(false);
	    double time = System.nanoTime();
	    time = System.nanoTime() - time;
	    System.out.println("Time elapsed: " + time + ". Number of Charts: " + chartCollection.count());
	});

	btnAdquirir.setToolTipText("Adquirir Leituras");
	JPanel connectionFieldSet = new JPanel();
	panel_1.add(connectionFieldSet, "cell 0 1,growx");
	connectionFieldSet.setBackground(new Color(0, 0, 51));
	connectionFieldSet.setForeground(new Color(211, 211, 211));

	titledBorderConexao = new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Conexão", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(211, 211, 211));
	connectionFieldSet.setBorder(titledBorderConexao);
	connectionFieldSet.setLayout(new MigLayout("", "[70px:70px:70px][70px:70px:70px][60px:60px:60px]", "[25px:25px:25px]"));

	connectionFieldSet.add(commComboBox, "cell 0 0,grow");
	btnConectar.setMargin(new Insets(2, 5, 2, 5));
	btnConectar.setFocusable(false);
	connectionFieldSet.add(btnConectar, "cell 1 0,grow");

	commLabel = new JLabel("Indisponível");
	connectionFieldSet.add(commLabel, "cell 2 0,alignx center,growy");

	JPanel opcoesFieldSet = new JPanel();
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

	ButtonGroup groupModoOp = new ButtonGroup();
	groupModoOp.add(rdbtnUnico);
	groupModoOp.add(rdbtnContinuo);

	rdbtnContinuo.setSelected(true);
	rdbtnUnico.addActionListener(actionEvent -> {
	    btnParar.setEnabled(false);
	    setReadOnce(true);
	    btnAdquirir.setEnabled(true);
	});
	rdbtnContinuo.addActionListener(actionEvent -> {
	    if (!btnParar.isEnabled()) {
		btnParar.setEnabled(false);
		setReadOnce(false);
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

	JRadioButton countsRadioButton = new JRadioButton("Counts");
	opcoesFieldSet.add(countsRadioButton, "cell 0 3,alignx center,growy");
	countsRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
	countsRadioButton.setForeground(new Color(211, 211, 211));
	countsRadioButton.setBackground(new Color(0, 0, 51));
	countsRadioButton.setSelected(true);

	ButtonGroup groupUnit = new ButtonGroup();
	groupUnit.add(countsRadioButton);
	countsRadioButton.addActionListener(actionEvent -> {
	    if (flag) {
		counts.setRange(0, (counts.getUpperBound() * 1.24121212));
		for (int i = 0; i < chartCollection.count(); i++) {
		    Chart chart = chartCollection.getChart(i);
		    for (int j = 0; j < chart.getXyseries().getItemCount(); j++) {
			XYSeries serie = chart.getXyseries();
			serie.updateByIndex(j, (serie.getY(j)).doubleValue() * 1.24121212);
			chart.setMiliVolt(false);
		    }
		}
		(jfreechart.getXYPlot()).getRangeAxis().setLabel("Counts");
		flag = false;
	    }
	});

	JRadioButton mVRadioButton = new JRadioButton("mV");
	opcoesFieldSet.add(mVRadioButton, "cell 1 3,alignx center,growy");
	mVRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
	mVRadioButton.setForeground(new Color(211, 211, 211));
	mVRadioButton.setBackground(new Color(0, 0, 51));
	groupUnit.add(mVRadioButton);
	mVRadioButton.addActionListener((ActionEvent actionEvent) -> {
	    if (!flag) {
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
	});

	lblNumeroAmostras = new JLabel("N\u00FAmero de Amostras");
	lblNumeroAmostras.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
	lblNumeroAmostras.setHorizontalAlignment(SwingConstants.CENTER);
	opcoesFieldSet.add(lblNumeroAmostras, "cell 0 4 2 1,grow");
	lblNumeroAmostras.setFont(new Font("Dialog", Font.PLAIN, 12));
	lblNumeroAmostras.setForeground(new Color(211, 211, 211));
	lblNumeroAmostras.setBackground(new Color(0, 0, 102));
	lblNumeroAmostras.setOpaque(true);

	JSlider sliderNumeroDeAmostras = new JSlider(0, 2048, 2048);
	opcoesFieldSet.add(sliderNumeroDeAmostras, "flowx,cell 0 5 2 1,grow");
	sliderNumeroDeAmostras.setBackground(new Color(0, 0, 51));
	sliderNumeroDeAmostras.addChangeListener(e -> {
	    label.setText("" + sliderNumeroDeAmostras.getValue());
	});
	sliderNumeroDeAmostras.setSnapToTicks(true);

	lblTempoDeIntegrao = new JLabel("Tempo de Integra\u00E7\u00E3o");
	lblTempoDeIntegrao.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
	lblTempoDeIntegrao.setHorizontalAlignment(SwingConstants.CENTER);
	opcoesFieldSet.add(lblTempoDeIntegrao, "cell 0 6 2 1,grow");
	lblTempoDeIntegrao.setFont(new Font("Dialog", Font.PLAIN, 12));
	lblTempoDeIntegrao.setForeground(new Color(211, 211, 211));
	lblTempoDeIntegrao.setBackground(new Color(0, 0, 102));
	lblTempoDeIntegrao.setOpaque(true);

	sliderTempoDeIntegracao = new JSlider(0, 2048, 2048);
	sliderTempoDeIntegracao.setSnapToTicks(true);
	sliderTempoDeIntegracao.setBackground(new Color(0, 0, 51));
	sliderTempoDeIntegracao.addChangeListener((ChangeEvent e) -> label.setText(""));
	opcoesFieldSet.add(sliderTempoDeIntegracao, "cell 0 7 2 1,grow");

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

	JPanel solucaoFieldSet = new JPanel();
	panel_1.add(solucaoFieldSet, "cell 0 3,growx");
	solucaoFieldSet.setForeground(new Color(211, 211, 211));
	solucaoFieldSet.setBackground(new Color(0, 0, 51));

	titledBorderSolucao = new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Solu\u00E7\u00E3o", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(211, 211, 211));
	solucaoFieldSet.setBorder(titledBorderSolucao);
	solucaoFieldSet.setLayout(new MigLayout("", "[50%][50%]", "[20px:20px:20px][20px:20px:20px]"));

	sliderTempoDeIntegracao.addChangeListener((ChangeEvent e) -> label_1.setText("" + sliderTempoDeIntegracao.getValue()));

	lblConjunto = new JLabel("");
	lblConjunto.setForeground(new Color(211, 211, 211));
	lblConjunto.setHorizontalAlignment(SwingConstants.CENTER);
	solucaoFieldSet.add(lblConjunto, "flowx,cell 0 0 2 1,growx,aligny center");

	btnEscolher = new JButton("Renomear");
	solucaoFieldSet.add(btnEscolher, "cell 0 1,growx");
	btnEscolher.setFont(new Font("Tahoma", Font.PLAIN, 11));

	btnEscolher.addActionListener((ActionEvent arg0) -> {
	    try {
		DBViewerDialog dialog = new DBViewerDialog();
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	});

	btnSalvar = new JButton("Salvar");
	btnSalvar.setFont(new Font("Tahoma", Font.PLAIN, 11));
	solucaoFieldSet.add(btnSalvar, "cell 1 1,growx");
	btnSalvar.addActionListener((ActionEvent event) -> dbHandler.insert(chartCollection));
	disableSpecPanel();
    }

    /**
     * Traduzir.
     */
    private void traduzir() {
	if (tradutorIngles) {
	    stringVersao = "Version";
	    stringInstrucoes = "Instructions for use";
	    stringExportar = "Export...";
	    subMenuExportar = "Coming soon...";
	    instrucoesDeUso = "Coming soon...";
	    arquivoMenu.setText("File");
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
	    btnAdquirir.setText("Acquire");
	    btnAdquirir.setToolTipText("Acquire Readings");
	    tabbedPane.setTitleAt(2, "Connection");
	    btnParar.setText("Stop");
	    btnConectar.setText("Connect");
	    titledBorderPortSettings.setTitle("Port Settings");
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
	    // btnRemover.setText("Remove");
	    // btnEditar.setText("Edit");
	    labTabConexao.setText("Connection");
	    confirmDialogText = "The modified settings can severely alter the functioning of the software. Would you like to proceed?";
	    btnLimpar.setText("Clean");
	    btnAplicar.setText("Apply");
	    titledBorderVariavelEstado.setTitle("State Variables");
	    tradutorIngles = false;
	} else if (tradutorPortugues) {
	    stringVersao = "Versão";
	    stringInstrucoes = "Instruções de uso";
	    stringExportar = "Exportar...";
	    subMenuExportar = "Em breve...";
	    instrucoesDeUso = "Em breve...";
	    arquivoMenu.setText("Arquivo");
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
	    btnAdquirir.setText("Adquirir");
	    btnAdquirir.setToolTipText("Adquirir Leituras");
	    tabbedPane.setTitleAt(2, "Conexão");
	    btnParar.setText("Parar");
	    btnConectar.setText("Conectar");
	    titledBorderPortSettings.setTitle("Configurações de Porta");
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
	    labTabConexao.setText("Conexão");
	    confirmDialogText = "As configurações modificadas podem alterar severamente o funcionamento do software. Deseja prosseguir?";
	    btnLimpar.setText("Limpar");
	    btnAplicar.setText("Aplicar");
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
	instrucoesItem.addActionListener((ActionEvent event) -> JOptionPane.showMessageDialog(frame, instrucoesDeUso, stringInstrucoes, JOptionPane.PLAIN_MESSAGE));

	versaoItem = new JMenuItem("Versão");
	versao = "VIS2048 - beta_v1.0";
	stringVersao = "Versão";
	versaoItem.addActionListener((ActionEvent event) -> JOptionPane.showMessageDialog(frame, versao, stringVersao, JOptionPane.PLAIN_MESSAGE));

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
	fundoItem.addActionListener((ActionEvent event) -> {
	});

	curvaItem = new JMenuItem("- Curva (cor)");
	curvaItem.setMnemonic('c');

	curvaItem.addActionListener((ActionEvent event) -> {
	});

	JMenuItem gridItem = new JMenuItem("- Grid");
	gridItem.setMnemonic('g');
	gridItem.addActionListener((ActionEvent event) -> {
	});

	autoEscalarItem = new JMenuItem("- Autoescalar");
	autoEscalarItem.setMnemonic('a');
	autoEscalarItem.addActionListener((ActionEvent event) -> {
	});

	graficoMenu = new JMenu("Gráficos (Alt+g)");
	graficoMenu.setMnemonic('g');
	graficoMenu.add(fundoItem);
	graficoMenu.add(curvaItem);
	graficoMenu.add(gridItem);
	graficoMenu.add(autoEscalarItem);

	JMenuItem portuguesItem = new JMenuItem("- Português");
	portuguesItem.setMnemonic('p');
	portuguesItem.addActionListener((ActionEvent event) -> {
	    tradutorPortugues = true;
	    traduzir();
	});

	JMenuItem englishItem = new JMenuItem("- English");
	englishItem.addActionListener((ActionEvent event) -> {
	    tradutorIngles = true;
	    traduzir();
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
	abrirItem = new JMenuItem("Abrir... 	Ctrl+A");
	abrirItem.setAccelerator(KeyStroke.getKeyStroke("control A"));
	abrirItem.setMnemonic('a');
	abrirItem.addActionListener(new AbrirAction());

	salvarItem = new JMenuItem("Salvar... 	Ctrl+S");
	salvarItem.setAccelerator(KeyStroke.getKeyStroke("control S"));
	salvarItem.setMnemonic('s');
	salvarItem.addActionListener(new SalvarAction(frame));

	exportarItem = new JMenuItem("Exportar (csv)...		Ctrl+E");
	exportarItem.setAccelerator(KeyStroke.getKeyStroke("control E"));
	exportarItem.setMnemonic('e');
	subMenuExportar = "Este é o submenu 'Exportar...'. Em breve você poderá exportar arquivos csv.";
	stringExportar = "Exportar...";
	exportarItem.addActionListener((ActionEvent event) -> JOptionPane.showMessageDialog(frame, subMenuExportar, stringExportar, JOptionPane.PLAIN_MESSAGE));

	exportarImagemItem = new JMenuItem("Exportar imagem (jpeg, PNG)... 	Ctrl+I");
	exportarImagemItem.setAccelerator(KeyStroke.getKeyStroke("control I"));
	exportarImagemItem.setMnemonic('i');
	exportarImagemItem.addActionListener((ActionEvent event) -> JOptionPane.showMessageDialog(frame, subMenuExportar, stringExportar, JOptionPane.PLAIN_MESSAGE));

	sairItem = new JMenuItem("Sair");
	sairItem.addActionListener((ActionEvent event) -> System.exit(0));

	arquivoMenu = new JMenu("Arquivo");
	arquivoMenu.add(abrirItem);
	arquivoMenu.add(salvarItem);
	arquivoMenu.add(exportarItem);
	arquivoMenu.add(exportarImagemItem);
	arquivoMenu.add(sairItem);
	return arquivoMenu;
    }

    /**
     * Disable spec panel.
     */
    private void disableSpecPanel() {
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
	if (sliderTempoDeIntegracao != null) {
	    sliderTempoDeIntegracao.setEnabled(false);
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
    private void enableSpecPanel() {
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
	if (sliderTempoDeIntegracao != null) {
	    sliderTempoDeIntegracao.setEnabled(true);
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
    private boolean isReadOnce() {
	return readOnce;
    }

    /**
     * Sets the read once.
     *
     * @param readOnce
     *            the new read once
     */
    private void setReadOnce(boolean readOnce) {
	this.readOnce = readOnce;
    }

    /**
     * Check if get.
     *
     * @throws InterruptedException
     *             the interrupted exception
     */
    private synchronized void checkIfGet() throws InterruptedException {
	while (!get)
	    wait();
    }

    /**
     * Sets the gets the.
     *
     * @param get
     *            the new gets the
     */
    private synchronized void setGet(boolean get) { // Controla ordem de
						    // adquirir
	this.get = get;
	if (this.get)
	    notifyAll();
    }

    /**
     * Launch thread.
     *
     * @return thread
     */
    private Thread launchThread() {
	return new Thread((Runnable) () -> {
	    XYSeries series;
	    while (true) {
		try {
		    System.out.println("checkIfGet() → launchThread()");
		    checkIfGet();
		} catch (InterruptedException e) {
		    System.out.println(e.getMessage());
		    e.printStackTrace();
		}
		series = new XYSeries(collectionName);
		Chart chart = harvester.getDataset("");
		series = chart.getXyseries();
		chartCollection.addChart(chart);
		System.out.println(chartCollection.count());
		if (dataSet.getSeriesCount() > 0)
		    dataSet.removeAllSeries();
		dataSet.addSeries(series);
		chart.addItemListener((ItemEvent ev) -> {
		    if (chart.getXyseries() == dataSet.getSeries(0))
			return;
		    if (ev.getStateChange() == ItemEvent.SELECTED) {
			System.out.println(chart.getTimestamp() + " is selected");
			dataSet.addSeries(chart.getXyseries());
			chart.setBorderPainted(true);

		    } else if (ev.getStateChange() == ItemEvent.DESELECTED) {
			System.out.println(chart.getTimestamp() + " is not selected");
			dataSet.removeSeries(chart.getXyseries());
			chart.setBorderPainted(false);
		    }
		});
		chart.setPicture();

		/* tooltip */
		chart.setToolTipText("<html>" + "<p><b>Nome:</b> " + chart.getName() + "</p>" + "<p><b>Data:</b> " + new Date((long) chart.getTimestamp()) + "</p>" + "<p><b>Resolução:</b> "
			+ chart.getNumberOfSamples() + "</p>" + "<p><b>Descrição:</b> " + chart.getDescription() + "</p>" + "</html>");

		if (chartCollection.count() == 20) {
		    DBChartCollection oldCharts = chartCollection;
		    chartCollection = new DBChartCollection();
		    dbHandler.insert(oldCharts);
		}
		// counts.setRange(0, 2500);

		// update sliderPanel
		/*
		 * for (int i = chartCollection.count(); i > 0; i--) { Component
		 * comp = chartCollection.getChart(i - 1);
		 * sliderPanel.remove(comp); sliderPanel.add(comp, "cell 0 " +
		 * (chartCollection.count() - i + 2) +
		 * ", alignx center, aligny top"); } sliderPanel.add(chart,
		 * "cell 0 1, alignx center, aligny top");
		 * sliderPanel.updateUI();
		 */
		if (readOnce) {
		    setGet(false);
		}
	    }
	}, "Spectrometer");
    }

    // ///////////////////////////////////
    // ///// COMUNICAÇÃO /////////////////
    // ///////////////////////////////////
    /**
     * Check comm event.
     * 
     * while (!commEvent) wait();
     *
     * @throws InterruptedException
     *             the interrupted exception
     */
    private synchronized void checkCommEvent() throws InterruptedException { // Verifica
	while (!commEvent)
	    wait();
    }

    /**
     * Atribui o valor comm event.
     *
     * @param commEvent
     *            novo comm event
     */
    private synchronized void setCommEvent(boolean commEvent) { // Controla
								// ordem
	// de adquirir
	this.commEvent = commEvent;
	if (this.commEvent) {
	    notifyAll();
	}
    }

    /**
     * Class checkForPorts.
     */
    private class CheckForPorts implements Runnable {

	/** Atributo porta atual. */
	private String portaAtual = null;

	/**
	 * Find item.
	 *
	 * @param port
	 *            the port
	 * @return true, se bem-sucedido
	 */
	private boolean findItem(String port) {
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
	    btnConectar.addActionListener((ActionEvent arg0) -> {
		System.out.println("Iniciando conexão");
		commLabel.setText("Conectando");
		commLabel.setForeground(Color.ORANGE);
		btnConectar.setEnabled(false);
		disableSpecPanel();
		setCommEvent(true);
	    });
	    commComboBox.addActionListener((ActionEvent arg0) -> {
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
			// verifica a porta de comunicação
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
		sleep(1000);
	    }
	}

	/**
	 * Sleep.
	 *
	 * @param ms
	 *            the ms
	 */
	private void sleep(long ms) {
	    try {
		Thread.sleep(ms);
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	}

    }
}
