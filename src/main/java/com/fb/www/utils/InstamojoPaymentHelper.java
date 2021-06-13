package com.fb.www.utils;

import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.fb.www.api.ApiEndpoint;
import com.fb.www.pojos.ApiFormatter;
import com.fb.www.pojos.Order;
import com.instamojo.wrapper.api.Instamojo;
import com.instamojo.wrapper.api.InstamojoImpl;
import com.instamojo.wrapper.exception.ConnectionException;
import com.instamojo.wrapper.exception.InvalidPaymentOrderException;
import com.instamojo.wrapper.model.PaymentOrder;
import com.instamojo.wrapper.response.CreatePaymentOrderResponse;
import com.instamojo.wrapper.response.PaymentOrderDetailsResponse;
@Configuration
public class InstamojoPaymentHelper {
	private static final Logger LOGGER = LoggerFactory.getLogger(InstamojoPaymentHelper.class);
	private PaymentOrder order;
	private Instamojo api = null;
	@Value("${instaMojoClientId}")
	private String instaMojoClientId;
	@Value("${instaMojoSecretKey}")
	private String instaMojoSecretKey;
	@Value("${instaMojoApiUrl}")
	private String instaMojoApiUrl;
	@Value("${instaMojoTokenUrl}")
	private String instaMojoTokenUrl;
	CreatePaymentOrderResponse createPaymentOrderResponse = null;
	
//	@Value("${instaMojoApiUrl}")
//	public void setInstaMojoApiUrl(String instaMojoApiUrl) {
//		this.instaMojoApiUrl = instaMojoApiUrl;
//	}
//	
//	@Value("${instaMojoTokenUrl}")
//	public void setInstaMojoTokenUrl(String instaMojoTokenUrl) {
//		this.instaMojoTokenUrl = instaMojoTokenUrl;
//	}
//	
//	@Value("${instaMojoClientId}")
//	public void setInstaMojoClientId(String instaMojoClientId) {
//		this.instaMojoClientId = instaMojoClientId;
//	}
//	
//	@Value("${instaMojoSecretKey}")
//	public void setInstaMojoSecretKey(String instaMojoSecretKey) {
//		this.instaMojoSecretKey = instaMojoSecretKey;
//	}

    public PaymentOrder getOrder() {
		return this.order;
	}
	public void setOrder(PaymentOrder order) {
		this.order = order;
	}
	
	public CreatePaymentOrderResponse initiatePayment(PaymentOrder order) {
	    try {
	        // gets the reference to the instamojo api
	    	 api = InstamojoClient.getInstamojoClient();
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    boolean isOrderValid = order.validate();
	    if (isOrderValid) {
	    	try {
				createPaymentOrderResponse = api.createNewPaymentOrder(order);
			} catch (Exception e) {

                if (order.isTransactionIdInvalid()) {
                	LOGGER.error("Transaction id is invalid. This is mostly due to duplicate  transaction id.");
                }
                if (order.isCurrencyInvalid()) {
                	LOGGER.error("Currency is invalid.");
                }
            }
	    }else{
	    	createPaymentOrderResponse = null;
	    	
	        if (order.isTransactionIdInvalid()) {
	            LOGGER.error("Transaction id is invalid.");
	        }
	        if (order.isAmountInvalid()) {
	            LOGGER.error("Amount can not be less than 9.00.");
	        }
	        if (order.isCurrencyInvalid()) {
	            LOGGER.error("Please provide the currency.");
	        }
	        if (order.isDescriptionInvalid()) {
	            LOGGER.error("Description can not be greater than 255 characters.");
	        }
	        if (order.isEmailInvalid()) {
	            LOGGER.error("Please provide valid Email Address.");
	        }
	        if (order.isNameInvalid()) {
	            LOGGER.error("Name can not be greater than 100 characters.");
	        }
	        if (order.isPhoneInvalid()) {
	            LOGGER.error("Phone is invalid.");
	        }
	        if (order.isRedirectUrlInvalid()) {
	            LOGGER.error("Please provide valid Redirect url.");
	        }
	        if (order.isWebhookInvalid()) {
	            LOGGER.error("Provide a valid webhook url");
	        }
	    }
	    return createPaymentOrderResponse;
	}
	//NOT IN USE
	public String checkStatus(String paymentId) {
		try {
			api = InstamojoClient.getInstamojoClient();

			PaymentOrderDetailsResponse paymentOrderDetailsResponse = api
					.getPaymentOrderDetailsByTransactionId("[TRANSACTION_ID]");
			return paymentOrderDetailsResponse.getStatus().toString();
		} catch (ConnectionException e) {
			LOGGER.error(e.toString(), e);
		}
		return "ERROR";
	}
}
