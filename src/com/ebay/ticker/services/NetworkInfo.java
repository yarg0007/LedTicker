package com.ebay.ticker.services;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;

public class NetworkInfo implements ServiceOutput, ServiceState {

	static final String[] networkPrefixes = new String[] {"et", "en", "wl"};

	public static void main(String args[]) throws SocketException {
		NetworkInfo networkInfo = new NetworkInfo();
		System.out.println(networkInfo.getTickerMessage());
	}

	public void startService() {

	}

	public void stopService() {

	}

	public String getTickerMessage() {
		return displayInterfaceInformation();
	}

	private String displayInterfaceInformation() {

		StringBuilder info = new StringBuilder();

		Enumeration<NetworkInterface> nets = null;
		try {
			nets = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			return "Unable to get network interfaces. - SocketException.";
		}

		for (NetworkInterface netint : Collections.list(nets)) {

			for (String prefix : networkPrefixes) {
				if (netint.getName().startsWith(prefix)) {
					info.append(displayInterfaceInformation(netint));
					info.append("        ");
					break;
				}
			}
		}
		System.out.println("We should see: " + info.toString());
		return info.toString();
	}

	private String displayInterfaceInformation(NetworkInterface netint) {

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(String.format("Display name: %s ", netint.getDisplayName()));
		Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
		for (InetAddress inetAddress : Collections.list(inetAddresses)) {
			stringBuilder.append(String.format("- InetAddress: %s", inetAddress));
		}
		return stringBuilder.toString();
	}
}
