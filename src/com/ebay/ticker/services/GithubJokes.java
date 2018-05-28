package com.ebay.ticker.services;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class GithubJokes extends Thread implements ServiceOutput,
ServiceState {

	/** URL to call jokes */
	private static final String SERVICE_URL = "https://yarg0007.github.io/tickerjokes/";

	/** Interval to check for new weather conditions. (milliseconds) */
	private static final int UPDATE_INTERVAL = 1800000; // 30 minutes.

	private boolean running;

	private ArrayList<String> messages = new ArrayList<String>();

	private int messageIndex = 0;

	@Override
	public void run() {

		super.run();

		URL url = null;
		HttpURLConnection conn = null;
		StringBuffer response = new StringBuffer();

		while (running) {

			url = null;
			conn = null;
			response = new StringBuffer();

			// ------------------------------
			// Call the Yahoo Weather service
			// ------------------------------
			try {

				url = new URL(SERVICE_URL);
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				//				conn.setRequestProperty("Accept", "application/xml");
				conn.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
				conn.setRequestProperty("Accept","*/*");

				BufferedReader br = new BufferedReader(new InputStreamReader(
						(conn.getInputStream())));

				if (conn.getResponseCode() != 200) {
					System.out.println("ERROR CODE: "+conn.getResponseCode());
					throw new RuntimeException("Failed : HTTP error code : "
							+ conn.getResponseCode());
				}

				System.out.println("\nSending 'GET' request to URL : " + SERVICE_URL);
				System.out.println("Response Code : " + conn.getResponseCode());

				String output;
				System.out.println("Output from Server .... \n");
				while ((output = br.readLine()) != null) {
					response.append(output);
					//					System.out.println(output);
				}
				br.close();
				conn.disconnect();

			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} finally {
				conn.disconnect();
			}


			Document dest = null;
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder parser;
			try {
				parser = dbFactory.newDocumentBuilder();
				dest = parser.parse(new ByteArrayInputStream(response.toString().getBytes()));
			} catch (ParserConfigurationException e1) {
				e1.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			NodeList divTag = dest.getElementsByTagName("div");
			int numJokes = 3;
			if (divTag != null && divTag.getLength() != 0) {
				String numJokeString = divTag.item(0).getTextContent();
				if (numJokeString.startsWith("display:")) {
					String count = numJokeString.substring(8);
					try {
						numJokes = Integer.valueOf(count);
					} catch (NumberFormatException nfe) {
						numJokes = 3;
					}
				}
			}

			if (dest != null) {
				NodeList itemTags = dest.getElementsByTagName("td");

				if (itemTags != null) {

					synchronized (messages) {

						messages.clear();

						int total = numJokes;
						if (numJokes > itemTags.getLength()) {
							total = itemTags.getLength();
						} else if (numJokes <= 0) {
							total = itemTags.getLength();
						}

						for (int i = 0; i < total; i++) {

							String joke =
									itemTags.item(i).getTextContent();

							//							String message = "{RTL}                    " +
							//									"Headlines by Yahoo News        " +
							//									headline +
							//									"                                       ";

							messages.add(joke);
						}

						// If num jokes <= 0 then randomize the
						// full list of jokes.
						if (numJokes <= 0) {
							Collections.shuffle(messages);
						}
					}
				}
			}



			// --------------------------------------------------------
			// Sleep for the specified interval before the next update.
			// --------------------------------------------------------
			try {
				Thread.sleep(UPDATE_INTERVAL);
			} catch (InterruptedException e) {
				System.out.println("GithubJokes thread interrupted.");
			}
		}
	}

	// -------------------------------------------------------------------------
	// Required by ServiceState
	// -------------------------------------------------------------------------

	public void startService() {
		running = true;
		this.start();
	}

	public void stopService() {
		running = false;
		this.interrupt();
	}

	// -------------------------------------------------------------------------
	// Required by ServiceOutput
	// -------------------------------------------------------------------------

	public String getTickerMessage() {

		String message = "";

		synchronized (messages) {

			if (messages.size() > 0) {

				if (messageIndex >= messages.size()) {
					messageIndex = 0;
				}

				message = messages.get(messageIndex);
				messageIndex++;
			}
		}

		return message;
	}

}
