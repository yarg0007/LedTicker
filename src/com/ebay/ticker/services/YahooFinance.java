package com.ebay.ticker.services;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class YahooFinance extends Thread implements ServiceOutput, ServiceState {

	/** URL to call for EBAY stock info. Uses Yahoo services. */
	private static final String SERVICE_URL =
			"http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20in%20(%22EBAY%22)&env=http://datatables.org/alltables.env&format=xml";
			//"http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20in%20%28%22EBAY%22%29&env=store://datatables.org/alltableswithkeys&format=xml";
	
	/** Interval to check for new finance data. (milliseconds) */
	private static final int UPDATE_INTERVAL = 300000; // 5 minutes.
	
	private boolean running;
	
	private String message = "{RTL}                    Stocks by Yahoo Finance     EBAY:";
	
	@Override
	public void run() {
		
		super.run();
		
		URL url = null;
		HttpURLConnection conn = null;
		StringBuffer response = new StringBuffer();
		
		while (running) {
		
			url = null;
			conn = null;
			response.setLength(0);
			
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
				
				System.out.println("\nSending 'GET' request to URL : " + SERVICE_URL);
				System.out.println("Response Code : " + conn.getResponseCode());
		 
				if (conn.getResponseCode() != 200) {
					System.out.println("ERROR CODE: "+conn.getResponseCode());
//					throw new RuntimeException("Failed : HTTP error code : "
//							+ conn.getResponseCode());
				} else {
					String output;
					System.out.println("Output from Server .... \n");
					while ((output = br.readLine()) != null) {
						response.append(output);
//						System.out.println(output);
					}
				}
				br.close();
				
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} finally {
				conn.disconnect();
			}
			
			
			if (response.length() > 0) {
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
				
				if (dest != null) {
					NodeList lastTradePriceTag = dest.getElementsByTagName("LastTradePriceOnly");
					String tradePrice = lastTradePriceTag.item(0).getTextContent();
		
					NodeList changeTag = dest.getElementsByTagName("Change");
					String change = changeTag.item(0).getTextContent();
					
					message = "{RTL}                    Stocks by Yahoo Finance" +
							"     EBAY: "+tradePrice+" "+change +
							"                              ";
				}
			}
			
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
		return message;
	}

}
