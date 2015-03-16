package br.iesb.VIS2048.database;

import java.awt.Color;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import net.miginfocom.swing.MigLayout;
import br.iesb.VIS2048.frame.Chart;

/**
 * Class DBViewer.
 */
public class DBViewer extends JDialog {

	/** Atributo DB coll. */
	public DBChartCollection DBColl = new DBChartCollection();
	public DBHandler db = new DBHandler();
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 2654392620198211140L;

	/** Atributo panel. */
	private JPanel panel;

	/**
	 * Launch the application.
	 */
	public DBViewer() {
		setResizable(false);
		setBounds(100, 100, 932, 425);
		{
			{

			}
		}
		getContentPane().setLayout(
				new MigLayout("", "[160px:160px][grow]", "[grow][33px]"));
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane
			.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane
			.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			getContentPane().add(scrollPane, "cell 0 0,grow");
			DBHandler db = new DBHandler();
			DefaultMutableTreeNode mutableTreeNode = new DefaultMutableTreeNode(
					DBHandler.getDBFileCollection());
			JTree tree = new JTree(mutableTreeNode);
			DBChartCollection dbCollection = new DBChartCollection();

			tree.addTreeSelectionListener(new TreeSelectionListener() {
				public void valueChanged(TreeSelectionEvent arg0) {
					String path = "";
					BufferedReader br = null;
					String sCurrentLine;
					DBChartCollection dbBuffer = null;
					for (int i = 0; i < tree.getSelectionPath().getPathCount(); i++) {
						path += tree.getSelectionPath().getPathComponent(i);
					}
					if (Files.exists(Paths.get(path + "\\index.txt")))
						try {
							br = new BufferedReader(new FileReader(path + "\\" + "index.txt"));
							while ((sCurrentLine = br.readLine()) != null) {
								panel.removeAll();
								dbBuffer = (DBChartCollection) DBHandler.loadGZipObject(path +"\\"+ sCurrentLine+".vis");
								for(int i=0; i<dbBuffer.count(); i++){
									dbCollection.addChart(dbBuffer.getChart(i));
									int j = 0, k = 0;
									for (int l = 0; l < dbCollection.count(); l++) {
										Chart chart = dbCollection.getChartQueue().get(l);
										chart.setToolTipText("<html>"
												// + "<p><b>Nome:</b> " +
												// chart.getName() +
												// "</p>"
												+ "<p><b>Data:</b> "
												+ new Date(chart.getTimestamp()
														.longValue()) + "</p>"
														// + "<p><b>Resolu��o:</b> " +
														// chart.getNumberOfSamples() +
														// "</p>"
														// + "<p><b>Descri��o:</b> " +
														// chart.getDescription() + "</p>"
														+ "</html>");
										panel.add(chart, "cell " + j++ + " " + k);
										panel.updateUI();
										if (j == 6) {
											j = 0;
											k++;
										}
									}									
								}
							}
						} catch (IOException e) {
							e.printStackTrace();
						}

				}
			});
			scrollPane.setViewportView(tree);
			db.getCollectionList().forEach(
					a -> mutableTreeNode.add(new DefaultMutableTreeNode(a)));
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane
			.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			getContentPane().add(scrollPane, "cell 1 0,grow");
			{
				panel = new JPanel();
				panel.setBackground(Color.WHITE);
				scrollPane.setViewportView(panel);
				panel.setLayout(new MigLayout("", "[]", "[]"));
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, "cell 0 1 2 1,growx,aligny top");
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
