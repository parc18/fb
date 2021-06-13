package com.fb.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fb.dao.FbApiRespose;
import com.fb.dao.ProductDao;
import com.fb.utils.UserUtils;
import com.khelacademy.dto.UserDto;

@RestController
@CrossOrigin
@RequestMapping(value = "/product")
public class ProductController {

	@Autowired
	ProductDao productDao;
	
	@Autowired
	FbApiRespose apiResponse;

	@GetMapping
	public ResponseEntity<?> getAllProduct(@RequestHeader("Authorization") String auth) throws Exception {
		if(UserUtils.validateBasicAuth(auth)) {
			try {
				return apiResponse.successResponse(productDao.getProducts());	
			}catch (Exception e) {
				return apiResponse.failureResponse(e, "true", 500);
			}
		} else {
			return apiResponse.failureResponse(new Exception("INVALID_CREDENTIALS", new BadCredentialsException(auth)), "true", 406);
		}
	}
}
