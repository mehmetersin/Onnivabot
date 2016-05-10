package com.mesoft.bootstrapper.controllers;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mesoft.bootstrapper.util.Util;

public class SerialComminucator {

	final static Logger logger = LoggerFactory
			.getLogger(SerialComminucator.class);

	void connect(String portName) throws Exception {

		logger.info("SerialComminucator starting with {} portname", portName);

		CommPortIdentifier portIdentifier = CommPortIdentifier
				.getPortIdentifier(portName);
		if (portIdentifier.isCurrentlyOwned()) {
			logger.error("Error: Port is currently in use");
		} else {
			int timeout = 2000;
			CommPort commPort = portIdentifier.open(this.getClass().getName(),
					timeout);

			if (commPort instanceof SerialPort) {
				SerialPort serialPort = (SerialPort) commPort;
				serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8,
						SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

				InputStream in = serialPort.getInputStream();
				OutputStream out = serialPort.getOutputStream();

				// (new Thread(new SerialReader(in))).start();

				serialPort.addEventListener(new SerialReader2(in));
				serialPort.notifyOnDataAvailable(true);

				(new Thread(new SerialWriter(out))).start();

			} else {
				logger.error("Error: Only serial ports are handled by this code.");
			}
		}
	}

	public static class SerialWriter implements Runnable {

		OutputStream out;

		public SerialWriter(OutputStream out) {
			this.out = out;
		}

		public void run() {
			try {

				while (true) {
					Util util = new Util();
					logger.debug("Navigate Robot Starting");
					int state = GpioAdapter.getStatus();


					if (state != GpioAdapter.STOP  && !GpioAdapter.isHaveBarrier()) {

						logger.debug(">>>>Navigate Robot:Direction" + state);

						// for (int i = 0; i < 3000; i++) {

						// byte[] aa = String.valueOf(""+Integer.valueOf(state))
						// .getBytes();
						byte[] aa = new String("\""+state+"\"").getBytes();
						out.write(aa, 0, aa.length);
						// }

					}{
						logger.debug(">>>>Navigate Robot:" + state);
					}
					logger.debug("Navigate Robot Waiting");
					Thread.sleep(Integer.valueOf(util.getConfig("updatePeriod")));

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void init(String portName) throws Exception {
		try {
			(new SerialComminucator()).connect(portName);
		} catch (Exception e) {
			logger.error("error in init:portname: " + portName, e);
			throw e;
		}
	}

	public static void main(String[] args) {

		SerialComminucator ser = new SerialComminucator();


		try {
			ser.init("/dev/ttyACM0");
			// ser.init("/dev/ttyUSB0");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void listSerialPortName() {
		List<String> vp = new ArrayList<String>();
		Enumeration portList = CommPortIdentifier.getPortIdentifiers();
		while (portList.hasMoreElements()) {
			CommPortIdentifier portId = (CommPortIdentifier) portList
					.nextElement();
			logger.debug("Port Name:" + portId.getName());
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				logger.debug("Port Name is :" + portId.getName());

			}
		}
	}

	public static String getSerialPortName() {
		List<String> vp = new ArrayList<String>();
		Enumeration portList = CommPortIdentifier.getPortIdentifiers();
		while (portList.hasMoreElements()) {
			CommPortIdentifier portId = (CommPortIdentifier) portList
					.nextElement();
			logger.debug("Port Name:" + portId.getName());
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				logger.debug("Port Name is :" + portId.getName());
				return portId.getName();

			}
		}
		return null;
	}
}