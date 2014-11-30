package br.iesb.VIS2048.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import net.miginfocom.swing.MigLayout;
import br.iesb.VIS2048.action.AbrirAction;
import br.iesb.VIS2048.action.SalvarAction;
import br.iesb.VIS2048.panel.SpecPanel;

public class BaseFrame {

	private JFrame frame = new JFrame("VIS2048 - espectrômetro de emissão");
	private JPanel panel = new JPanel();
	private JPanel calibPanel = new JPanel();
	private JPanel degreePanel = new JPanel();
	private JPanel coefPanel = new JPanel();
	//private JPanel specPanel = new JPanel();
	private JPanel contentPane;

	private JLabel lblVis = new JLabel("VIS");
	private JLabel lblEspectrmetro = new JLabel(
			"2048 - espectrômetro de emissão");

	private JPanel panel_1 = new JPanel();
	private JPanel panel_2 = new JPanel();

	private List<Integer> coeficients = new ArrayList<Integer>();

	public BaseFrame() {
		frame.getContentPane().setBackground(new Color(65, 105, 225));
		frame.setBounds(100, 100, 865, 500);
		frame.setBackground(new Color(65, 105, 225));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setJMenuBar(getMenuBar());
		setCalibPanel();
	}

	public void setCalibPanel() {
		calibPanel.setForeground(new Color(0, 0, 0));
		calibPanel.setBackground(new Color(65, 105, 225));
		frame.setContentPane(calibPanel);

		calibPanel.setLayout(new MigLayout("",
				"[grow][][][][grow][][][][][][][][][][][]",
				"[][][][][][][grow][grow][grow][grow]"));

		lblVis.setForeground(new Color(0, 0, 0));
		lblVis.setFont(new Font("Dialog", Font.BOLD, 40));
		calibPanel.add(lblVis, "cell 3 6");

		lblEspectrmetro.setForeground(new Color(255, 255, 255));
		lblEspectrmetro.setFont(new Font("Dialog", Font.BOLD, 36));
		calibPanel.add(lblEspectrmetro, "cell 4 6 10 1");
		{
			panel_1.setForeground(new Color(248, 248, 255));
			panel_1.setFont(new Font("Dialog", Font.BOLD, 22));
			panel_1.setBackground(new Color(0, 0, 0));

			calibPanel
					.add(panel_1, "cell 0 7 12 1,alignx center,aligny bottom");

			panel_1.setLayout(new BorderLayout(0, 0));
			{
				JLabel lblEqCalib = new JLabel("Possui equação de calibração?");
				lblEqCalib.setFont(new Font("Dialog", Font.PLAIN, 22));
				lblEqCalib.setForeground(new Color(248, 248, 255));
				lblEqCalib.setBorder(new EmptyBorder(5, 15, 5, 15));
				panel_1.add(lblEqCalib, BorderLayout.NORTH);
			}
		}
		{
			panel_2.setBackground(new Color(65, 105, 225));
			ButtonGroup groupP1 = new ButtonGroup();
			calibPanel.add(panel_2, "cell 0 8 12 1,growx, aligny center");
			{
				JRadioButton rdbtnSim = new JRadioButton("Sim");
				rdbtnSim.setFont(new Font("Dialog", Font.BOLD, 12));
				rdbtnSim.setBackground(new Color(0, 0, 0));
				rdbtnSim.setForeground(new Color(248, 248, 255));
				groupP1.add(rdbtnSim);
				rdbtnSim.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent event) {
						if (event.getSource() == rdbtnSim)
							setDegreePanel();
					}
				});
				panel_2.add(rdbtnSim);
			}
			{
				JRadioButton rdbtnNo = new JRadioButton("Não");
				rdbtnNo.setFont(new Font("Dialog", Font.BOLD, 12));
				rdbtnNo.setForeground(new Color(248, 248, 255));
				rdbtnNo.setBackground(Color.BLACK);
				panel_2.add(rdbtnNo);
				
				groupP1.add(rdbtnNo);
				rdbtnNo.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent event) {
						if (event.getSource() == rdbtnNo)
							setSpecPanel();
					}
				});
			}
		}
	}

	public void setDegreePanel() {
		degreePanel.setForeground(new Color(0, 0, 0));
		degreePanel.setBackground(new Color(65, 105, 225));

		frame.getContentPane().setVisible(false);
		frame.setContentPane(degreePanel);
		frame.getContentPane().setVisible(true);

		degreePanel.setLayout(new MigLayout("",
				"[grow][][][][grow][][][][][][][][][][][]",
				"[][][][][][][grow][grow][grow][grow]"));

		degreePanel.add(lblVis, "cell 3 6");
		degreePanel.add(lblEspectrmetro, "cell 4 6 10 1");
		{
			panel_1 = new JPanel();
			panel_1.setForeground(new Color(248, 248, 255));
			panel_1.setFont(new Font("Dialog", Font.BOLD, 22));
			panel_1.setBackground(new Color(0, 0, 0));
			degreePanel.add(panel_1,
					"cell 0 7 12 1,alignx center,aligny bottom");
			panel_1.setLayout(new BorderLayout(0, 0));
			{
				JLabel lblNewLabel_1 = new JLabel("Insira o grau do polinômio");
				lblNewLabel_1.setBorder(new EmptyBorder(5, 15, 5, 15));
				lblNewLabel_1.setFont(new Font("Dialog", Font.PLAIN, 22));
				lblNewLabel_1.setForeground(new Color(248, 248, 255));
				panel_1.add(lblNewLabel_1, BorderLayout.NORTH);
			}
			panel_1.transferFocus();
		}
		{
			panel_2 = new JPanel();
			panel_2.setBackground(new Color(65, 105, 225));
			degreePanel.add(panel_2, "cell 0 8 12 1,growx,aligny top");
			{
				JTextField textField = new JTextField();
				textField.setSelectedTextColor(new Color(0, 0, 0));
				textField.setHorizontalAlignment(SwingConstants.CENTER);
				textField.setFont(new Font("Dialog", Font.BOLD, 12));
				textField.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0)));
				textField.setMargin(new Insets(5, 5, 5, 5));
				textField.setForeground(new Color(0, 0, 0));
				panel_2.add(textField);
				textField.requestFocus();
				textField.setColumns(10);
				textField.addKeyListener(new KeyListener() {

					private Integer beforeCode;
					private Integer code;
					private boolean enterCode = false;
					
					@Override
					public void keyTyped(KeyEvent event) {
						if(textField.getText().length() >= 1)
							event.setKeyChar((char) KeyEvent.VK_CLEAR);
					}

					@Override
					public void keyReleased(KeyEvent event) {
						Integer code = event.getExtendedKeyCode();
						if(code > 48 && code < 52 && this.code == null) {
							this.code = code;
							this.beforeCode = new Integer(this.code.intValue());
						} if(code != 10 && code != 8 && code != 17 && code != 27) {
							if(code == 48 || code >= 52 && code < 58)
								if(this.beforeCode == null)
									this.beforeCode = code;
							if(this.beforeCode == null)
								this.beforeCode = code;
						} if(this.enterCode) {
							if(this.beforeCode == null) {
								if(code == 10)
									JOptionPane.showMessageDialog(textField, "Campo de Preenchimento Obrigatório!");
							} else {
								if(this.beforeCode > 48 && this.beforeCode < 52) {
									setCoefPanel(this.code);
								} else if(this.beforeCode == 48 || this.beforeCode >= 52 && this.beforeCode < 58) {
									JOptionPane.showMessageDialog(textField, "Coeficiente inválido!");
								} else {
									JOptionPane.showMessageDialog(textField, "Caractere inválido!");
								}
							}
						}
						this.enterCode = false;
						System.out.println("this.code = "+this.code);
						System.out.println("code = "+code);
						System.out.println("enterCode = "+this.enterCode);
						System.out.println("beforeCode = "+this.beforeCode);
						System.out.println("=========================================");
					}

					@Override
					public void keyPressed(KeyEvent event) {
						Integer code = event.getExtendedKeyCode();
						if(code == 8 | code == 17) {
							this.code = null;
							this.beforeCode = null;
							this.enterCode = false;
						} else if(code == 10) {
							this.enterCode = true;
						}
					}
				});
			}
		}
	}

	private void setCoefPanel(Integer integer) {
		coefPanel.setForeground(new Color(0, 0, 0));
		coefPanel.setBackground(new Color(65, 105, 225));

		frame.getContentPane().setVisible(false);
		frame.setContentPane(coefPanel);
		frame.getContentPane().setVisible(true);

		coefPanel.setLayout(new MigLayout("",
				"[grow][][][][grow][][][][][][][][][][][]",
				"[][][][][][][grow][grow][grow][grow]"));

		coefPanel.add(lblVis, "cell 3 6");
		coefPanel.add(lblEspectrmetro, "cell 4 6 10 1");
		{
			panel_1 = new JPanel();
			panel_1.setForeground(new Color(248, 248, 255));
			panel_1.setFont(new Font("Dialog", Font.BOLD, 22));
			panel_1.setBackground(new Color(0, 0, 0));
			coefPanel.add(panel_1, "cell 0 7 12 1,alignx center,aligny bottom");
			panel_1.setLayout(new BorderLayout(0, 0));
			{
				JLabel lblNewLabel_1 = new JLabel("Insira os coeficientes de regressão");
				lblNewLabel_1.setFont(new Font("Dialog", Font.PLAIN, 22));
				lblNewLabel_1.setForeground(new Color(248, 248, 255));
				lblNewLabel_1.setBorder(new EmptyBorder(5, 15, 5, 15));
				panel_1.add(lblNewLabel_1, BorderLayout.NORTH);
			}
			panel_1.transferFocus();
		}
		{
			panel_2 = new JPanel();
			coefPanel.add(panel_2, "cell 0 8 12 1,alignx center,aligny top");
			panel_2.setBackground(new Color(65, 105, 225));
			panel_2.setLayout(new MigLayout("", "[][][30][30][30][30][30][30][30][30][][30px]", "[][]"));
			switch (integer) {
			case 49: {
				JTextField textField_1 = new JTextField();
				textField_1.setForeground(new Color(0, 0, 0));
				textField_1.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0,0), new Color(0, 0, 0)));
				textField_1.setColumns(10);
				textField_1.setFont(new Font("Dialog", Font.BOLD, 12));
				textField_1.setHorizontalAlignment(0);
				textField_1.requestFocus();
				textField_1.addKeyListener(new KeyListener() {
					
					private Integer beforeCode;
					
					@Override
					public void keyTyped(KeyEvent e) {
						if(e.getExtendedKeyCode() == 8 && beforeCode != null)
							coeficients.remove(beforeCode);
					}

					@Override
					public void keyReleased(KeyEvent e) {
						Integer code = e.getExtendedKeyCode();
						if(code != 8 && code != 10)
							if(code >= 48 && code < 58) {
								coeficients.add(code);
								beforeCode = code;
							}
					}

					@Override
					public void keyPressed(KeyEvent e) {
					}
				});
				panel_2.add(textField_1, "cell 5 1,growx");
				panel_2.transferFocus();
			}
				{
					JLabel lblNewLabel_2 = new JLabel("X +");
					lblNewLabel_2.setForeground(new Color(0, 0, 0));
					panel_2.add(lblNewLabel_2, "cell 6 1,alignx trailing");
				}
				{
					JTextField textField = new JTextField();
					textField.requestFocus();
					textField.setForeground(new Color(0, 0, 0));
					textField.setHorizontalAlignment(0);
					textField.setFont(new Font("Dialog", Font.BOLD, 12));
					textField.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0)));
					textField.addKeyListener(new KeyListener() {
						
						private Integer beforeCode;
						
						@Override
						public void keyTyped(KeyEvent e) {
							if(e.getExtendedKeyCode() == 8 && beforeCode != null)
								coeficients.remove(beforeCode);
						}

						@Override
						public void keyReleased(KeyEvent e) {
							Integer code = e.getExtendedKeyCode();
							if(code != 8 && code != 10)
								if(code >= 48 && code < 58) {
									coeficients.add(code);
									beforeCode = code;
								} if (code == 10)
									setSpecPanel();
						}

						@Override
						public void keyPressed(KeyEvent e) {
						}
					});
					panel_2.add(textField, "cell 7 1,growx");
					textField.setColumns(10);
				}
				break;
			case 50: {
				JTextField textField_2 = new JTextField();
				textField_2.setForeground(Color.BLACK);
				textField_2.setColumns(10);
				textField_2.requestFocus();
				textField_2.setHorizontalAlignment(0);
				textField_2.setFont(new Font("Dialog", Font.BOLD, 12));
				textField_2.setBorder(new BevelBorder(BevelBorder.RAISED,
						new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0,
								0), new Color(0, 0, 0)));
				textField_2.addKeyListener(new KeyListener() {

					private Integer beforeCode;
					
					@Override
					public void keyTyped(KeyEvent e) {
						if(e.getExtendedKeyCode() == 8 && beforeCode != null)
							coeficients.remove(beforeCode);
					}

					@Override
					public void keyReleased(KeyEvent e) {
						Integer code = e.getExtendedKeyCode();
						if(code != 8 && code != 10)
							if(code >= 48 && code < 58) {
								coeficients.add(code);
								beforeCode = code;
							}
					}

					@Override
					public void keyPressed(KeyEvent e) {
					}
				});
				panel_2.add(textField_2, "cell 4 1,growx");
				panel_2.transferFocus();
			}
				{
					JLabel lblX = new JLabel("X² +");
					lblX.setForeground(Color.BLACK);
					panel_2.add(lblX, "cell 5 1,alignx trailing");
				}
				{
					JTextField textField_1 = new JTextField();
					textField_1.setForeground(new Color(0, 0, 0));
					textField_1.setColumns(10);
					textField_1.requestFocus();
					textField_1.setHorizontalAlignment(0);
					textField_1.setFont(new Font("Dialog", Font.BOLD, 12));
					textField_1.setBorder(new BevelBorder(BevelBorder.RAISED,
							new Color(0, 0, 0), new Color(0, 0, 0), new Color(
									0, 0, 0), new Color(0, 0, 0)));
					textField_1.addKeyListener(new KeyListener() {
						
						private Integer beforeCode;
						
						@Override
						public void keyTyped(KeyEvent e) {
							if(e.getExtendedKeyCode() == 8 && beforeCode != null)
								coeficients.remove(beforeCode);
						}

						@Override
						public void keyReleased(KeyEvent e) {
							Integer code = e.getExtendedKeyCode();
							if(code != 8 && code != 10)
								if(code >= 48 && code < 58) {
									coeficients.add(code);
									beforeCode = code;
								}
						}

						@Override
						public void keyPressed(KeyEvent e) {
						}
					});
					panel_2.add(textField_1, "cell 6 1,alignx center");
				}
				{
					JLabel lblNewLabel_2 = new JLabel("X +");
					lblNewLabel_2.setForeground(new Color(0, 0, 0));
					panel_2.add(lblNewLabel_2, "cell 7 1,alignx center");
				}
				{
					JTextField textField = new JTextField();
					textField.setForeground(new Color(0, 0, 0));
					textField.requestFocus();
					textField.setHorizontalAlignment(0);
					textField.setFont(new Font("Dialog", Font.BOLD, 12));
					textField.setBorder(new BevelBorder(BevelBorder.RAISED,
							new Color(0, 0, 0), new Color(0, 0, 0), new Color(
									0, 0, 0), new Color(0, 0, 0)));
					textField.addKeyListener(new KeyListener() {

						private Integer beforeCode;
						
						@Override
						public void keyTyped(KeyEvent e) {
							if(e.getExtendedKeyCode() == 8 && beforeCode != null)
								coeficients.remove(beforeCode);
						}

						@Override
						public void keyReleased(KeyEvent e) {
							Integer code = e.getExtendedKeyCode();
							if(code != 8 && code != 10)
								if(code >= 48 && code < 58) {
									coeficients.add(code);
									beforeCode = code;
								} if (code == 10)
									setSpecPanel();
						}

						@Override
						public void keyPressed(KeyEvent e) {
						}
					});
					panel_2.add(textField, "cell 8 1,alignx center");
					textField.setColumns(10);
				}
				break;
			default: {
				JTextField textField_3 = new JTextField();
				textField_3.setForeground(Color.BLACK);
				textField_3.setColumns(10);
				textField_3.requestFocus();
				textField_3.setFont(new Font("Dialog", Font.BOLD, 12));
				textField_3.setHorizontalAlignment(0);
				textField_3.setBorder(new BevelBorder(BevelBorder.RAISED,
						new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0,
								0), new Color(0, 0, 0)));
				textField_3.addKeyListener(new KeyListener() {
					
					private Integer beforeCode;
					
					@Override
					public void keyTyped(KeyEvent e) {
						if(e.getExtendedKeyCode() == 8 && beforeCode != null)
							coeficients.remove(beforeCode);
					}

					@Override
					public void keyReleased(KeyEvent e) {
						Integer code = e.getExtendedKeyCode();
						if(code != 8 && code != 10)
							if(code >= 48 && code < 58) {
								coeficients.add(code);
								beforeCode = code;
							}
					}

					@Override
					public void keyPressed(KeyEvent e) {
					}
				});
				panel_2.add(textField_3, "cell 3 1,alignx center");
				panel_2.transferFocus();
			}
				{
					JLabel lblX_1 = new JLabel("X³ +");
					lblX_1.setForeground(Color.BLACK);
					panel_2.add(lblX_1, "cell 4 1,alignx center");
				}
				{
					JTextField textField_2 = new JTextField();
					textField_2.setForeground(Color.BLACK);
					textField_2.setFont(new Font("Dialog", Font.BOLD, 12));
					textField_2.requestFocus();
					textField_2.setHorizontalAlignment(0);
					textField_2.setBorder(new BevelBorder(BevelBorder.RAISED,
							new Color(0, 0, 0), new Color(0, 0, 0), new Color(
									0, 0, 0), new Color(0, 0, 0)));
					textField_2.setColumns(10);
					panel_2.add(textField_2, "cell 5 1,alignx center");
				}
				{
					JLabel lblX = new JLabel("X² +");
					lblX.setForeground(Color.BLACK);
					panel_2.add(lblX, "cell 6 1,alignx center");
				}
				{
					JTextField textField_1 = new JTextField();
					textField_1.requestFocus();
					textField_1.setFont(new Font("Dialog", Font.BOLD, 12));
					textField_1.setForeground(new Color(0, 0, 0));
					textField_1.setHorizontalAlignment(0);
					textField_1.setBorder(new BevelBorder(BevelBorder.RAISED,
							new Color(0, 0, 0), new Color(0, 0, 0), new Color(
									0, 0, 0), new Color(0, 0, 0)));
					textField_1.setColumns(10);
					textField_1.addKeyListener(new KeyListener() {

						private Integer beforeCode;
						
						@Override
						public void keyTyped(KeyEvent e) {
							if(e.getExtendedKeyCode() == 8 && beforeCode != null)
								coeficients.remove(beforeCode);
						}

						@Override
						public void keyReleased(KeyEvent e) {
							Integer code = e.getExtendedKeyCode();
							if(code != 8 && code != 10)
								if(code >= 48 && code < 58) {
									coeficients.add(code);
									beforeCode = code;
								}
						}

						@Override
						public void keyPressed(KeyEvent e) {
						}
					});
					panel_2.add(textField_1, "cell 7 1,alignx center");
					panel_2.transferFocus();
				}
				{
					JLabel lblNewLabel_2 = new JLabel("X +");
					lblNewLabel_2.setForeground(new Color(0, 0, 0));
					panel_2.add(lblNewLabel_2, "cell 8 1,alignx center");
				}
				{
					JTextField textField = new JTextField();
					textField.setForeground(new Color(0, 0, 0));
					panel_2.add(textField, "cell 9 1,alignx center");
					textField.setColumns(10);
					textField.setHorizontalAlignment(0);
					textField.setFont(new Font("Dialog", Font.BOLD, 12));
					textField.setFont(new Font("Dialog", Font.BOLD, 12));
					textField.setBorder(new BevelBorder(BevelBorder.RAISED,
							new Color(0, 0, 0), new Color(0, 0, 0), new Color(
									0, 0, 0), new Color(0, 0, 0)));
					textField.addKeyListener(new KeyListener() {
						
						private Integer beforeCode;
						
						@Override
						public void keyTyped(KeyEvent e) {
							if(e.getExtendedKeyCode() == 8 && beforeCode != null)
								coeficients.remove(beforeCode);
						}

						@Override
						public void keyReleased(KeyEvent event) {
							Integer code = event.getExtendedKeyCode();
							if(code != 8 && code != 10)
								if(code >= 48 && code < 58) {
									coeficients.add(code);
									beforeCode = code;
								} if (code == 10)
									setSpecPanel();
						}

						@Override
						public void keyPressed(KeyEvent e) {
						}
					});
				}
				break;
			}
		}
	}
	
	private void setSpecPanel() {
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 51));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setBackground(new Color(0, 0, 51));
		contentPane.add(panel, BorderLayout.WEST);
		
		frame.getContentPane().setVisible(false);
		frame.setContentPane(contentPane);
		frame.getContentPane().setVisible(true);

		panel_2 = new JPanel() {
			private static final long serialVersionUID = 3911679766974187781L;

			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
						RenderingHints.VALUE_RENDER_QUALITY);
				int w = getWidth();
				int h = getHeight();
				Color color1 = Color.DARK_GRAY;
				Color color2 = Color.LIGHT_GRAY;
				GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
				g2d.setPaint(gp);
				g2d.fillRect(0, 0, w, h);
			}
		};
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_2.setOpaque(false);
		panel_2.setPreferredSize(new Dimension(160, 28));
		panel_2.setMinimumSize(new Dimension(80, 20));
		panel_2.setBackground(Color.DARK_GRAY);
		panel.setLayout(new MigLayout(
						"",
						"[180px,grow][grow]",
						"[grow][33px][][grow][33px][20px,fill][][20px,fill][][20px,fill][][20px,fill][][20px,fill][][grow]"));
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(0, 0, 51));
		panel.add(panel_3, "cell 0 0,grow");
		panel.add(panel_2, "cell 0 1,growx,aligny center");

		JTextPane txtpnEspectro = new JTextPane();
		txtpnEspectro.setMargin(new Insets(0, 0, 0, 0));
		txtpnEspectro.setEditable(false);
		txtpnEspectro.setCaretColor(new Color(0, 0, 0));
		txtpnEspectro.setForeground(new Color(0, 0, 0));
		txtpnEspectro.setSelectedTextColor(Color.LIGHT_GRAY);
		txtpnEspectro.setOpaque(false);
		txtpnEspectro.setText("Espectro");
		panel_2.add(txtpnEspectro);

		JButton btnAdquirir = new JButton("Adquirir");
		btnAdiquirir.requestFocus();
		panel.add(btnAdquirir, "flowx,cell 0 2,growx");
		panel.transferFocus();

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(0, 0, 51));
		panel.add(panel_1, "cell 0 3,grow");

		JPanel panel_4 = new JPanel() {
			private static final long serialVersionUID = 3911679766974187781L;

			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
						RenderingHints.VALUE_RENDER_QUALITY);
				int w = getWidth();
				int h = getHeight();
				Color color1 = Color.DARK_GRAY;
				Color color2 = Color.LIGHT_GRAY;
				GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
				g2d.setPaint(gp);
				g2d.fillRect(0, 0, w, h);
			}
		};
		panel_4.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_4.setOpaque(false);
		panel_4.setPreferredSize(new Dimension(160, 28));
		panel_4.setBackground(Color.DARK_GRAY);
		panel.add(panel_4, "cell 0 4,growx,aligny center");

		JTextPane txtpnOpes = new JTextPane();
		txtpnOpes.setMargin(new Insets(0, 0, 0, 0));
		txtpnOpes.setForeground(Color.BLACK);
		txtpnOpes.setCaretColor(Color.BLACK);
		txtpnOpes.setOpaque(false);
		txtpnOpes.setEditable(false);
		txtpnOpes.setText("Op\u00E7\u00F5es");
		panel_4.add(txtpnOpes);
		
		///////////////////////////////
		///////Minhas Alterações///////
		///////////////////////////////
		SpecPanel specPanel = new SpecPanel();
		specPanel.setBackground(new Color(0, 0, 51, 0));
		specPanel.setAlignmentY(Component.TOP_ALIGNMENT);
		specPanel.setPreferredSize(new Dimension(630, 300));
		contentPane.add(specPanel, BorderLayout.CENTER);
		
		JButton btnParar = new JButton("Parar");
		btnParar.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panel.add(btnParar, "cell 0 2,growx");
		btnParar.setActionCommand("disable");
		btnParar.setEnabled(false);
		btnParar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if ("disable".equals(event.getActionCommand())) {
			        btnAdquirir.setEnabled(true);
			        btnParar.setEnabled(false);
			        //specPanel.stop = true;
			        specPanel.getSpec(false);
			    } else {
			        btnAdquirir.setEnabled(false);
			        btnParar.setEnabled(true);
			        btnParar.requestFocus();
			    }
			}
		});

		btnAdquirir.setActionCommand("enable");
		btnAdquirir.setToolTipText("Começar a adquirir leituras");
		btnAdquirir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if ("enable".equals(event.getActionCommand())) {
			        specPanel.getSpec(true);
			        if(specPanel.isReadOnce())
			        	btnParar.setEnabled(false);
			        else{
			        	btnParar.setEnabled(true);
			        	btnAdquirir.setEnabled(false);
			        }
			        
			    } else {
			        btnAdquirir.setEnabled(true);
			        btnParar.setEnabled(false);
			    }
			}
		});
		
		/////////////////////////////
		///////Terminam aqui/////////
		/////////////////////////////		
		
		JTextPane txtpnModoDeOperao = new JTextPane();
		txtpnModoDeOperao.setPreferredSize(new Dimension(6, 24));
		txtpnModoDeOperao.setMargin(new Insets(5, 5, 5, 5));
		txtpnModoDeOperao.setBorder(new LineBorder(Color.DARK_GRAY));
		txtpnModoDeOperao.setForeground(Color.LIGHT_GRAY);
		txtpnModoDeOperao.setBackground(new Color(25, 25, 112));
		txtpnModoDeOperao.setEditable(false);
		txtpnModoDeOperao.setDisabledTextColor(new Color(0, 0, 0));
		txtpnModoDeOperao.setText("Modo de Opera\u00E7\u00E3o");
		panel.add(txtpnModoDeOperao, "cell 0 5,grow");
		ButtonGroup groupSpec1 = new ButtonGroup();
		
		JRadioButton rdbtnDiscreto = new JRadioButton("Único");
		rdbtnDiscreto.setFocusPainted(false);
		rdbtnDiscreto.setBackground(new Color(0, 0, 51));
		rdbtnDiscreto.setOpaque(false);
		rdbtnDiscreto.requestFocus();
		rdbtnDiscreto.setForeground(Color.LIGHT_GRAY);
		panel.add(rdbtnDiscreto,
				"flowx,cell 0 6,alignx center,aligny top");
		panel.transferFocus();
		groupSpec1.add(rdbtnDiscreto);
		rdbtnDiscreto.setActionCommand("once");
		rdbtnDiscreto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if ("once".equals(event.getActionCommand())) {
			        btnParar.setEnabled(false);
			        specPanel.toggleReadOnce(true);
			        btnAdquirir.setEnabled(true);
			    } else {
			        btnParar.setEnabled(false);
			        specPanel.toggleReadOnce(false);
			    }
			}
		});
		
		JRadioButton rdbtnContnuo = new JRadioButton("Cont\u00EDnuo");
		rdbtnContnuo.setFocusPainted(false);
		rdbtnContnuo.setBackground(new Color(0, 0, 51));
		rdbtnContnuo.setOpaque(false);
		rdbtnContnuo.setForeground(Color.LIGHT_GRAY);
		rdbtnContnuo.setSelected(true);
		rdbtnContnuo.requestFocus();
		rdbtnContnuo.setActionCommand("cont");
		rdbtnContnuo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if ("cont".equals(event.getActionCommand())) {
			        btnParar.setEnabled(true);
			        specPanel.toggleReadOnce(false);
			        btnParar.setEnabled(true);
			    } else {
			        btnParar.setEnabled(false);
			        specPanel.toggleReadOnce(true);
			    }
			}
		});
		panel.add(rdbtnContnuo, "cell 0 6,alignx center,aligny top");
		groupSpec1.add(rdbtnContnuo);
		
		JTextPane txtpnUnidade = new JTextPane();
		txtpnUnidade.setPreferredSize(new Dimension(6, 24));
		txtpnUnidade.setMargin(new Insets(5, 5, 5, 5));
		txtpnUnidade.setBorder(new LineBorder(Color.DARK_GRAY));
		txtpnUnidade.setForeground(Color.LIGHT_GRAY);
		txtpnUnidade.setBackground(new Color(25, 25, 112));
		txtpnUnidade.setEditable(false);
		txtpnUnidade.setDisabledTextColor(new Color(0, 0, 0));
		txtpnUnidade.setText("Unidade");
		panel.add(txtpnUnidade, "cell 0 7,grow");
		ButtonGroup groupSpec2 = new ButtonGroup();
		
		JRadioButton rdbtnCounts = new JRadioButton("Counts");
		rdbtnCounts.setFocusPainted(false);
		rdbtnCounts.setBackground(new Color(0, 0, 51));
		rdbtnCounts.setOpaque(false);
		rdbtnCounts.setForeground(Color.LIGHT_GRAY);
		rdbtnCounts.requestFocus();
		panel.add(rdbtnCounts,
				"flowx,cell 0 8,alignx center,aligny top");
		groupSpec2.add(rdbtnCounts);
		rdbtnCounts.setSelected(true);
		rdbtnCounts.setActionCommand("Counts");
		rdbtnCounts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if ("Counts".equals(event.getActionCommand())) {
			        specPanel.setImage("Counts");
			        specPanel.reloadTitle();
			    } else {
			    	specPanel.setImage("mV");
			    	specPanel.reloadTitle();
			    }
			}
		});
		
		JRadioButton rdbtnMv = new JRadioButton("mV");
		rdbtnMv.setFocusPainted(false);
		rdbtnMv.setBackground(new Color(0, 0, 51));
		rdbtnMv.setOpaque(false);
		rdbtnMv.setForeground(Color.LIGHT_GRAY);
		panel.add(rdbtnMv, "cell 0 8,alignx center,aligny top");
		groupSpec2.add(rdbtnMv);
		rdbtnMv.setActionCommand("mV");
		rdbtnMv.requestFocus();
		rdbtnMv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if ("mV".equals(event.getActionCommand())) {
			        specPanel.setImage("mV");
			        specPanel.reloadTitle();
			    } else {
			    	specPanel.setImage("Counts");
			    	specPanel.reloadTitle();
			    }
			}
		});

		JTextPane txtpnFaixaEspectral = new JTextPane();
		txtpnFaixaEspectral.setPreferredSize(new Dimension(6, 24));
		txtpnFaixaEspectral.setMargin(new Insets(5, 5, 5, 5));
		txtpnFaixaEspectral.setBorder(new LineBorder(Color.DARK_GRAY));
		txtpnFaixaEspectral.setForeground(Color.LIGHT_GRAY);
		txtpnFaixaEspectral.setBackground(new Color(25, 25, 112));
		txtpnFaixaEspectral.setEditable(false);
		txtpnFaixaEspectral.setDisabledTextColor(new Color(0, 0, 0));
		txtpnFaixaEspectral.setText("Faixa Espectral");
		panel.add(txtpnFaixaEspectral, "cell 0 9,grow");

		JTextField textField = new JTextField();
		textField.setForeground(Color.LIGHT_GRAY);
		textField.setOpaque(false);
		panel.add(textField, "flowx,cell 0 10,growx");
		textField.setColumns(10);

		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setBackground(new Color(0, 0, 51));
		textPane.setOpaque(false);
		textPane.setForeground(Color.LIGHT_GRAY);
		textPane.setText("-");
		textPane.transferFocus();
		panel.add(textPane, "cell 0 10");

		JTextField textField_1 = new JTextField();
		textField_1.setForeground(Color.LIGHT_GRAY);
		textField_1.setOpaque(false);
		textField_1.requestFocus();
		
		panel.add(textField_1, "cell 0 10");
		textField_1.setColumns(10);

		JTextPane txtpnNm = new JTextPane();
		txtpnNm.setEditable(false);
		txtpnNm.setBackground(new Color(0, 0, 51));
		txtpnNm.setOpaque(false);
		txtpnNm.setForeground(Color.LIGHT_GRAY);
		txtpnNm.setText("nm");
		panel.add(txtpnNm, "cell 0 10");

		JTextPane txtpnTempoDeIntegrao = new JTextPane();
		txtpnTempoDeIntegrao.setPreferredSize(new Dimension(6, 24));
		txtpnTempoDeIntegrao.setMargin(new Insets(5, 5, 5, 5));
		txtpnTempoDeIntegrao.setBorder(new LineBorder(Color.DARK_GRAY));
		txtpnTempoDeIntegrao.setForeground(Color.LIGHT_GRAY);
		txtpnTempoDeIntegrao.setBackground(new Color(25, 25, 112));
		txtpnTempoDeIntegrao.setEditable(false);
		txtpnTempoDeIntegrao.setDisabledTextColor(new Color(0, 0, 0));
		txtpnTempoDeIntegrao.setText("Tempo de Integra\u00E7\u00E3o");
		panel.add(txtpnTempoDeIntegrao, "cell 0 11,grow");

		JTextField textField_2 = new JTextField();
		textField_2.setForeground(Color.LIGHT_GRAY);
		textField_2.setOpaque(false);
		textField_2.requestFocus();
		panel.add(textField_2, "flowx,cell 0 12,growx");
		textField_2.setColumns(10);

		JTextPane txtpnTms = new JTextPane();
		txtpnTms.setEditable(false);
		txtpnTms.setBackground(new Color(0, 0, 51));
		txtpnTms.setOpaque(false);
		txtpnTms.setForeground(Color.LIGHT_GRAY);
		txtpnTms.setText("t (ms)");
		panel.add(txtpnTms, "cell 0 12");

		JTextPane txtpnAmostrasParaPromediao = new JTextPane();
		txtpnAmostrasParaPromediao.setPreferredSize(new Dimension(6, 24));
		txtpnAmostrasParaPromediao.setMargin(new Insets(5, 5, 5, 5));
		txtpnAmostrasParaPromediao.setBorder(new LineBorder(Color.DARK_GRAY));
		txtpnAmostrasParaPromediao.setForeground(Color.LIGHT_GRAY);
		txtpnAmostrasParaPromediao.setBackground(new Color(25, 25, 112));
		txtpnAmostrasParaPromediao.setEditable(false);
		txtpnAmostrasParaPromediao.setDisabledTextColor(new Color(0, 0, 0));
		txtpnAmostrasParaPromediao
				.setText("Amostras para Promedia\u00E7\u00E3o");
		panel.add(txtpnAmostrasParaPromediao, "cell 0 13,grow");

		JTextField textField_3 = new JTextField();
		textField_3.setForeground(Color.LIGHT_GRAY);
		textField_3.setOpaque(false);
		textField_3.requestFocus();
		panel.add(textField_3, "cell 0 14,growx");
		textField_3.setColumns(10);

		JPanel panel_5 = new JPanel();
		panel_5.setBackground(new Color(0, 0, 51));
		panel.add(panel_5, "cell 0 15,grow");

	}

	private JMenuBar getMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(getArquivoMenu());
		menuBar.add(getPreferenciasMenu());
		menuBar.add(getAjudaMenu());
		return menuBar;
	}

	private JMenu getArquivoMenu() {
		JMenu arquivoMenu = new JMenu("Arquivo");
		arquivoMenu.setForeground(Color.BLACK);
		arquivoMenu.setMnemonic(KeyEvent.VK_A); // set mnemonic to A
		arquivoMenu.add(abrirItem());
		arquivoMenu.add(salvarItem());
		arquivoMenu.add(exportarItem());
		arquivoMenu.add(exportarImagemItem());
		arquivoMenu.add(sairItem());
		return arquivoMenu;
	}

	private JMenuItem abrirItem() {
		JMenuItem abrirItem = new JMenuItem("Abrir... (Alt+a)");
		abrirItem.setMnemonic('a');
		abrirItem.addActionListener(new AbrirAction());
		return abrirItem;
	}

	private JMenuItem salvarItem() {
		JMenuItem salvarItem = new JMenuItem("Salvar... (Alt+s)");
		salvarItem.setMnemonic('s');
		salvarItem.addActionListener(new SalvarAction(getFrame()));
		return salvarItem;
	}

	private JMenuItem exportarItem() {
		JMenuItem exportarItem = new JMenuItem("Exportar (csv)... (Alt+s)");
		exportarItem.setMnemonic('e');
		exportarItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JOptionPane
						.showMessageDialog(
								getFrame(),
								"Este é o submenu 'Exportar...'. Em breve voc� poder� exportar arquivos csv.",
								"Exportar...	(Alt+e)",
								JOptionPane.PLAIN_MESSAGE);
			}
		});
		return exportarItem;
	}

	private JMenuItem exportarImagemItem() {
		JMenuItem exportarImagemItem = new JMenuItem(
				"Exportar imagem (jpeg, PNG)... (Alt+i)");
		exportarImagemItem.setMnemonic('i');
		exportarImagemItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JOptionPane
						.showMessageDialog(
								getFrame(),
								"Este é o submenu 'Exportar imagem...'. Em breve voc� poder� exportar arquivos jpeg, PNG.",
								"Exportar...	(Alt+e)",
								JOptionPane.PLAIN_MESSAGE);
			}
		});
		return exportarImagemItem;
	}

	private JMenuItem sairItem() {
		JMenuItem sairItem = new JMenuItem("Sair");
		sairItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});
		return sairItem;
	}

	private JMenu getPreferenciasMenu() {
		JMenu preferenciasMenu = new JMenu("Preferências");
		preferenciasMenu.setForeground(new Color(0, 0, 0));
		preferenciasMenu.setMnemonic('p');
		preferenciasMenu.add(graficoMenu());
		preferenciasMenu.add(idiomaMenu());
		return preferenciasMenu;
	}

	private JMenu graficoMenu() {
		JMenu graficoMenu = new JMenu("Gráficos (Alt+g)");
		graficoMenu.setMnemonic('g');
		graficoMenu.add(fundoItem());
		graficoMenu.add(curvaItem());
		graficoMenu.add(gridItem());
		graficoMenu.add(autoEscalarItem());
		return graficoMenu;
	}

	private JMenuItem fundoItem() {
		JMenuItem fundoItem = new JMenuItem("- Fundo (cor)");
		fundoItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JOptionPane.showMessageDialog(getFrame(),
						"Este é o submenu 'fundo'", "- Fundo (cor)",
						JOptionPane.PLAIN_MESSAGE);
			}
		});
		return fundoItem;
	}

	private JMenuItem curvaItem() {
		JMenuItem curvaItem = new JMenuItem("- Curva (cor)");
		curvaItem.setMnemonic('c');

		curvaItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JOptionPane.showMessageDialog(getFrame(),
						"Este � o submenu 'Curva'.", "- Curva (cor)",
						JOptionPane.PLAIN_MESSAGE);
			}
		});
		return curvaItem;
	}

	private JMenuItem gridItem() {
		JMenuItem gridItem = new JMenuItem("- Grid");
		gridItem.setMnemonic('g');
		gridItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JOptionPane.showMessageDialog(getFrame(),
						"Este � o submenu 'Grid'.", "- Grid",
						JOptionPane.PLAIN_MESSAGE);
			}
		});
		return gridItem;
	}

	private JMenuItem autoEscalarItem() {
		JMenuItem autoEscalarItem = new JMenuItem("- Autoescalar");
		autoEscalarItem.setMnemonic('a');

		autoEscalarItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JOptionPane.showMessageDialog(getFrame(),
						"Este � o submenu 'Autoescalar'.", "- Autoescalar",
						JOptionPane.PLAIN_MESSAGE);
			}
		});
		return autoEscalarItem;
	}

	private JMenu idiomaMenu() {
		JMenu idiomaMenu = new JMenu("Idioma (Alt+i)");
		idiomaMenu.setMnemonic('i');
		idiomaMenu.add(portuguesItem());
		idiomaMenu.add(englishItem());
		return idiomaMenu;
	}

	private JMenuItem portuguesItem() {
		JMenuItem portuguesItem = new JMenuItem("- Portugu�s");
		portuguesItem.setMnemonic('p');
		portuguesItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JOptionPane.showMessageDialog(getFrame(),
						"Este � o submenu 'portugues'", "- Portugu�s",
						JOptionPane.PLAIN_MESSAGE);
			}
		});
		return portuguesItem;
	}

	private JMenuItem englishItem() {
		JMenuItem englishItem = new JMenuItem("- English");
		englishItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JOptionPane.showMessageDialog(getFrame(),
						"Este � o submenu 'english'.", "- English",
						JOptionPane.PLAIN_MESSAGE);
			}
		});
		return englishItem;
	}

	private JMenu getAjudaMenu() {
		JMenu ajudaMenu = new JMenu("Ajuda");
		ajudaMenu.setForeground(Color.BLACK);
		ajudaMenu.setMnemonic('h');
		ajudaMenu.add(instrucoesItem());
		ajudaMenu.add(versaoItem());
		return ajudaMenu;
	}

	private JMenuItem instrucoesItem() {
		JMenuItem instrucoesItem = new JMenuItem("Instru��es de uso");
		instrucoesItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JOptionPane.showMessageDialog(getFrame(),
						"Este � o submenu 'instru��es de uso'",
						"Instru��es de uso", JOptionPane.PLAIN_MESSAGE);
			}
		});
		return instrucoesItem;
	}

	private JMenuItem versaoItem() {
		JMenuItem versaoItem = new JMenuItem("Vers�o");
		versaoItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JOptionPane.showMessageDialog(getFrame(),
						"Este � o submenu 'vers�o'.", "Vers�o",
						JOptionPane.PLAIN_MESSAGE);
			}
		});
		return versaoItem;
	}

	public JPanel getDegreePanel() {
		return degreePanel;
	}

	public void setDegreePanel(JPanel degreePanel) {
		this.degreePanel = degreePanel;
	}

	public JPanel getCoefPanel() {
		return coefPanel;
	}

	public JPanel getCalibPanel() {
		return calibPanel;
	}

	public void setCalibPanel(JPanel calibPanel) {
		this.calibPanel = calibPanel;
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public JPanel getPanel() {
		return panel;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}

}
