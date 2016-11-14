package com.ebay.ticker.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.ebay.ticker.util.TickerConstants;

public class TickerMessage implements ServiceOutput {
	
	private String SERVICE_URL = TickerConstants.TICKER_SERVER_BASE_URL + "/message";
	
	private String message = "";
	
	// -------------------------------------------------------------------------
	// Required by ServiceOutput
	// -------------------------------------------------------------------------
	
	@Override
	public String getTickerMessage() {
		
		URL url = null;
		HttpURLConnection conn = null;
		StringBuffer response = new StringBuffer();
			
		// ------------------------------
		// Call the ticker message service
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
		
			
		if (response != null && response.length() > 0) {
			String text = response.toString().substring(1);
			text = text.substring(0, text.length()-1);
			message = text;
			message += "                              ";
		}

		return message;
	}

}
