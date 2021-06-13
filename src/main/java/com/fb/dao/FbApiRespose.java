package com.fb.dao;

import org.springframework.http.ResponseEntity;

import com.khelacademy.www.pojos.ApiFormatter;
import com.khelacademy.www.services.ServiceUtil;

public interface FbApiRespose {
	public <T> ResponseEntity<T> successResponse(T E);
	public <T> ResponseEntity<T> failureResponse(Exception e, String isError, Integer code);

}
