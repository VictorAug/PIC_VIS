package br.iesb.VIS2048.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.miginfocom.swing.MigLayout;
import br.iesb.VIS2048.database.DBChartCollection;

/**
 * Class ChartEditor.
 */
public class ChartEditor extends JDialog {

    /** Constante serialVersionUID. */
    private static final long serialVersionUID = 6356283623484026659L;

    /** Atributo content panel. */
    private final JPanel contentPanel = new JPanel();

    /** Atributo text field. */
    private JTextField textField;

    /** Atributo text field_1. */
    private JTextField textField_1;

    /** Atributo text field_2. */
    private JTextField textField_2;

    /** Atributo text field_3. */
    private JTextField textField_3;

    /** Atributo txt de xxx. */
    private JTextField txtDeXxx;

    /** Atributo text area. */
    private JTextArea textArea;

    /** Atributo panel. */
    private JPanel panel;

    /** Atributo btn new button. */
    private JButton btnNewButton;

    /** Atributo btn new button_1. */
    private JButton btnNewButton_1;

    /** Atributo chart collection. */
    private DBChartCollection chartCollection;

    /** Atributo selected chart. */
    private int selectedChart;

    /** Atributo selected. */
    private Chart selected;

    /** Atributo spinner. */
    private JSpinner spinner;

    /**
     * Launch the application.
     *
     * @param chartCollection
     *            the chart collection
     * @param selectedChart
     *            the selected chart
     */

    /**
     * Create the dialog.
     * 
     * @param chartCollection
     */
    public ChartEditor(DBChartCollection chartCollection, int selectedChart) {
	if (chartCollection == null)
	    return;
	else {
	    this.chartCollection = chartCollection;
	    this.selectedChart = selectedChart;
	}
	setBounds(100, 100, 504, 250);
	getContentPane().setLayout(new BorderLayout());
	contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
	getContentPane().add(contentPanel, BorderLayout.CENTER);
	contentPanel.setLayout(new MigLayout("", "[grow][grow]", "[grow]"));
	{
	    panel = new JPanel();
	    contentPanel.add(panel, "cell 0 0,alignx center,growy");
	    panel.setLayout(new MigLayout("", "[][][]", "[grow][25]"));
	    {
		selected = chartCollection.getChart(selectedChart);
		selected.setMaximumSize(new Dimension(112, 84));
		selected.setMinimumSize(new Dimension(112, 84));
		panel.add(selected, "cell 0 0 3 1,alignx center,growy");
	    }
	    {
		btnNewButton = new JButton("<");
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
		btnNewButton_1 = new JButton(">");
		btnNewButton_1.setMinimumSize(new Dimension(25, 25));
		btnNewButton_1.setMaximumSize(new Dimension(25, 25));
		btnNewButton_1.setPreferredSize(new Dimension(25, 25));
		btnNewButton_1.setMargin(new Insets(2, 2, 2, 2));
		panel.add(btnNewButton_1, "cell 2 1");
	    }
	    {
		txtDeXxx = new JTextField();
		txtDeXxx.setBorder(null);
		txtDeXxx.setEditable(false);
		txtDeXxx.setMaximumSize(new Dimension(40, 40));
		txtDeXxx.setSize(new Dimension(6, 25));
		txtDeXxx.setPreferredSize(new Dimension(6, 25));
		txtDeXxx.setMinimumSize(new Dimension(6, 25));
		panel.add(txtDeXxx, "cell 1 1,alignx left");
		txtDeXxx.setColumns(10);
	    }
	}
	{
	    JPanel panel2 = new JPanel();
	    contentPanel.add(panel2, "cell 1 0,grow");
	    panel2.setLayout(new MigLayout("", "[][grow]", "[][][][grow]"));
	    {
		JLabel lblNome = new JLabel("Data");
		panel2.add(lblNome, "cell 0 0,alignx trailing,aligny baseline");
	    }
	    {
		textField = new JTextField();
		textField.setEditable(false);
		panel2.add(textField, "cell 1 0,growx");
		textField.setColumns(10);
	    }
	    {
		JLabel lblNewLabel = new JLabel("Nome");
		panel2.add(lblNewLabel, "cell 0 1,alignx trailing");
	    }
	    {
		textField_1 = new JTextField();
		panel2.add(textField_1, "cell 1 1,growx");
		textField_1.setColumns(10);
	    }
	    {
		JLabel lblPonto = new JLabel("Ponto");
		panel2.add(lblPonto, "cell 0 2,alignx right");
	    }
	    {
		spinner = new JSpinner(new SpinnerNumberModel(selectedChart, 0, 2047, 1));
		spinner.setMaximumSize(new Dimension(60, 20));
		spinner.setMinimumSize(new Dimension(60, 20));
		spinner.setBounds(new Rectangle(0, 0, 10, 0));
		panel2.add(spinner, "flowx,cell 1 2,growx");
		spinner.addChangeListener(new ChangeListener() {
		    @Override
		    public void stateChanged(ChangeEvent arg0) {
			textField_2.setText("" + selected.getXyseries().getY((int) spinner.getValue()));
		    }
		});
	    }
	    {
		JLabel lblDescrio = new JLabel("Descrição");
		panel2.add(lblDescrio, "cell 0 3,alignx right");
	    }
	    {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panel2.add(scrollPane, "cell 1 3,grow");
		{
		    textArea = new JTextArea();
		    textArea.setLineWrap(true);
		    textArea.setWrapStyleWord(true);
		    textArea.setDropMode(DropMode.INSERT);
		    scrollPane.setViewportView(textArea);
		    textArea.setText(chartCollection.getChart(selectedChart).getDescription());
		}
	    }
	    {
		textField_2 = new JTextField();
		panel2.add(textField_2, "cell 1 2,growx");
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
		btnRemover.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent arg0) {
			chartCollection.getChartQueue().remove(selectedChart);
			if (selectedChart >= 1)
			    ChartEditor.this.selectedChart--;
			else if (chartCollection.count() > 0) {
			    ChartEditor.this.selectedChart = 0;
			} else if (chartCollection.count() == 0) {
			    dispose();
			}
			if (chartCollection.count() > 0)
			    updateView();
		    }
		});
	    }
	    {
		JButton okButton = new JButton("Salvar");
		okButton.setActionCommand("Salvar");
		buttonPane.add(okButton);
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
	btnNewButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		ChartEditor.this.selectedChart--;
		// else if()
		updateView();
	    }
	});
	btnNewButton_1.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		ChartEditor.this.selectedChart++;
		// else if()
		updateView();
	    }
	});
	textField_3.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		int value = Integer.parseInt(textField_3.getText()) - 1;
		if (value < 0 || value >= chartCollection.count())
		    return;
		else
		    ChartEditor.this.selectedChart = value;
		if (chartCollection.count() > 0)
		    updateView();
	    }
	});
	updateView();
    }

    /**
     * Update view.
     */
    void updateView() {
	panel.remove(selected);
	selected = chartCollection.getChart(selectedChart);
	textArea.setText(selected.getDescription());
	textField_3.setText((selectedChart + 1) + "");
	textField_1.setText(selected.getName());
	textField.setText(new SimpleDateFormat("dd 'de' MMMM 'de' YYYY").format(new Date(selected.getTimestamp())));
	txtDeXxx.setText("de " + chartCollection.count());
	selected.setMaximumSize(new Dimension(112, 84));
	selected.setMinimumSize(new Dimension(112, 84));

	if (ChartEditor.this.selectedChart == 0)
	    btnNewButton.setEnabled(false);
	else
	    btnNewButton.setEnabled(true);
	if (ChartEditor.this.selectedChart == chartCollection.count() - 1)
	    btnNewButton_1.setEnabled(false);
	else
	    btnNewButton_1.setEnabled(true);

	textField_2.setText("" + selected.getXyseries().getY((int) spinner.getValue()));
	panel.add(selected, "cell 0 0 3 1,alignx center,growy");
	panel.updateUI();
    }
}
