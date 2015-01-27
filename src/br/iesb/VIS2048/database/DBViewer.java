package br.iesb.VIS2048.database;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import net.miginfocom.swing.MigLayout;

import javax.swing.ScrollPaneConstants;

import br.iesb.VIS2048.frame.Chart;

import java.awt.GridLayout;
import java.awt.GridBagLayout;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class DBViewer extends JDialog {
	public DBChartCollection DBColl = new DBChartCollection();
	/**
	 * 
	 */
	private static final long serialVersionUID = 2654392620198211140L;
	JPanel panel;
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
		getContentPane().setLayout(new MigLayout("", "[160px:160px][grow]", "[grow][33px]"));
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			getContentPane().add(scrollPane, "cell 0 0,grow");
			DBHandler DB = new DBHandler();
			DefaultMutableTreeNode Collection = new DefaultMutableTreeNode(DBHandler.getDBFileCollection());
			JTree tree = new JTree(Collection);
			
			tree.addTreeSelectionListener(new TreeSelectionListener() {
				public void valueChanged(TreeSelectionEvent arg0) {
					String Path = "";
					for(int i=0; i<tree.getSelectionPath().getPathCount(); i++){
						Path += tree.getSelectionPath().getPathComponent(i);
					}
					if(Path.matches(".*\\.vis$")){
						panel.removeAll();
						DBChartCollection DBCollection = (DBChartCollection) DBHandler.loadGZipObject(Path);
						int j=0, k=0;
						for(int i=0; i<DBCollection.count(); i++){
							Chart chart = DBCollection.getChartQueue().get(i);
							chart.setToolTipText(
									"<html>"
									//+ "<p><b>Nome:</b> " + chart.getName() + "</p>"
									+ "<p><b>Data:</b> " + new java.util.Date((long)chart.getTimestamp()) + "</p>"
									//+ "<p><b>Resolu��o:</b> " + chart.getNumberOfSamples() + "</p>"
									//+ "<p><b>Descri��o:</b> " + chart.getDescription() + "</p>"
									+ "</html>");
							panel.add(chart, "cell " + j++ + " " + k);
							panel.updateUI();
							if(j==6){
								j=0; k++;
							}
						}
					}					
				}
			});
			scrollPane.setViewportView(tree);
			ArrayList<String> collectionList = DB.getCollectionList();
			for(String a : collectionList){
				Collection.add(new DefaultMutableTreeNode(a));
			}
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
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
