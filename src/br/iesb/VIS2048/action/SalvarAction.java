package br.iesb.VIS2048.action;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class SalvarAction extends AbstractAction {
	
	private static final long serialVersionUID = 1L;
//	private byte[] buffer = new byte[1024];
	private JFrame parent;
	private JTextArea textArea;
	
	public SalvarAction(JFrame frame) {
		super("Salvar");
		this.putValue(Action.SHORT_DESCRIPTION, "Abrir arquivo");
		this.parent = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser();
//		ZipOutputStream outputStream = new ZipOutputStream(new FileOutputStream());
		int res = fileChooser.showSaveDialog(this.parent);
        if(res == JFileChooser.APPROVE_OPTION){  
            File arq = fileChooser.getSelectedFile();  
            try{  
                   
                fileChooser.showOpenDialog(this.parent);  
     
                String path = fileChooser.getSelectedFile().getAbsolutePath();  
                System.out.println("fileChooser.getSelectedFile().getAbsolutePath(): " + path);  
  
                escreveArquivo(textArea.getText(), arq.getPath());  
  
                System.out.println("Nome do arquivo escolhido: " + arq.getPath());  
  
            } catch(IOException ioe){  
                JOptionPane.showMessageDialog(null, "N�o foi poss�vel salvar arquivo!");  
            }  
        }  
	}
	
	public void escreveArquivo(String conteudo, String fileName) throws IOException {  
		  
        PrintWriter out = new PrintWriter(new FileWriter(fileName));  
        out.print(conteudo);  
        out.close();  
        JOptionPane.showMessageDialog(null, "Arquivo salvo com sucesso!");  
  
    }  

}
