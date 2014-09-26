package br.iesb.VIS2048.model;

import java.awt.event.KeyEvent;

import javax.swing.JMenu;

public class JMenuUtil {

	private final JMenuItemUtil jMenuItemUtil = new JMenuItemUtil();

	public JMenuUtil() {
	}

	public JMenuItemUtil getjMenuItemUtil() {
		return jMenuItemUtil;
	}

	public JMenu getArquivoMenu() {
		JMenu arquivoMenu = new JMenu("Arquivo"); // create file menu
		arquivoMenu.setMnemonic(KeyEvent.VK_A); // set mnemonic to A
		arquivoMenu.add(jMenuItemUtil.getAbrirItem());
		arquivoMenu.add(jMenuItemUtil.getSalvarItem());
		arquivoMenu.add(jMenuItemUtil.getExportarItem());
		arquivoMenu.add(jMenuItemUtil.getExportarImagemItem());
		arquivoMenu.add(jMenuItemUtil.getSairItem());
		arquivoMenu.add(jMenuItemUtil.getExportarItem());
		arquivoMenu.add(jMenuItemUtil.getExportarImagemItem());
		arquivoMenu.add(jMenuItemUtil.getSairItem());
		return arquivoMenu;
	}

	public JMenu getPreferenciasMenu() {
		JMenu preferenciasMenu = new JMenu("Preferências");
		preferenciasMenu.setMnemonic('p');
		preferenciasMenu.add(getGraficoMenu());
		preferenciasMenu.add(getIdiomaMenu());
		return preferenciasMenu;
	}

	public JMenu getGraficoMenu() {
		JMenu graficoMenu = new JMenu("Grafico (Alt+g)");
		graficoMenu.setMnemonic('g');
		graficoMenu.add(jMenuItemUtil.getFundoItem());
		graficoMenu.add(jMenuItemUtil.getCurvaItem());
		graficoMenu.add(jMenuItemUtil.getGridItem());
		graficoMenu.add(jMenuItemUtil.getAutoEscalarItem());
		return graficoMenu;
	}

	public JMenu getIdiomaMenu() {
		JMenu idiomaMenu = new JMenu("Idioma (Alt+i)");
		idiomaMenu.setMnemonic('i');
		idiomaMenu.add(jMenuItemUtil.getPortuguesItem());
		idiomaMenu.add(jMenuItemUtil.getEnglishItem());
		return idiomaMenu;
	}

	public JMenu getAjudaMenu() {
		JMenu ajudaMenu = new JMenu("Ajuda");
		ajudaMenu.setMnemonic('h');
		ajudaMenu.add(jMenuItemUtil.getInstrucoesItem());
		ajudaMenu.add(jMenuItemUtil.getVersaoItem());
		return ajudaMenu;
	}

}
