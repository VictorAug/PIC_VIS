package br.iesb.VIS2048.model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class JMenuItemUtil {

	private final TelaPrincipal telaPrincipal = new TelaPrincipal();

	public JMenuItemUtil() {
		getAbrirItem();
		getSalvarItem();
		getExportarItem();
		getExportarImagemItem();
		getSairItem();

		getFundoItem();
		getCurvaItem();
		getGridItem();
		getAutoEscalarItem();

		getPortuguesItem();
		getEnglishItem();

		getInstrucoesItem();
		getVersaoItem();
	}

	public final JMenuItem getAbrirItem() {
		JMenuItem abrirItem = new JMenuItem("Abrir... (Alt+a)");
		abrirItem.setMnemonic('a');

		abrirItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JOptionPane
						.showMessageDialog(
								telaPrincipal,
								"Este é o submenu 'Abrir...'. Em breve você poderá abrir projetos de análise espectral.",
								"Abrir...	(Alt+a)", JOptionPane.PLAIN_MESSAGE);
			}
		});
		return abrirItem;
	}

	public final JMenuItem getSalvarItem() {
		JMenuItem salvarItem = new JMenuItem("Salvar... (Alt+s)");
		salvarItem.setMnemonic('s');

		salvarItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JOptionPane
						.showMessageDialog(
								telaPrincipal,
								"Este é o submenu 'Salvar...'. Em breve você poderá salvar projetos de análise espectral.",
								"Salvar...	(Alt+s)", JOptionPane.PLAIN_MESSAGE);
			}
		});
		return salvarItem;
	}

	public final JMenuItem getExportarItem() {
		JMenuItem exportarItem = new JMenuItem("Exportar (csv)... (Alt+s)");
		exportarItem.setMnemonic('e');

		exportarItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JOptionPane
						.showMessageDialog(
								telaPrincipal,
								"Este é o submenu 'Exportar...'. Em breve você poderá exportar arquivos csv.",
								"Exportar...	(Alt+e)",
								JOptionPane.PLAIN_MESSAGE);
			}
		});
		return exportarItem;
	}

	public final JMenuItem getExportarImagemItem() {
		JMenuItem exportarImagemItem = new JMenuItem(
				"Exportar imagem (jpeg, PNG)... (Alt+i)");
		exportarImagemItem.setMnemonic('i');

		exportarImagemItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JOptionPane
						.showMessageDialog(
								telaPrincipal,
								"Este é o submenu 'Exportar imagem...'. Em breve você poderá exportar arquivos jpeg, PNG.",
								"Exportar...	(Alt+e)",
								JOptionPane.PLAIN_MESSAGE);
			}
		});
		return exportarImagemItem;
	}

	public final JMenuItem getSairItem() {
		JMenuItem sairItem = new JMenuItem("Sair");

		sairItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});
		return sairItem;
	}

	public final JMenuItem getFundoItem() {
		JMenuItem fundoItem = new JMenuItem("- Fundo (cor)");

		fundoItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JOptionPane.showMessageDialog(telaPrincipal,
						"Este é o submenu 'fundo'", "- Fundo (cor)",
						JOptionPane.PLAIN_MESSAGE);
			}
		});
		return fundoItem;
	}

	public final JMenuItem getCurvaItem() {
		JMenuItem curvaItem = new JMenuItem("- Curva (cor)");
		curvaItem.setMnemonic('c');

		curvaItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JOptionPane.showMessageDialog(telaPrincipal,
						"Este é o submenu 'Curva'.", "- Curva (cor)",
						JOptionPane.PLAIN_MESSAGE);
			}
		});
		return curvaItem;
	}

	public final JMenuItem getGridItem() {
		JMenuItem gridItem = new JMenuItem("- Grid");
		gridItem.setMnemonic('g');

		gridItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JOptionPane.showMessageDialog(telaPrincipal,
						"Este é o submenu 'Grid'.", "- Grid",
						JOptionPane.PLAIN_MESSAGE);
			}
		});
		return gridItem;
	}

	public final JMenuItem getAutoEscalarItem() {
		JMenuItem autoEscalarItem = new JMenuItem("- Autoescalar");
		autoEscalarItem.setMnemonic('a');

		autoEscalarItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JOptionPane.showMessageDialog(telaPrincipal,
						"Este é o submenu 'Autoescalar'.", "- Autoescalar",
						JOptionPane.PLAIN_MESSAGE);
			}
		});
		return autoEscalarItem;
	}

	public final JMenuItem getPortuguesItem() {
		JMenuItem portuguesItem = new JMenuItem("- Português");
		portuguesItem.setMnemonic('p');

		portuguesItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JOptionPane.showMessageDialog(telaPrincipal,
						"Este é o submenu 'portugues'", "- Português",
						JOptionPane.PLAIN_MESSAGE);
			}
		});
		return portuguesItem;
	}

	public final JMenuItem getEnglishItem() {
		JMenuItem englishItem = new JMenuItem("- English");

		englishItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JOptionPane.showMessageDialog(telaPrincipal,
						"Este é o submenu 'english'.", "- English",
						JOptionPane.PLAIN_MESSAGE);
			}
		});
		return englishItem;
	}

	public final JMenuItem getInstrucoesItem() {
		JMenuItem instrucoesItem = new JMenuItem("Instruções de uso");

		instrucoesItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JOptionPane.showMessageDialog(telaPrincipal,
						"Este é o submenu 'instruções de uso'",
						"Instruções de uso", JOptionPane.PLAIN_MESSAGE);
			}
		});
		return instrucoesItem;
	}

	public final JMenuItem getVersaoItem() {
		JMenuItem versaoItem = new JMenuItem("Versão");

		versaoItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JOptionPane.showMessageDialog(telaPrincipal,
						"Este é o submenu 'versão'.", "Versão",
						JOptionPane.PLAIN_MESSAGE);
			}
		});
		return versaoItem;
	}

}
