package com.ebay.ticker.util;

public class AlphaProtocolEncoderConstants {
	
	/** Signature that a special command should be processed. */
	public static final String AP_ENCODER_OPEN = "{";
	
	/** Closing character of signature that a special command should be processed. */
	public static final String AP_ENCODER_CLOSE = "}";

	/** Message travels right to left. */
	public static final String RIGHT_TO_LEFT = "RTL";
	
	/** Text is stationary. */
	public static final String HOLD = "HOLD";
	
	/** Message remains stationary and flashes. */
	public static final String FLASH = "FLASH";
	
	/** Previous message is pushed up by new message. */
	public static final String ROLL_UP = "RU";
	
	/** Previous message is pushed down by new message. */
	public static final String ROLL_DOWN = "RD";
	
	/** Previous message is pushed left by new message. */
	public static final String ROLL_LEFT = "RL";
	
	/** Previous message is pushed right by new message. */
	public static final String ROLL_RIGHT = "RR";
	
	/** New message is wiped over the old message from bottom up. */
	public static final String WIPE_UP = "WU";
	
	/** New message is wiped over the old message from top down. */
	public static final String WIPE_DOWN = "WD";
	
	/** New message is wiped over the old message from right to left. */
	public static final String WIPE_LEFT = "WL";
	
	/** New message is wiped over the old message from left to right. */
	public static final String WIPE_RIGHT = "WR";
	
	/** Various modes are called upon to display the message. */
	public static final String AUTO = "AUTO";
	
	/** Previous message is pushed towards the center by new message. */
	public static final String ROLL_IN = "RI";
	
	/** Previous message is pushed outwards from the center by new message. */
	public static final String ROLL_OUT = "RO";
	
	/** New message is wiped over the previous message in an inward direction.*/
	public static final String WIPE_IN = "WI";
	
	/** New message is wiped over the previous message in an outward direction.*/
	public static final String WIPE_OUT = "WO";
	
	/** Message will twinkle on the sign. */
	public static final String SPECIAL_TWINKLE = "TWINKLE";
	
	/** Message will sparkle over the current message. */
	public static final String SPECIAL_SPARKLE = "SPARKLE";
	
	/** Message will snow onto the display. */
	public static final String SPECIAL_SNOW = "SNOW";
	
	/** Interlock over current message along alternating rows. */
	public static final String SPECIAL_INTERLOCK = "INTERLOCK";
	
	/** Half of sign swipes up, the other half swipes down. */
	public static final String SPECIAL_SWITCH = "SWITCH";
	
	/** Vertical wave action behind message. */
	public static final String SPECIAL_WAVE = "WAVE";
	
	/** New message sprays onto screen from right to left. */
	public static final String SPECIAL_SPRAY = "SPRAY";
	
	/** Explode new message onto screen. */
	public static final String SPECIAL_STARBURST = "STAR";
	
	/** Special welcome message. */
	public static final String SPECIAL_WELCOME = "WELCOME";
	
	/** Special slot machine transition to new message. */
	public static final String SPECIAL_SLOT_MACHINE = "SLOT";
	
	/** Special news flash animation. */
	public static final String SPECIAL_NEWS_FLASH = "NEWS";
	
	/** Special thank you message. */
	public static final String SPECIAL_THANK_YOU = "THANK";
	
	/** Special no smoking message. */
	public static final String SPECIAL_NO_SMOKING = "SMOKE";
	
	/** Special don't drink and drive message. */
	public static final String SPECIAL_DONT_DRINK_AND_DRIVE = "DRINK";
	
	/** Special running animal animation. */
	public static final String SPECIAL_RUNNING_ANIMAL = "ANIMAL";
	
	/** Special fireworks animation. */
	public static final String SPECIAL_FIREWORKS = "FIREWORK";
	
	/** Special balloon animation. */
	public static final String SPECIAL_BALLOON = "BALLOON";
	
	/** Special cherry bomb animation. */
	public static final String SPECIAL_CHERRY_BOMB = "BOMB";
}
