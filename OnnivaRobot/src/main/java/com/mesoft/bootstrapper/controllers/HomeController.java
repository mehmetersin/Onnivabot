package com.mesoft.bootstrapper.controllers;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created with IntelliJ IDEA. User: will Date: 11/23/13 Time: 12:37 PM
 */
@Controller
@RequestMapping("/")
public class HomeController {


	@Value("${gateNo}")
	private String gateNo;
	@Value("${gpio.pin}")
	private String pin;
	@Value("${gpip.sleep}")
	private String sleepTime;

	static int counter = 0;

	static boolean init = false;

	static Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home() {
		return "/WEB-INF/views/angular-index.jsp";
	}


	
	
	@RequestMapping(value = "/access/{code}", method = RequestMethod.GET)
	public @ResponseBody String access(@PathVariable String code, ModelMap model) {

		if (!init) {

			init = true;
			SerialComminucator ser = new SerialComminucator();

			
			try {
				ser.init("/dev/ttyACM0");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				init = false;
			}

		}

		logger.debug("Method code: " + code);

		try {

			JSONObject obj = new JSONObject();
			obj.put("type", "image");

			counter++;

			obj.put("url", "http://lorempixel.com/1360/76" + counter);

			if (counter == 9) {
				counter = 1;
			}

			return obj.toJSONString();

		} catch (Exception e) {
			logger.error("Error while processing", e);
		} finally {
			// Release the connection.
			// method.releaseConnection();
		}
		return "NOK";

	}

	@RequestMapping(value = "/navigate/{code}", method = RequestMethod.GET)
	public @ResponseBody String navigate(@PathVariable String code, ModelMap model) {

		logger.debug("Method url: " +  code);

		
		GpioAdapter.setStatus(Integer.valueOf(code));
		

		try {

			return "OK";

		} catch (Exception e) {
			logger.error("Error while processing", e);
		} finally {
			// Release the connection.
			// method.releaseConnection();
		}
		return "NOK";

	}

}
