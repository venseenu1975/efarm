package com.farm.util;


import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class FarmUtil {
	
	public static final String ACCOUNT_SID = "";
    public static final String AUTH_TOKEN = "";
    public static final String TWILIO_NUMBER = "";
    
    
    public void sendSMS(String messageContent,String toNumber) {
	    try {
	        TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
	 
	        // Build a filter for the MessageList
	        List<NameValuePair> params = new ArrayList<NameValuePair>();
	        params.add(new BasicNameValuePair("Body",messageContent));
	        params.add(new BasicNameValuePair("To", toNumber)); //Add real number here
	        params.add(new BasicNameValuePair("From", TWILIO_NUMBER));
	 
	        MessageFactory messageFactory = client.getAccount().getMessageFactory();
	        Message message = messageFactory.create(params);
	        System.out.println(message.getSid());
	    } 
	    catch (TwilioRestException e) {
	        System.out.println(e.getErrorMessage());
	    }
	}
}
