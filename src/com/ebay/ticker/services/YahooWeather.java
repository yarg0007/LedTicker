package com.ebay.ticker.services;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLDecoder;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;

public class YahooWeather extends Thread implements ServiceOutput, ServiceState {

	/** URL to call for Portland weather. Uses Yahoo services. */
	private static final String SERVICE_URL =
			"http://weather.yahooapis.com/forecastrss?w=2475687";
	
	/** Interval to check for new weather conditions. (milliseconds) */
	private static final int UPDATE_INTERVAL = 300000; // 5 minutes.
	
	private boolean running;
	
	private String message = "";
	
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

			if (dest != null) {
				NodeList currentConditions = dest.getElementsByTagName("yweather:condition");
				int size = currentConditions.getLength();
				
				for (int i = 0; i < size; i++) {
					System.out.println("HI");
				}
				
				Node conditions = currentConditions.item(0);
				String conditionsText = conditions.getAttributes().getNamedItem("text").getNodeValue().toString();
				String conditionsTemp = conditions.getAttributes().getNamedItem("temp").getNodeValue().toString();
				
				message = "{RTL}                    Weather by Yahoo Weather     Current Conditions: "+conditionsTemp+" F and "+conditionsText;
				message += "     Forecast:";
						
				NodeList forecast = dest.getElementsByTagName("yweather:forecast");
				size = forecast.getLength();
				for (int i = 0; i < size; i++) {
					
					Node forecastNode = forecast.item(i);
					
					String day = forecastNode.getAttributes().getNamedItem("day").getNodeValue().toString();
					String low = forecastNode.getAttributes().getNamedItem("low").getNodeValue().toString();
					String high = forecastNode.getAttributes().getNamedItem("high").getNodeValue().toString();
					String text = forecastNode.getAttributes().getNamedItem("text").getNodeValue().toString();
					
					message += "          "+day+" High: "+high+" Low: "+low+" and "+text;
				}
	
				message += "                              ";
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
