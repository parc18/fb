package com.fb.daoimpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fb.dao.FbApiRespose;
import com.khelacademy.www.pojos.ApiFormatter;
import com.khelacademy.www.pojos.MyErrors;
import com.khelacademy.www.services.ServiceUtil;
@Service
public class ApiResponseImpl implements FbApiRespose {

	@Override
	public <T> ResponseEntity<T> successResponse(T E) {
		ApiFormatter<T> response= ServiceUtil.convertToSuccessResponse(E);
	    @SuppressWarnings("unchecked")
		ResponseEntity<T> ok = (ResponseEntity<T>) ResponseEntity.ok(response);
		return ok;
	}

	@Override
	public <T> ResponseEntity<T> failureResponse(Exception e, String isError, Integer code) {
		MyErrors error = new MyErrors(e.getMessage());
		ApiFormatter<MyErrors> err = ServiceUtil.convertToFailureResponse(error, isError, code);
		return (ResponseEntity<T>) ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(err);
	}

}
