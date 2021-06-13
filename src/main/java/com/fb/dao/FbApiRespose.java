package com.fb.dao;

import org.springframework.http.ResponseEntity;

import com.fb.www.pojos.ApiFormatter;
import com.fb.www.services.ServiceUtil;

public interface FbApiRespose {
	public <T> ResponseEntity<T> successResponse(T E);
	public <T> ResponseEntity<T> failureResponse(Exception e, String isError, Integer code);

}
