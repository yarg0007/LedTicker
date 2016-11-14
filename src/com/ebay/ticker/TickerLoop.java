package com.ebay.ticker;

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.ebay.ticker.serial.SerialConnection;
import com.ebay.ticker.services.BDNMessage;
import com.ebay.ticker.services.CoffeeMessage;
import com.ebay.ticker.services.GithubJokes;
import com.ebay.ticker.services.TickerMessage;
import com.ebay.ticker.services.TwitterKnockKnockFeed;
import com.ebay.ticker.services.YahooFinance;
import com.ebay.ticker.services.YahooWeather;
import com.ebay.ticker.services.YahooWorldNews;
import com.ebay.ticker.timer.RightToLeftTickerTimer;
import com.ebay.ticker.util.AlphaProtocolEncoder;

public class TickerLoop extends Thread {

	private int sleepTime = 30000;
	
	private SerialConnection serialConnection;
	
	DateFormat dateFormat;
	
	private YahooWeather weather;
	
	private YahooFinance finance;
	
	private YahooWorldNews worldNews;
	
	private TickerMessage tickerMessage;
	
	private BDNMessage bdnMessage;
	
	private CoffeeMessage coffeeMessage;
	
	private TwitterKnockKnockFeed knockKnockFeed;
	
	private GithubJokes githubJokes;
	
	private boolean running;
	
	public TickerLoop() {
		
		serialConnection = new SerialConnection(null);
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
		
		weather = new YahooWeather();
//		weather.startService();
		
		finance = new YahooFinance();
//		finance.startService();
		
		worldNews = new YahooWorldNews();
//		worldNews.startService();
		
		tickerMessage = new TickerMessage();
		bdnMessage = new BDNMessage();
		coffeeMessage = new CoffeeMessage();
		
		knockKnockFeed = new TwitterKnockKnockFeed();
//		knockKnockFeed.startService();
		
		githubJokes = new GithubJokes();
		githubJokes.startService();
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
		weather.stopService();
		finance.stopService();
		worldNews.stopService();
		knockKnockFeed.stopService();
		githubJokes.stopService();
		running = false;
		this.interrupt();
	}
	
	@Override
	public void run() {

		super.run();
		
		int displayIndex = 0;
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2015);
		cal.set(Calendar.MONTH, Calendar.OCTOBER);
		cal.set(Calendar.DAY_OF_MONTH, 15);
		cal.set(Calendar.HOUR_OF_DAY, 17);
		cal.set(Calendar.MINUTE, 30);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		
		while (running) {
			
			
			String message = githubJokes.getTickerMessage();
			sendMessage(message);
			
//			switch (displayIndex) {
//			
//			case 0:
//				sendMessage("Did you hear about the guy who got hit in the head with a can of soda? He was lucky it was a soft drink.");
//				break;
//			case 1:
//				sendMessage("How do you make antifreeze?                        Steal her blanket.");
//				break;
//			case 2:
//				sendMessage("There was an explosion at a cheese factory in France.                       De-brie went everywhere.");
//				break;
//			default:
//				displayIndex = -1;
//				break;
//			
//			}
//			
//			displayIndex++;
			
	
//		String message = AlphaProtocolEncoder.encodeMessage("{SLOT}ABCDEFG HIJKLMNOPQRS TUVWXYZ 123456789, ,,,!GUDLBADRU{FIREWORK}GRSQOBJDEVCALW EGCBANDGREBAZEANLO ERGBICZAT ILEBACAO{ANIMAL}ABCDEFGHIJKL MNOPQRSTUVWXYZ 1234");
//		RightToLeftTickerTimer rtlTimer = new RightToLeftTickerTimer();
		// rtlTimer.calculateDisplayTime("ABCDEFGHIJKLMNOPQRSTUVWXYZ 123456789,,,,!GUDLBADRUGRSQOBJDEVCALWEGCBANDGREBAZEANLOERGBICZATILEBACAO ABCDEFGHIJKLMNOPQRSTUVWXYZ 1234567890RTG");
//		String message = AlphaProtocolEncoder.encodeMessage("{RL}"+timeMessage);

		//		String message = AlphaProtocolEncoder.encodeMessage("{WELCOME}Becky{FIREWORK}I LIKE TO PARTY!");
//		System.out.println("Going to try to send message Test");
//		System.out.println("encoded message: "+message);
		
		
		}
		
		try {
			serialConnection.closeConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Success");
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
