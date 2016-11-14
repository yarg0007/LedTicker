package com.ebay.ticker.services;

import java.util.Random;

import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;

public class TwitterKnockKnockFeed extends Thread implements ServiceOutput, ServiceState {

	/** Interval to check for new weather conditions. (milliseconds) */
	private static final int UPDATE_INTERVAL = 3000000; // 50 minutes.
	
	private boolean running;
	
	private ResponseList<Status> userPostings = null;
	
	private String message = "";
	
	@Override
	public void run() {
		
		super.run();
		
		System.out.println("Knock Knock Joke Service Started");
		
		TwitterFactory factory = new TwitterFactory();
	    AccessToken accessToken = new AccessToken("201948712-tJG9MJw0X97jmMVfq7KIBA2ji8bsK6pd24z1BqzO", "PnDOt9yuTEJhvrXfhHmGi8IO8E5KvnMdKI2bl2309905A");
	    Twitter twitter = factory.getInstance();
	    twitter.setOAuthConsumer("DLAzv7NzqKasvhi8XVSc8g", "epltq4JuK14YmGEqqLV5OpaYycNdYzIbYDYAJirgIe4");
	    twitter.setOAuthAccessToken(accessToken);
	    
	    // Get the DailyKnockKnock user.
	    ResponseList<User> users = null;
	    
	    try {
			users = twitter.searchUsers("DailyKnockKnock", 1);
		} catch (TwitterException e2) {
			e2.printStackTrace();
		}
	    
	    if (users == null) {
	    	return;
	    } else if (users.size() != 1) {
	    	return;
	    }
	    
	    if (users != null && users.size() == 1) {
	    
		    User user = users.get(0);
		    
		    while (running) {
		    
		    	System.out.println("Getting new Knock Knock Jokes.");
			    try {
			    	if (userPostings != null) {
				    	synchronized (userPostings) {
				    		userPostings = twitter.getUserTimeline(user.getId(), new Paging(1, user.getStatusesCount()));
				    	}
			    	} else {
			    		userPostings = twitter.getUserTimeline(user.getId(), new Paging(1, user.getStatusesCount()));
			    	}
				} catch (TwitterException e2) {
					e2.printStackTrace();
				}
			    System.out.println("Knock Knock Joke Service Sleeping.");
				// --------------------------------------------------------
				// Sleep for the specified interval before the next update.
				// --------------------------------------------------------
				try {
					this.sleep(UPDATE_INTERVAL);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		    }
		}
	}
	
	// -------------------------------------------------------------------------
	// Required by ServiceState
	// -------------------------------------------------------------------------
	
	@Override
	public void startService() {
		running = true;
		this.start();
	}
	
	@Override
	public void stopService() {
		running = false;
		this.interrupt();
	}
	
	// -------------------------------------------------------------------------
	// Required by ServiceOutput
	// -------------------------------------------------------------------------
	
	@Override
	public String getTickerMessage() {
		
		synchronized (userPostings) {
			Random rand = new Random();
			int randomIndex = rand.nextInt(userPostings.size()-1);
			message = userPostings.get(randomIndex).getText();
		}
		
		message = message.replace("\n", " ");
		
		message = "{RTL}                    " +
				"     "+message+
				"                              ";
		
		return message;
	}

}
