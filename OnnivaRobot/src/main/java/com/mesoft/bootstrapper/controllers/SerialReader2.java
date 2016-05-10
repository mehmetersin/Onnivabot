package com.mesoft.bootstrapper.controllers;

import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SerialReader2 implements SerialPortEventListener 
{
	
	final static Logger logger = LoggerFactory
			.getLogger(SerialReader2.class);
	
    private InputStream in;
    private byte[] buffer = new byte[1024];
    String buffer_string;
    public SerialReader2( InputStream in )
    {
        this.in = in;
    }

    public void serialEvent(SerialPortEvent arg0) {
        int data;

        try
        {
            int len = 0;
            while ( ( data = in.read()) > -1 )
            {
                if ( data == '\n' ) {
                    break;
                }
                buffer[len++] = (byte) data;
            }
            //System.out.print(new String(buffer,0,len));
            buffer_string = new String(buffer,0,len);
            
            buffer_string = buffer_string.replaceAll("\"", "");
            buffer_string = buffer_string.replaceAll("\n", "");
            buffer_string = buffer_string.replaceAll("\r", "");
            
            Integer i = Integer.valueOf(buffer_string);
            if (i<30){
            	logger.debug("DUR:"+buffer_string);
            	GpioAdapter.setHaveBarrier(true);
            }else{
            	GpioAdapter.setHaveBarrier(false);
            }
            
           // logger.debug("Data from sonar:"+buffer_string);
            
        }
        catch ( Exception e )
        {
           logger.error("Error in serial read",e);
            
        }             
    }

}