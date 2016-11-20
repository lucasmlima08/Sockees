package com.myllenno.sockees.report;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class HandlerDialog {
	
    public final String CONNECTION_OPENED = "CONNECTION OPENED";
    public final String CONNECTION_CLOSED = "CONNECTION CLOSED";
    public final String SERVER_UNAVAILABLE = "SERVER UNAVAILABLE";
    public final String USER_AUTHENTICATED = "USER AUTHENTICATED";
    public final String USER_NOT_AUTHENTICATED = "USER NOT AUTHENTICATED";
    public final String TIMEOUT_FOR_AUTHENTICATION = "TIMEOUT FOR AUTHENTICATION";
    public final String USER_UNAVAILABLE = "USER UNAVAILABLE";
    public final String USER_RECEIVED = "USER RECEIVED";
    public final String USER_REFUSED = "USER REFUSED";
    public final String USER_REMOVED = "USER REMOVED";
    public final String DATA_SENT = "DATA SENT";
    public final String DATA_NOT_SEND = "DATA NOT SEND";
    public final String DATA_RECEIVED = "DATA RECEIVED";
    public final String DATA_EMPTY = "DATA EMPTY";
    public final String REQUESTS_RECEIVED_ALL = "REQUESTS RECEIVED ALL";
    public final String REQUESTS_SENT_ALL = "REQUESTS SENT ALL";
    public final String REQUESTS_UNKNOWN = "REQUESTS UNKNOWN";
    public final String EMPTY = "EMPTY";
    
    private Handler handler;
    
    public HandlerDialog(Handler handler){
    	this.handler = handler;
    }
    
    public void publishInfo(String info){
    	if (handler != null){
    		handler.publish(new LogRecord(Level.INFO, info));
    	}
    }
    
    public void publishSevere(String severe){
    	if (handler != null){
    		handler.publish(new LogRecord(Level.SEVERE, severe));
    	}
    }
}
