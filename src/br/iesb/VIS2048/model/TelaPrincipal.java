package br.iesb.VIS2048.model;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

public class TelaPrincipal extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Color bgColor = Color.BLUE;

	public TelaPrincipal() {
		super("VIS2048 - espectrômetro de emissão");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(780, 600);
		setBackground(bgColor);
		setLayout(new FlowLayout());
		setVisible(true);
		setResizable(true);

		JMenuBar bar = new JMenuBar();
		setJMenuBar(bar);
		bar.add(new JMenuUtil().getArquivoMenu());
		bar.add(new JMenuUtil().getPreferenciasMenu());
		bar.add(new JMenuUtil().getAjudaMenu());
	}

	public static void main(String[] args) {
		new TelaPrincipal();
	}
	
}