package com.mesoft.bootstrapper.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;

/**
 * This example code demonstrates how to perform simple state control of a GPIO
 * pin on the Raspberry Pi.
 * 
 * @author Robert Savage
 */
public class GpioAdapter extends Thread {

	private static GpioController gpio = null;

	private static GpioPinDigitalOutput pin = null;

	static int STOP = 0;
	static int UP = 1;
	static int RIGHT = 2;
	static int DOWN = 3;
	static int LEFT = 4;

	private static int status = 0;
	
	private static boolean haveBarrier = false;
	
	
	

	public static boolean isHaveBarrier() {
		return haveBarrier;
	}

	public static void setHaveBarrier(boolean haveBarrier) {
		GpioAdapter.haveBarrier = haveBarrier;
	}

	public static int getStatus() {

		if (status != 0) {
			int tempStatus = status;
			status = 0;
			return tempStatus;
		}

		return status;
	}

	public static void setStatus(int status) {
		logger.debug("status change new value"+status);
		GpioAdapter.status = status;
	}

	private static Integer pinNumber;
	private static Integer sleepTime;

	public static Integer getPinNumber() {
		return pinNumber;
	}

	public static void setPinNumber(Integer pinNumber) {
		GpioAdapter.pinNumber = pinNumber;
	}

	public static Integer getSleepTime() {
		return sleepTime;
	}

	public static void setSleepTime(Integer sleepTime) {
		GpioAdapter.sleepTime = sleepTime;
	}

	static Logger logger = LoggerFactory
			.getLogger(GpioAdapter.class);

	public static void guidanceRobot(String code) {
		// TODO

		status = Integer.valueOf(code);
	}

	public static void openGate() {

		try {

			logger.debug("<--Pi4J--> GPIO Control .. started.Sleep Time:"
					+ sleepTime + " Pin:" + pinNumber);
			// create gpio controller
			if (gpio == null) {
				gpio = GpioFactory.getInstance();
			}
			// provision gpio pin #01 as an output pin and turn on
			if (pin == null) {

				Pin raspin = RaspiPin.GPIO_00;

				switch (pinNumber) {
				case 1:
					raspin = RaspiPin.GPIO_01;
					logger.debug("--> Pin set 1");
					break;
				case 2:
					raspin = RaspiPin.GPIO_02;
					break;
				case 3:
					raspin = RaspiPin.GPIO_03;
					break;
				case 4:
					raspin = RaspiPin.GPIO_04;
					break;
				case 5:
					raspin = RaspiPin.GPIO_05;
					break;
				case 6:
					raspin = RaspiPin.GPIO_06;
					break;
				case 7:
					raspin = RaspiPin.GPIO_07;
					break;
				case 8:
					raspin = RaspiPin.GPIO_08;
					break;
				case 9:
					raspin = RaspiPin.GPIO_09;
					break;
				case 10:
					raspin = RaspiPin.GPIO_10;
					break;
				case 11:
					raspin = RaspiPin.GPIO_11;
					break;
				case 12:
					raspin = RaspiPin.GPIO_12;
					break;
				case 13:
					raspin = RaspiPin.GPIO_13;
					break;
				case 14:
					raspin = RaspiPin.GPIO_14;
					break;
				case 15:
					raspin = RaspiPin.GPIO_15;
					break;
				case 16:
					raspin = RaspiPin.GPIO_16;
					break;
				case 17:
					raspin = RaspiPin.GPIO_17;
					break;
				case 18:
					raspin = RaspiPin.GPIO_18;
					break;
				case 19:
					raspin = RaspiPin.GPIO_19;
					break;
				case 20:
					raspin = RaspiPin.GPIO_20;
					break;
				case 21:
					raspin = RaspiPin.GPIO_21;
					break;
				case 22:
					raspin = RaspiPin.GPIO_22;
					break;
				case 23:
					raspin = RaspiPin.GPIO_23;
					break;
				case 24:
					raspin = RaspiPin.GPIO_24;
					break;
				case 25:
					raspin = RaspiPin.GPIO_25;
					break;
				case 26:
					raspin = RaspiPin.GPIO_26;
					break;
				case 27:
					raspin = RaspiPin.GPIO_27;
					break;
				case 28:
					raspin = RaspiPin.GPIO_28;
					break;
				case 29:
					raspin = RaspiPin.GPIO_29;
					break;

				default:
					break;
				}

				logger.debug("Pin Number" + raspin.getAddress());

				pin = gpio.provisionDigitalOutputPin(raspin, "GatePin");
				logger.debug("Gate Ping Number" + pin.getName());

			}
			pin.high();
			logger.debug("--> GPIO state should be: HIGH");
			Thread.sleep(sleepTime);
			// turn off gpio pin #01

			pin.low();
			logger.debug("--> GPIO state should be: LOW");

		} catch (Exception e) {
			logger.error("Hata oluştu");
			e.printStackTrace();

			gpio.shutdown();
			pin = null;
			gpio = null;

		}

	}

	@Override
	public void run() {
		GpioAdapter.openGate();
	}

}