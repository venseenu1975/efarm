package com.farm.util;

import java.io.IOException;


import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;

@Component
public class FarmUtil {

	private static Logger log = Logger.getLogger(FarmUtil.class);
	
	
	@Value("${ACCOUNT.SID}")
	private  String accountSID;
	
	@Value("${AUTH.TOKEN}")
	private  String authToken;
	
	@Value("${TWILIO.NUMBER}")
	private  String twilioNum;

	public  void sendSMS(String messageContent, String toNumber) throws IOException {
		try {
			TwilioRestClient client = new TwilioRestClient(accountSID,authToken);
			// Build a filter for the MessageList
			List<NameValuePair> params = new ArrayList<>();
			params.add(new BasicNameValuePair("Body", messageContent));
			params.add(new BasicNameValuePair("To", toNumber)); // Add real
																// number here
			params.add(new BasicNameValuePair("From",twilioNum));
			MessageFactory messageFactory = client.getAccount().getMessageFactory();
			Message message = messageFactory.create(params);
			log.info(message.getSid());
		} catch (TwilioRestException e) {
			log.error(e);
		}
	}

	/**
	 * Calculate distance between two points in latitude and longitude taking
	 * into account height difference. If you are not interested in height
	 * difference pass 0.0. Uses Haversine method as its base.
	 * 
	 * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
	 * el2 End altitude in meters
	 * 
	 * @returns Distance in Meters
	 */
	public static double distance(double lat1, double lat2, double lon1, double lon2) {

		final int R = 6371; // Radius of the earth
		double latDistance = Math.toRadians(lat2 - lat1);
		double lonDistance = Math.toRadians(lon2 - lon1);
		double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double distance = R * c * 1000; // convert to meters
		double height = 0;
		distance = Math.pow(distance, 2) + Math.pow(height, 2);
		return Math.sqrt(distance);
	}

	public static BigDecimal calculateCost(int itemQuantity, BigDecimal itemPrice) {
		BigDecimal itemCost = BigDecimal.ZERO;
		BigDecimal totalCost = BigDecimal.ZERO;
		itemCost = itemPrice.multiply(new BigDecimal(itemQuantity));
		totalCost = totalCost.add(itemCost);
		return totalCost;
	}

	public static Properties getProperties() throws IOException {
		log.info("Entering inside Utils.getConnectionProperties()");
		Properties connectionProp = new Properties();
		String propFileName = "efarm.properties";
		InputStream input = FarmUtil.class.getClassLoader().getResourceAsStream(propFileName);

		if (input == null) {
			log.info("Could not find/read file: " + propFileName);

		} else {
			// load a properties file
			try {
				connectionProp.load(input);
			} catch (IOException e) {
				log.error("Could not load properties file: " + propFileName);
				throw new IOException(e.getMessage(), e);
			}
		}
		return connectionProp;
	}

	 @Bean
	    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
	        return new PropertySourcesPlaceholderConfigurer();
	    }
}
