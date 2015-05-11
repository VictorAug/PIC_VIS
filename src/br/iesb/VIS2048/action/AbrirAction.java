package br.iesb.VIS2048.action;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 * Classe AbrirAction.
 */
public class AbrirAction extends AbstractAction {

    /** Constante de serialização do objeto. */
    private static final long serialVersionUID = 1L;

    private JTextArea textArea;

    /**
     * Instancia um novo objeto AbrirAction.
     */
    public AbrirAction() {
	super("Abrir");
	this.putValue(Action.SHORT_DESCRIPTION, "Abrir arquivo");
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent actionEvent) {
	JFileChooser fileChooser = new JFileChooser();
	int resp = fileChooser.showOpenDialog(textArea);
	if (resp != JFileChooser.APPROVE_OPTION) {
	    return;
	}
	openFile(fileChooser.getSelectedFile());
    }

    /**
     * Abrir o arquivo.
     *
     * @param arquivo
     *            the file
     */
    private void openFile(File arquivo) {
	try {
	    FileReader in = new FileReader(arquivo);
	    in.read();
	    in.close();
	} catch (IOException e) {
	    JOptionPane.showMessageDialog(null, e.getMessage());
	}
    }

}
