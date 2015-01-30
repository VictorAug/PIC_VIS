package br.iesb.VIS2048.frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.DropMode;
import javax.swing.JSpinner;

import br.iesb.VIS2048.database.DBChartCollection;

import java.awt.Rectangle;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JToggleButton;
import java.awt.Insets;

public class ChartEditor extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField txtDeXxx;

	/**
	 * Launch the application.
	 */
	
	/**
	 * Create the dialog.
	 * @param chartCollection 
	 */
	public ChartEditor(DBChartCollection chartCollection) {
		setBounds(100, 100, 504, 250);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[grow][grow]", "[grow]"));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, "cell 0 0,alignx center,growy");
			panel.setLayout(new MigLayout("", "[][][]", "[grow][25]"));
			{
				JToggleButton toggleButton = new JToggleButton("New toggle button");
				toggleButton.setMaximumSize(new Dimension(125, 85));
				toggleButton.setMinimumSize(new Dimension(125, 85));
				panel.add(toggleButton, "cell 0 0 3 1,alignx center,growy");
			}
			{
				JButton btnNewButton = new JButton("<");
				btnNewButton.setPreferredSize(new Dimension(25, 25));
				btnNewButton.setMinimumSize(new Dimension(25, 25));
				btnNewButton.setMargin(new Insets(2, 2, 2, 2));
				btnNewButton.setMaximumSize(new Dimension(25, 25));
				panel.add(btnNewButton, "flowx,cell 0 1");
			}
			{
				textField_3 = new JTextField();
				textField_3.setMaximumSize(new Dimension(60, 60));
				panel.add(textField_3, "flowx,cell 1 1,grow");
				textField_3.setColumns(10);
			}
			{
				JButton btnNewButton_1 = new JButton(">");
				btnNewButton_1.setMinimumSize(new Dimension(25, 25));
				btnNewButton_1.setMaximumSize(new Dimension(25, 25));
				btnNewButton_1.setPreferredSize(new Dimension(25, 25));
				btnNewButton_1.setMargin(new Insets(2, 2, 2, 2));
				panel.add(btnNewButton_1, "cell 2 1");
			}
			{
				txtDeXxx = new JTextField();
				txtDeXxx.setEditable(false);
				txtDeXxx.setMaximumSize(new Dimension(40, 40));
				txtDeXxx.setSize(new Dimension(6, 25));
				txtDeXxx.setPreferredSize(new Dimension(6, 25));
				txtDeXxx.setMinimumSize(new Dimension(6, 25));
				txtDeXxx.setText("de xxx");
				panel.add(txtDeXxx, "cell 1 1,alignx left");
				txtDeXxx.setColumns(10);
			}
		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, "cell 1 0,grow");
			panel.setLayout(new MigLayout("", "[][grow]", "[][][][grow]"));
			{
				JLabel lblNome = new JLabel("Timestamp");
				panel.add(lblNome, "cell 0 0,alignx trailing,aligny center");
			}
			{
				textField = new JTextField();
				textField.setEditable(false);
				panel.add(textField, "cell 1 0,growx");
				textField.setColumns(10);
			}
			{
				JLabel lblNewLabel = new JLabel("Nome");
				panel.add(lblNewLabel, "cell 0 1,alignx trailing");
			}
			{
				textField_1 = new JTextField();
				panel.add(textField_1, "cell 1 1,growx");
				textField_1.setColumns(10);
			}
			{
				JLabel lblPonto = new JLabel("Ponto");
				panel.add(lblPonto, "cell 0 2,alignx right");
			}
			{
				JSpinner spinner = new JSpinner();
				spinner.setMaximumSize(new Dimension(60, 20));
				spinner.setMinimumSize(new Dimension(60, 20));
				spinner.setBounds(new Rectangle(0, 0, 10, 0));
				panel.add(spinner, "flowx,cell 1 2,growx");
			}
			{
				JLabel lblDescrio = new JLabel("Descrição");
				panel.add(lblDescrio, "cell 0 3,alignx right");
			}
			{
				JScrollPane scrollPane = new JScrollPane();
				scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
				panel.add(scrollPane, "cell 1 3,grow");
				{
					JTextArea textArea = new JTextArea();
					textArea.setLineWrap(true);
					textArea.setWrapStyleWord(true);
					textArea.setDropMode(DropMode.INSERT);
					scrollPane.setViewportView(textArea);
				}
			}
			{
				textField_2 = new JTextField();
				panel.add(textField_2, "cell 1 2,growx");
				textField_2.setColumns(10);
			}
			
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnRemover = new JButton("Remover");
				buttonPane.add(btnRemover);
			}
			{
				JButton okButton = new JButton("Salvar");
				okButton.setActionCommand("Salvar");
				buttonPane.add(okButton);
				//getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				buttonPane.add(cancelButton);
				cancelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						dispose();
						
					}
				});
			}
		}
	}

}
