package br.iesb.VIS2048.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import jssc.SerialPortList;
import net.miginfocom.swing.MigLayout;
import br.iesb.VIS2048.database.Zipper;
import br.iesb.VIS2048.panel.SpecPanel;

public class Visio {

	private JFrame frame;
	//private JPanel contentPane;
	JLabel lblConectado;
	protected int baudRate = 115200;
	protected int dataBits = 8;
	protected int stopBits = 0;
	protected int parity = 0;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					System.setProperty("sun.java2d.d3d", "false");
					Visio window = new Visio();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Visio() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
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
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 0, 51));
		tabbedPane.addTab(null, panel);
		tabbedPane.setEnabledAt(0, true);
		tabbedPane.setBackgroundAt(0, new Color(255, 255, 255));
		panel.setLayout(new MigLayout("", "[15%,grow][85%]", "[grow][grow][grow][80px:n,grow]"));
		
		JLabel labTab1 = new JLabel("Espectro");
		labTab1.setUI(new VerticalLabelUI(false));
		tabbedPane.setTabComponentAt(0, labTab1);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(new Color(0, 0, 51));
		panel_5.setForeground(new Color(211, 211, 211));
		panel_5.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Espectro", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(211, 211, 211)));
		panel.add(panel_5, "cell 0 0,grow");
		panel_5.setLayout(new MigLayout("", "[100px,grow][100px,grow]", "[20px]"));
		
		JButton btnAdquirir = new JButton("Adquirir");
		btnAdquirir.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnAdquirir.setEnabled(false);
		panel_5.add(btnAdquirir, "cell 0 0,grow");
		
		JButton btnParar = new JButton("Parar");
		btnParar.setFont(new Font("Tahoma", Font.PLAIN, 11));
		panel_5.add(btnParar, "cell 1 0,grow");
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(0, 0, 51));
		panel.add(panel_1, "cell 1 0 1 4,grow");
		panel_1.setLayout(new MigLayout("", "[grow]", "[90%,grow][10%]"));
		
		SpecPanel specPanel = new SpecPanel();
		specPanel.setBackground(new Color(0, 0, 51));
		specPanel.setAlignmentY(0.0f);
		panel_1.add(specPanel, "flowy,cell 0 0,grow");
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(0, 0, 51));
		panel_1.add(panel_3, "cell 0 1,grow");
		
		JPanel panel_6 = new JPanel();
		panel_6.setBackground(new Color(0, 0, 51));
		panel_6.setForeground(new Color(211, 211, 211));
		panel_6.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Conex\u00E3o", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(211, 211, 211)));
		panel.add(panel_6, "cell 0 1,grow");
		panel_6.setLayout(new MigLayout("", "[100px,grow][100px,grow]", "[20px,grow]"));
		
		JComboBox<String> comboBox = new JComboBox<String>();
		checkPort = new Thread(new checkForPorts(comboBox));
		comboBox.setForeground(new Color(211, 211, 211));
		comboBox.setBackground(new Color(0, 0, 51));
		comboBox.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		comboBox.addActionListener (new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				if(specPanel.getDevice() != null){
					lblConectado.setText("Indisponível");
					lblConectado.setForeground(new Color(255, 0, 0));
					btnAdquirir.setEnabled(false);
					specPanel.getDevice().closeComm();
				}
				lblConectado.setText("Conectando");
				lblConectado.setForeground(Color.ORANGE);
				if(specPanel.newComm((String) comboBox.getSelectedItem())){
					lblConectado.setText("Conectado");
					lblConectado.setForeground(new Color(0, 211, 0));
					btnAdquirir.setEnabled(true);
					System.out.println("Conectado");
				}else{
					lblConectado.setText("Indisponível");
					lblConectado.setForeground(new Color(255, 0, 0));
				}				
		    }
		});
		panel_6.add(comboBox, "cell 0 0,growx,aligny center");
		
		lblConectado = new JLabel("Indisponível");
		lblConectado.setForeground(new Color(255, 0, 0));
		panel_6.add(lblConectado, "cell 1 0,alignx center,aligny center");
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(0, 0, 51));
		panel_4.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Op\u00E7\u00F5es", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(211, 211, 211)));
		panel.add(panel_4, "cell 0 2,grow");
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
		
		JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("mV");
		panel_4.add(rdbtnNewRadioButton_2, "cell 1 4,alignx center,growy");
		rdbtnNewRadioButton_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnNewRadioButton_2.setForeground(new Color(211, 211, 211));
		rdbtnNewRadioButton_2.setBackground(new Color(0, 0, 51));
		
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
		
		JSlider slider = new JSlider();
		panel_4.add(slider, "flowx,cell 0 7 2 1,grow");
		slider.setBackground(new Color(0, 0, 51));
		
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
		
		JLabel label = new JLabel("2048");
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
		checkPort.setDaemon(true);
		checkPort.start();
		
		JPanel panel_7 = new JPanel();
		panel_7.setForeground(new Color(211, 211, 211));
		panel_7.setBackground(new Color(0, 0, 51));
		panel_7.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Solu\u00E7\u00E3o", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(211, 211, 211)));
		panel.add(panel_7, "cell 0 3,grow");
		panel_7.setLayout(new MigLayout("", "[grow]", "[18px,grow][16,grow]"));
		
		JLabel lblConjunto = new JLabel("Conjunto");
		lblConjunto.setForeground(new Color(211, 211, 211));
		lblConjunto.setHorizontalAlignment(SwingConstants.CENTER);
		panel_7.add(lblConjunto, "flowx,cell 0 0,growx,aligny top");
		
		JButton btnEscolher = new JButton("Escolher");
		panel_7.add(btnEscolher, "flowx,cell 0 1,growx");
		
		JButton btnNovo = new JButton("Novo");
		panel_7.add(btnNovo, "cell 0 1");
		
		JButton btnLimpar = new JButton("Limpar");
		panel_7.add(btnLimpar, "cell 0 1,growx");
		
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
					Zipper zipper = new Zipper();
					zipper.generateFileList(null);
					zipper.zipIt(null);

					btnAdquirir.setEnabled(true);
					btnParar.setEnabled(false);
					btnParar.setFocusable(false);
					specPanel.getSpec(false);
				} else {
					btnAdquirir.setEnabled(false);
					btnParar.setEnabled(true);
					btnParar.setFocusable(true);
					specPanel.reloadTitle();
				}
			}
		});

		btnAdquirir.setActionCommand("enable");
		btnAdquirir.setToolTipText("Adquirir leituras");
		btnAdquirir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				if ("enable".equals(event.getActionCommand())) {
					specPanel.getSpec(true);
					if (specPanel.isReadOnce())
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
	private Thread checkPort;
	//private boolean changeOnPort = false;
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
}


