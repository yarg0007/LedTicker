package com.ebay.ticker.util;

/**
 * Constants related to the Alpha Communications Protocol designed for 
 * Alpha and BetaBrite signs.
 * 
 * Protocol reference:
 * http://www.alpha-american.com/p-alpha-communications-protocol.html
 * 
 * Perl examples to easily examine communications with the sign:
 * http://dens-site.net/betabrite/betabrite.htm
 * 
 * @author Benjamin Yarger <byarger@ebay.com>
 */
public class AlphaProtocolConstants {
	
	// -------------------------------------------------------------------------
	// Initialization and closing message commands. Used to setup and close any
	// message sent to the led ticker.
	// -------------------------------------------------------------------------

	/** Wake up sign and sync to establish baud rate. */
	public static final String NUL_INIT = "\0\0\0\0\0";
	
	/** Start-of-header signature. */
	public static final String START_OF_HEADER = "\001";
	
	/** Sign type code (z) specifies all signs. */
	public static final String SIGN_TYPE = "Z";
	
	/** Address of sign (00 = broadcast). */
	public static final String SIGN_ADDRESS = "00";
	
	/** Start of text message signature. */
	public static final String START_OF_TEXT = "\002";
	
	/** Command code to write text file to the sign. */
	public static final String COMMAND_CODE = "AA";
	
	/** End of transmission signature. */
	public static final String END_OF_TRANSMISSION = "\004";
	
	/** Convenience initializer string containing complete init sequence. */
	public static final String INIT = 
			NUL_INIT+START_OF_HEADER+SIGN_TYPE+SIGN_ADDRESS+START_OF_TEXT+COMMAND_CODE;
	
	/** Prefix for all display command codes.*/
	public static final String DISPLAY_COMMAND_SIGNATURE = (char)0x1B + " ";
	
	// -------------------------------------------------------------------------
	// Display Code Values
	// These encode message display animations.
	// -------------------------------------------------------------------------
	
	/** Message travels right to left. */
	public static final String RIGHT_TO_LEFT = "a";
	
	/** Text is stationary. */
	public static final String HOLD = "b";
	
	/** Message remains stationary and flashes. */
	public static final String FLASH = "c";
	
	/** Previous message is pushed up by new message. */
	public static final String ROLL_UP = "e";
	
	/** Previous message is pushed down by new message. */
	public static final String ROLL_DOWN = "f";
	
	/** Previous message is pushed left by new message. */
	public static final String ROLL_LEFT = "g";
	
	/** Previous message is pushed right by new message. */
	public static final String ROLL_RIGHT = "h";
	
	/** New message is wiped over the old message from bottom up. */
	public static final String WIPE_UP = "i";
	
	/** New message is wiped over the old message from top down. */
	public static final String WIPE_DOWN = "j";
	
	/** New message is wiped over the old message from right to left. */
	public static final String WIPE_LEFT = "k";
	
	/** New message is wiped over the old message from left to right. */
	public static final String WIPE_RIGHT = "l";
	
	/** Various modes are called upon to display the message. */
	public static final String AUTO = "o";
	
	/** Previous message is pushed towards the center by new message. */
	public static final String ROLL_IN = "p";
	
	/** Previous message is pushed outwards from the center by new message. */
	public static final String ROLL_OUT = "q";
	
	/** New message is wiped over the previous message in an inward direction.*/
	public static final String WIPE_IN = "r";
	
	/** New message is wiped over the previous message in an outward direction.*/
	public static final String WIPE_OUT = "s";
	
	/** Message will twinkle on the sign. */
	public static final String SPECIAL_TWINKLE = "n0";
	
	/** Message will sparkle over the current message. */
	public static final String SPECIAL_SPARKLE = "n1";
	
	/** Message will snow onto the display. */
	public static final String SPECIAL_SNOW = "n2";
	
	/** Interlock over current message along alternating rows. */
	public static final String SPECIAL_INTERLOCK = "n3";
	
	/** Half of sign swipes up, the other half swipes down. */
	public static final String SPECIAL_SWITCH = "n4";
	
	/** Vertical wave action behind message. */
	public static final String SPECIAL_WAVE = "n5";
	
	/** New message sprays onto screen from right to left. */
	public static final String SPECIAL_SPRAY = "n6";
	
	/** Explode new message onto screen. */
	public static final String SPECIAL_STARBURST = "n7";
	
	/** Special welcome message. */
	public static final String SPECIAL_WELCOME = "n8";
	
	/** Special slot machine transition to new message. */
	public static final String SPECIAL_SLOT_MACHINE = "n9";
	
	/** Special news flash animation. */
	public static final String SPECIAL_NEWS_FLASH = "nA";
	
	/** Special thank you message. */
	public static final String SPECIAL_THANK_YOU = "nS";
	
	/** Special no smoking message. */
	public static final String SPECIAL_NO_SMOKING = "nU";
	
	/** Special don't drink and drive message. */
	public static final String SPECIAL_DONT_DRINK_AND_DRIVE = "nV";
	
	/** Special running animal animation. */
	public static final String SPECIAL_RUNNING_ANIMAL = "nW";
	
	/** Special fireworks animation. */
	public static final String SPECIAL_FIREWORKS = "nX";
	
	/** Special balloon animation. */
	public static final String SPECIAL_BALLOON = "nY";
	
	/** Special cherry bomb animation. */
	public static final String SPECIAL_CHERRY_BOMB = "nZ";
}
