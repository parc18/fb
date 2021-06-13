package com.fb.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fb.dao.FbApiRespose;
import com.fb.dao.Order;
import com.fb.dto.CartDto;

@RestController
@CrossOrigin
@RequestMapping(value = "/order")
public class OrderController {
	@Autowired
	Order orderDao;

	@Autowired
	FbApiRespose apiResponse;

	@RequestMapping(value = "/addcart", method = RequestMethod.POST)
	public ResponseEntity<?> userSignUp(@RequestBody CartDto cartDto) throws Exception {
		SecurityContext context = SecurityContextHolder.getContext();
		String userName = context.getAuthentication().getName();
		cartDto.setUserName(userName);
		try {
			return apiResponse.successResponse(orderDao.addToCard(cartDto));
		} catch (Exception e) {
			return apiResponse.failureResponse(e, "true", 500);
		}
	}
}
