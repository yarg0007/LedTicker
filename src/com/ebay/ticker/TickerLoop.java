package com.ebay.ticker;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.ebay.pi.GpioButton;
import com.ebay.ticker.serial.SerialConnection;
import com.ebay.ticker.services.GithubJokes;
import com.ebay.ticker.services.NetworkInfo;
import com.ebay.ticker.util.AlphaProtocolEncoder;

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;

public class TickerLoop extends Thread {

	private int sleepTime = 30000;

	private SerialConnection serialConnection;

	DateFormat dateFormat;

	private GpioButton gpioButton;

	private GithubJokes githubJokes;

	private NetworkInfo networkInfo;

	private boolean running;

	public TickerLoop() {

		serialConnection = new SerialConnection("/dev/ttyUSB0");
		try {
			serialConnection.openConnection();
		} catch (NoSuchPortException e) {
			e.printStackTrace();
			serialConnection = null;
		} catch (PortInUseException e) {
			e.printStackTrace();
			serialConnection = null;
		} catch (UnsupportedCommOperationException e) {
			e.printStackTrace();
			serialConnection = null;
		}  catch (IOException e) {
			e.printStackTrace();
			serialConnection = null;
		}

		dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy h:mm a");

		gpioButton = new GpioButton();

		githubJokes = new GithubJokes();
		githubJokes.startService();

		networkInfo = new NetworkInfo();
		networkInfo.startService();
	}

	/**
	 * Start the ticker loop.
	 */
	public void startTickerLoop() {
		running = true;
		this.start();
	}

	/**
	 * Stop the ticker loop.
	 */
	public void stopTickerLoop() {
		githubJokes.stopService();
		networkInfo.stopService();
		running = false;
		this.interrupt();
		System.out.println("Ticker loop - terminate loop requested.");
	}

	@Override
	public void run() {

		super.run();

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2015);
		cal.set(Calendar.MONTH, Calendar.OCTOBER);
		cal.set(Calendar.DAY_OF_MONTH, 15);
		cal.set(Calendar.HOUR_OF_DAY, 17);
		cal.set(Calendar.MINUTE, 30);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		while (running) {

			// TODO: if gpio button pressed display the network info. Otherwise, display joke.

			String message = "";

			if (gpioButton.wasButtonPressed()) {
				message = networkInfo.getTickerMessage();
			} else {
				message = githubJokes.getTickerMessage();
			}

			sendMessage(message);


			//		String message = AlphaProtocolEncoder.encodeMessage("{SLOT}ABCDEFG HIJKLMNOPQRS TUVWXYZ 123456789, ,,,!GUDLBADRU{FIREWORK}GRSQOBJDEVCALW EGCBANDGREBAZEANLO ERGBICZAT ILEBACAO{ANIMAL}ABCDEFGHIJKL MNOPQRSTUVWXYZ 1234");
			//		RightToLeftTickerTimer rtlTimer = new RightToLeftTickerTimer();
			// rtlTimer.calculateDisplayTime("ABCDEFGHIJKLMNOPQRSTUVWXYZ 123456789,,,,!GUDLBADRUGRSQOBJDEVCALWEGCBANDGREBAZEANLOERGBICZATILEBACAO ABCDEFGHIJKLMNOPQRSTUVWXYZ 1234567890RTG");
			//		String message = AlphaProtocolEncoder.encodeMessage("{RL}"+timeMessage);

			//		String message = AlphaProtocolEncoder.encodeMessage("{WELCOME}Becky{FIREWORK}I LIKE TO PARTY!");
			//		System.out.println("Going to try to send message Test");
			//		System.out.println("encoded message: "+message);


		}

		System.out.println("Exited the ticker loop, running = false");

		try {
			if (serialConnection != null) {
				serialConnection.closeConnection();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Success - ticker loop terminated.");
	}

	// -------------------------------------------------------------------------
	// Private methods
	// -------------------------------------------------------------------------

	private void sendMessage(String message) {

		String paddedMessage =
				"{RTL}              " +
						message +
						"                    ";

		int duration = 4000 + (90 * message.length());
		sendMessage(paddedMessage, duration);
	}

	/**
	 * Send raw message to the ticker. Allows for {<transition>} formatting
	 * included in the message.
	 * @param message Message to display on ticker.
	 */
	private void sendMessage(String message, Integer sleep) {

		message = AlphaProtocolEncoder.encodeMessage(message);

		try {
			if (serialConnection != null) {
				serialConnection.sendMessage(message);
			} else {
				System.out.println("send message: "+message);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			if (sleep == null) {
				Thread.sleep(sleepTime);
			} else {
				Thread.sleep(sleep);
			}
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
}
