package com.ebay.ticker.timer;

public class RightToLeftTickerTimer implements TickerTimer {

	// Base amount of time to display a single character (milliseconds).
	private static final int BASE_TIME_SECONDS = 2000;

	// Total time required for MAX_CHARACTERS_ON_SCREEN to move across ticker.
	private static final int SECONDS_PER_CHARACTER = 105;

	public int calculateDisplayTime(String message) {

		int displayTime =
				BASE_TIME_SECONDS + message.length() * SECONDS_PER_CHARACTER;

		return displayTime;
	}

}
