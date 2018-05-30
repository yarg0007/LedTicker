package com.ebay.ticker;

import java.util.Scanner;

/**
 * https://github.com/rxtx/rxtx
 */
public class LedTicker {

	private TickerLoop loop;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		LedTicker ticker = new LedTicker();
		TickerGui tickerGui = null;

		for (String arg : args) {
			System.out.println(arg);
			if (arg.equalsIgnoreCase("useGui=true")) {
				tickerGui = new TickerGui(ticker.getTickerLoop());
			}
		}

		ticker.startTickerLoop();

		if (tickerGui == null) {
			Scanner keyboard = new Scanner(System.in);
			System.out.println("Press enter to quit.");
			String input = keyboard.nextLine();
			ticker.getTickerLoop().stopTickerLoop();
		}
	}

	public LedTicker() {
		loop = new TickerLoop();
	}

	public void startTickerLoop() {
		loop.startTickerLoop();
	}

	public TickerLoop getTickerLoop() {
		return loop;
	}
}
