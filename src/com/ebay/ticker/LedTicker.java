package com.ebay.ticker;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * https://github.com/rxtx/rxtx
 */
@SuppressWarnings("serial")
public class LedTicker extends JFrame implements ActionListener {

	private TickerLoop loop;

	private JButton stopButton;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		LedTicker ticker = new LedTicker();
		ticker.startTickerLoop();
	}

	public LedTicker() {

		loop = new TickerLoop();

		this.setSize(300, 200);
		this.setTitle("LedTicker App");

		JPanel panel = new JPanel();
		stopButton = new JButton("STOP");
		Dimension stopButtonSize = new Dimension(280, 160);
		stopButton.setSize(stopButtonSize);
		stopButton.setPreferredSize(stopButtonSize);
		stopButton.setMinimumSize(stopButtonSize);
		stopButton.setMaximumSize(stopButtonSize);
		stopButton.addActionListener(this);

		panel.add(stopButton);
		this.add(panel);

	}

	public void startTickerLoop() {
		loop.startTickerLoop();
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {

		if (ae.getSource() == stopButton) {
			this.setVisible(false);
			loop.stopTickerLoop();
			System.out.println("Shutting down the LedTicker... Please wait.");

			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println("LedTicker process terminated.");
			System.exit(0);
		}
	}

}
