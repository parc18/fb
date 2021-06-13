package com.fb.www.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
@Component
@Configuration
public class Constants {
    public static final int SUCCESS_RESPONSE_CODE = 200;
    public static final int BAD_REQUEST_CODE = 400;
    public static final String SUCCESS_RESPONSE_VALUE = "SUCCESS!!";
    public static final String FAILURE_RESPONSE_VALUE = "FAILURE!!";
    //@Value("${instawebhook2}")
    public static String REDIRECT_URL="http://localhost:8080/thankyou/";
    public static String WEBHOOK_URL;
    @Value("${instaWebHook}")
	private void setDbUser(String WEBHOOK_URL) {
    	Constants.WEBHOOK_URL=WEBHOOK_URL;
	}
    
}
