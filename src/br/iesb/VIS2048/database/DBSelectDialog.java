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

/**
 * Classe DBSelect.
 */
public class DBSelectDialog extends JDialog {

    /** Constante de serialização do objeto. */
    private static final long serialVersionUID = 1L;

    private final JPanel contentPanel = new JPanel();

    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;

    private JLabel lblNomeDoArquivo;
    private JLabel lblNmeroDeAmostras;
    private JLabel lblPerodo;

    /**
     * Cria a caixa de diálogo.
     *
     * @param dbHandler
     *            the db handler
     */
    public DBSelectDialog(DBHandler dbHandler) {
	setModalityType(ModalityType.APPLICATION_MODAL);
	setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
	dbHandler.updateCollectionList();
	ArrayList<String> collectionList = dbHandler.getCollectionList();
	JComboBox<String> comboBox = new JComboBox<String>();
	comboBox.addItem("Novo Arquivo...");
	comboBox.addActionListener(arg0 -> {
	    if (((String) comboBox.getSelectedItem()).equals("Novo Arquivo...")) {
		textField.setText(dbHandler.getMainDB());
		textField.setEditable(true);
		System.out.println("Create new");
	    } else {
		textField.setText((String) comboBox.getSelectedItem());
		textField.setEditable(false);
		textField_1.setText("");
		textField_2.setText("");
	    }
	});
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
	    contentPanel.add(comboBox, "cell 1 0,growx");
	    // comboBox.removeAll();
	    for (int i = 0; i < collectionList.size(); i++) {
		comboBox.addItem(collectionList.get(i));
	    }
	}
	{
	    lblNomeDoArquivo = new JLabel("Nome do Arquivo:\r\n");
	    contentPanel.add(lblNomeDoArquivo, "cell 0 1,alignx trailing");
	}
	{
	    textField = new JTextField();
	    contentPanel.add(textField, "cell 1 1,growx");
	    textField.setText(dbHandler.getMainDB());
	    textField.setColumns(10);
	}
	{
	    lblNmeroDeAmostras = new JLabel("Número de Amostras:");
	    contentPanel.add(lblNmeroDeAmostras, "cell 0 2,alignx trailing");
	}
	{
	    textField_1 = new JTextField();
	    textField_1.setEditable(false);
	    contentPanel.add(textField_1, "cell 1 2,growx");
	    textField_1.setColumns(10);
	}
	{
	    lblPerodo = new JLabel("Período:");
	    contentPanel.add(lblPerodo, "cell 0 3,alignx trailing");
	}
	{
	    textField_2 = new JTextField();
	    textField_2.setEditable(false);
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
		okButton.addActionListener(e -> {
		    dbHandler.setMainDB((String) textField.getText());
		    dispose();
		});
	    }
	}

    }

}
