package com.ebay.ticker.timer;

public interface TickerTimer {

	/**
	 * Calculate the time the message will be on screen, including transition
	 * time.
	 * @param message Message to display for the associated transition type.
	 * @return Time in milliseconds (1000 = 1 sec)
	 */
	public int calculateDisplayTime(String message);
}
