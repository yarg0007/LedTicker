package com.ebay.ticker.util;

import java.util.HashMap;

public class AlphaProtocolEncoder {

	private static HashMap<String, String> encodingMap = null;
	
	public static String encodeMessage(String message) throws IllegalArgumentException {
		
		if (encodingMap == null) {
			initializeEncodingMap();
		}

		String encodedMessage = AlphaProtocolConstants.INIT;
		
		// Chunk the message apart, looking for {} encoded constants to swap
		// out.
		int openCurlyBraceIndex = 0;
		int closingCurlyBraceIndex = 0;
		
		// If no opening display command was provided, default to
		// right to left.
		openCurlyBraceIndex = message.indexOf("{");
		if (openCurlyBraceIndex != 0) {
			encodedMessage += AlphaProtocolConstants.DISPLAY_COMMAND_SIGNATURE+AlphaProtocolConstants.RIGHT_TO_LEFT;
		}
		
		// Process message.
		while (message.length() > 0) {
			
			openCurlyBraceIndex = message.indexOf(
					AlphaProtocolEncoderConstants.AP_ENCODER_OPEN);
			
			if (openCurlyBraceIndex == -1) {
				encodedMessage += message;
				message = "";
			} else {
				
				// Take the data before the opening curly brace and add
				// it directly into the encodedMessage.
				encodedMessage += message.substring(0, openCurlyBraceIndex);
				message = message.substring(openCurlyBraceIndex);
				
				// Pluck off the entire curly brace chunk.
				closingCurlyBraceIndex = message.indexOf(
						AlphaProtocolEncoderConstants.AP_ENCODER_CLOSE);
				
				if (closingCurlyBraceIndex >= (message.length()-1)) {
					message = "";
				} else {
					String curlyChunk = message.substring(1, closingCurlyBraceIndex);
					message = message.substring(closingCurlyBraceIndex+1);
					
					String encodedChunk = encodingMap.get(curlyChunk);
					
					if (encodedChunk != null) {
						encodedMessage += AlphaProtocolConstants.DISPLAY_COMMAND_SIGNATURE+encodedChunk;
					}
					
				}
				
			}
		}
		
		encodedMessage += AlphaProtocolConstants.END_OF_TRANSMISSION;
		
		return encodedMessage;
	}
	
	private static void initializeEncodingMap() {
		
		encodingMap = new HashMap<String, String>();
//		encodingMap.put(AlphaProtocolEncoderConstants.AUTO, AlphaProtocolConstants.AUTO);
		encodingMap.put(AlphaProtocolEncoderConstants.FLASH, AlphaProtocolConstants.FLASH);
		encodingMap.put(AlphaProtocolEncoderConstants.HOLD, AlphaProtocolConstants.HOLD);
		encodingMap.put(AlphaProtocolEncoderConstants.RIGHT_TO_LEFT, AlphaProtocolConstants.RIGHT_TO_LEFT);
//		encodingMap.put(AlphaProtocolEncoderConstants.ROLL_DOWN, AlphaProtocolConstants.ROLL_DOWN);
//		encodingMap.put(AlphaProtocolEncoderConstants.ROLL_IN, AlphaProtocolConstants.ROLL_IN);
//		encodingMap.put(AlphaProtocolEncoderConstants.ROLL_LEFT, AlphaProtocolConstants.ROLL_LEFT);
//		encodingMap.put(AlphaProtocolEncoderConstants.ROLL_OUT, AlphaProtocolConstants.ROLL_OUT);
//		encodingMap.put(AlphaProtocolEncoderConstants.ROLL_RIGHT, AlphaProtocolConstants.ROLL_RIGHT);
//		encodingMap.put(AlphaProtocolEncoderConstants.ROLL_UP, AlphaProtocolConstants.ROLL_UP);
		
//		encodingMap.put(AlphaProtocolEncoderConstants.WIPE_DOWN, AlphaProtocolConstants.WIPE_DOWN);
//		encodingMap.put(AlphaProtocolEncoderConstants.WIPE_IN, AlphaProtocolConstants.WIPE_IN);
//		encodingMap.put(AlphaProtocolEncoderConstants.WIPE_LEFT, AlphaProtocolConstants.WIPE_LEFT);
//		encodingMap.put(AlphaProtocolEncoderConstants.WIPE_OUT, AlphaProtocolConstants.WIPE_OUT);
//		encodingMap.put(AlphaProtocolEncoderConstants.WIPE_RIGHT, AlphaProtocolConstants.WIPE_RIGHT);
//		encodingMap.put(AlphaProtocolEncoderConstants.WIPE_UP, AlphaProtocolConstants.WIPE_UP);

		encodingMap.put(AlphaProtocolEncoderConstants.SPECIAL_BALLOON, AlphaProtocolConstants.SPECIAL_BALLOON);
		encodingMap.put(AlphaProtocolEncoderConstants.SPECIAL_CHERRY_BOMB, AlphaProtocolConstants.SPECIAL_CHERRY_BOMB);
//		encodingMap.put(AlphaProtocolEncoderConstants.SPECIAL_DONT_DRINK_AND_DRIVE, AlphaProtocolConstants.SPECIAL_DONT_DRINK_AND_DRIVE);
		encodingMap.put(AlphaProtocolEncoderConstants.SPECIAL_FIREWORKS, AlphaProtocolConstants.SPECIAL_FIREWORKS);
		encodingMap.put(AlphaProtocolEncoderConstants.SPECIAL_INTERLOCK, AlphaProtocolConstants.SPECIAL_INTERLOCK);
		encodingMap.put(AlphaProtocolEncoderConstants.SPECIAL_NEWS_FLASH, AlphaProtocolConstants.SPECIAL_NEWS_FLASH);
//		encodingMap.put(AlphaProtocolEncoderConstants.SPECIAL_NO_SMOKING, AlphaProtocolConstants.SPECIAL_NO_SMOKING);
		encodingMap.put(AlphaProtocolEncoderConstants.SPECIAL_RUNNING_ANIMAL, AlphaProtocolConstants.SPECIAL_RUNNING_ANIMAL);
		encodingMap.put(AlphaProtocolEncoderConstants.SPECIAL_SLOT_MACHINE, AlphaProtocolConstants.SPECIAL_SLOT_MACHINE);
		encodingMap.put(AlphaProtocolEncoderConstants.SPECIAL_SNOW, AlphaProtocolConstants.SPECIAL_SNOW);
		encodingMap.put(AlphaProtocolEncoderConstants.SPECIAL_SPARKLE, AlphaProtocolConstants.SPECIAL_SPARKLE);
		encodingMap.put(AlphaProtocolEncoderConstants.SPECIAL_SPRAY, AlphaProtocolConstants.SPECIAL_SPRAY);
		encodingMap.put(AlphaProtocolEncoderConstants.SPECIAL_STARBURST, AlphaProtocolConstants.SPECIAL_STARBURST);
		encodingMap.put(AlphaProtocolEncoderConstants.SPECIAL_SWITCH, AlphaProtocolConstants.SPECIAL_SWITCH);
		encodingMap.put(AlphaProtocolEncoderConstants.SPECIAL_THANK_YOU, AlphaProtocolConstants.SPECIAL_THANK_YOU);
		encodingMap.put(AlphaProtocolEncoderConstants.SPECIAL_TWINKLE, AlphaProtocolConstants.SPECIAL_TWINKLE);
		encodingMap.put(AlphaProtocolEncoderConstants.SPECIAL_WAVE, AlphaProtocolConstants.SPECIAL_WAVE);
		encodingMap.put(AlphaProtocolEncoderConstants.SPECIAL_WELCOME, AlphaProtocolConstants.SPECIAL_WELCOME);
	}
}
