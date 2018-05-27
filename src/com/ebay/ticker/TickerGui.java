package com.ebay.ticker;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TickerGui extends JFrame implements ActionListener {

	private TickerLoop tickerLoop;

	private JButton stopButton;

	public TickerGui(TickerLoop tickerLoop) {
		this.tickerLoop = tickerLoop;

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
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {

		if (ae.getSource() == stopButton) {
			this.setVisible(false);
			System.out.println("Shutting down the LedTicker from GUI... Please wait.");
			tickerLoop.stopTickerLoop();

			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.exit(0);
		}
	}

}
