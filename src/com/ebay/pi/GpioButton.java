package com.ebay.pi;

import java.util.concurrent.Callable;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.trigger.GpioCallbackTrigger;

/**
 * Borrowing from: http://pi4j.com/example/trigger.html
 */
public class GpioButton {

	private GpioPinDigitalInput myButton;

	private boolean buttonPressed = false;

	public GpioButton() {

		String osName = System.getProperty("os.name");

		if (!osName.startsWith("Mac")) {
			// create gpio controller
			GpioController gpio = GpioFactory.getInstance();

			// provision gpio pin #02 as an input pin with its internal pull down resistor enabled
			myButton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_DOWN);

			// create a gpio callback trigger on gpio pin#4; when #4 changes state, perform a callback
			// invocation on the user defined 'Callable' class instance
			myButton.addTrigger(
					new GpioCallbackTrigger(
							new Callable<Void>() {
								@Override
								public Void call() throws Exception {
									System.out.println(" --> GPIO TRIGGER CALLBACK RECEIVED ");
									buttonPressed = true;
									return null;
								}
							}
							)
					);
		}
	}

	/**
	 * Check if the button was pressed. If it was, the state is reset to false until the next time the button is pressed.
	 * @return True if the button has been pressed, false otherwise.
	 */
	public boolean wasButtonPressed() {

		if (buttonPressed) {
			buttonPressed = false;
			return true;
		}

		return false;
	}
}
