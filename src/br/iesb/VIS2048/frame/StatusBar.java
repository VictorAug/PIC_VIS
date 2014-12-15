package br.iesb.vis.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EtchedBorder;

// TODO: Auto-generated Javadoc
/**
 * The Class StatusBar.
 */
public class StatusBar extends JPanel implements Runnable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The msg. */
	private JLabel msg = new JLabel();
	
	/** The progress. */
	private JProgressBar progress = new JProgressBar();
	
	/** The status text. */
	private String statusText;

	/**
	 * The constructor.
	 * 
	 */
	public StatusBar() {
		progress.setMinimum(0);
		progress.setMaximum(100);
		progress.setMinimumSize(new Dimension(100, 20));
		progress.setSize(new Dimension(100, 20));

		msg.setMinimumSize(new Dimension(100, 20));
		msg.setSize(new Dimension(100, 20));
		msg.setFont(new Font("Dialog", Font.PLAIN, 10));
		msg.setForeground(Color.black);

		setLayout(new BorderLayout());
		setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		add(msg, BorderLayout.CENTER);
		add(progress, BorderLayout.EAST);
	}

	/**
	 * Show status.
	 *
	 * @param s            the status string to show
	 */
	public void showStatus(String s) {
		msg.setText(s);
		paintImmediately(getBounds());
	}

	/**
	 * Show progress.
	 *
	 * @param percent            the percentage of the progress bar to be shown
	 */
	public void showProgress(int percent) {
		progress.setValue(percent);
	}

	/**
	 * Inc progress.
	 *
	 * @param delataPercent            an increment for the progrss bar
	 */
	public void incProgress(int delataPercent) {
		progress.setValue(progress.getValue() + delataPercent);
	}

	/**
	 * Do fake progress.
	 *
	 * @param s            the status bar text
	 * @param work            the work that has to be done, i.e. the maximum value for the
	 *            progress
	 */
	public synchronized void doFakeProgress(String s, int work) {
		statusText = s;
		showStatus(statusText + "... not implemented yet ...");
		progress.setMaximum(work);
		progress.setValue(0);
		Thread t = new Thread(this);
		t.start();
	}

	/**
	 * Run.
	 *
	 * @see java.lang.Runnable#run()
	 */
	public synchronized void run() {
		int work = progress.getMaximum();
		for (int i = 0; i < work; i++) {
			progress.setValue(i);
			repaint();
			try {
				wait(10);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		showStatus(statusText + "... done.");
		repaint();
		try {
			wait(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		progress.setValue(0);
		showStatus("");
		repaint();
	}

}
