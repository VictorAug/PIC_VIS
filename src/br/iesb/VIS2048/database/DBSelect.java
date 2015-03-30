package br.iesb.VIS2048.database;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

public class DBSelect extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DBSelect dialog = new DBSelect();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DBSelect() {
		DBHandler dbHandler = new DBHandler();
		dbHandler .updateCollectionList();
		ArrayList collectionList = dbHandler.getCollectionList();
		setBounds(100, 100, 399, 186);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[80px][grow]", "[grow][][][]"));
		{
			JLabel lblNewLabel = new JLabel("Abrir:");
			contentPanel.add(lblNewLabel, "cell 0 0,alignx trailing");
		}
		{
			JComboBox comboBox = new JComboBox();
			contentPanel.add(comboBox, "cell 1 0,growx");
			comboBox.removeAll();
			for (int i = 0; i < collectionList.size(); i++) {
				comboBox.addItem(collectionList.get(i));
			}
		}
		{
			JLabel lblNomeDoArquivo = new JLabel("Nome do Arquivo:\r\n");
			contentPanel.add(lblNomeDoArquivo, "cell 0 1,alignx trailing");
		}
		{
			textField = new JTextField();
			contentPanel.add(textField, "cell 1 1,growx");
			textField.setColumns(10);
		}
		{
			JLabel lblNmeroDeAmostras = new JLabel("Número de Amostras:");
			contentPanel.add(lblNmeroDeAmostras, "cell 0 2,alignx trailing");
		}
		{
			textField_1 = new JTextField();
			contentPanel.add(textField_1, "cell 1 2,growx");
			textField_1.setColumns(10);
		}
		{
			JLabel lblPerodo = new JLabel("Período:");
			contentPanel.add(lblPerodo, "cell 0 3,alignx trailing");
		}
		{
			textField_2 = new JTextField();
			contentPanel.add(textField_2, "cell 1 3,growx");
			textField_2.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		
		
	}

}
