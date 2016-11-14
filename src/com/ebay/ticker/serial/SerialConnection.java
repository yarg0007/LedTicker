package com.ebay.ticker.serial;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

public class SerialConnection {

	// The driver for mac serial connection to led ticker:
	// http://www.ftdichip.com/Drivers/VCP/MacOSX/FTDIUSBSerialDriver_v2_2_18.dmg
	// You may need to create /var/lock/ with r+w permissions.
	
	/* Default serial port to connect to. */
	private static final String defaultSerialPortName = "/dev/cu.usbserial";
	
	/** Serial port to connect to, default or user specified. */
	private String serialPortName = defaultSerialPortName;
	
	/** Port open connection timeout. */
	private int ioTimeoutMS = 5000;
	
	/** Serial port instance. */
	private SerialPort serialPort;
	
	/** Serial port output stream. */
	private OutputStream outputStream;
	
	/** Writes strings to serial port output stream. */
	private OutputStreamWriter outputStreamWriter;
	
	/**
	 * Create a new serial connection instance to the serial port specified.
	 * @param serialPortName Serial port name to connect to. If null, defaults
	 * to {@value #defaultSerialPortName}.
	 */
	public SerialConnection(String serialPortName) {
		
		if (serialPortName != null) {
			this.serialPortName = serialPortName;
		}
	}
	
	/**
	 * Open the connection with the sign.
	 * @throws NoSuchPortException
	 * @throws PortInUseException
	 * @throws UnsupportedCommOperationException
	 * @throws IOException 
	 */
	public void openConnection() throws NoSuchPortException, PortInUseException, UnsupportedCommOperationException, IOException {
		
		CommPortIdentifier portID = 
				CommPortIdentifier.getPortIdentifier(serialPortName);
		
		serialPort = (SerialPort) portID.open(
				getClass().getName(), ioTimeoutMS);
		
		serialPort.setSerialPortParams(
				9600, 
				SerialPort.DATABITS_7, 
				SerialPort.STOPBITS_2, 
				SerialPort.PARITY_EVEN);
		
		serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
		
		outputStream = serialPort.getOutputStream();
		outputStreamWriter = new OutputStreamWriter(outputStream);
	}
	
	/**
	 * Close the serial connection.
	 * @throws IOException 
	 */
	public void closeConnection() throws IOException {
		System.out.println("Closing connection");
		serialPort.close();
		outputStreamWriter.close();
		outputStream.close();
		System.out.println("Connection closed.");
	}
	
	/**
	 * Send a message to the led sign. The message is expected to be encoded
	 * according to the Alpha Communication Protocol.
	 * @param message Alpha Communication Protocol encoded message.
	 * @throws IOException
	 */
	public void sendMessage(String message) throws IOException {
		
//		int esc = 0x1B;
//		message = "\0\0\0\0\0\001Z00\002AA"+ (char)esc + " aTest Message\004";
//		message = "\0\0\0\0\0\001Z00\002AA"+ (char)esc + " n9Test Message\004";
		
//		System.out.println("SENDING: "+message);
		outputStreamWriter.write(message);
		outputStreamWriter.flush();
//		System.out.println("Done sending.");
	}
}
