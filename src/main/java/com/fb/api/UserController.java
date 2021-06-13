package com.fb.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fb.dao.UserDao;
import com.fb.dto.UserDto;
import com.fb.utils.UserUtils;

@RestController
@CrossOrigin
public class UserController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserDao userDao;

	@RequestMapping(value = "user/signup", method = RequestMethod.POST)
	public ResponseEntity<?> userSignUp(@RequestBody UserDto userRequest, @RequestHeader("Authorization") String auth) throws Exception {
		if(UserUtils.validateBasicAuth(auth)) {
			return userDao.firstTimeRegistration(userRequest);
		} else {
			throw new Exception("INVALID_CREDENTIALS", new BadCredentialsException(auth));
		}
	}
	@RequestMapping(value = "user/login", method = RequestMethod.POST)
	public ResponseEntity<?> userLogin(@RequestBody UserDto userRequest, @RequestHeader("Authorization") String auth) throws Exception {
		if(UserUtils.validateBasicAuth(auth)) {
			return userDao.userLogin(userRequest);
		}else {
			throw new Exception("INVALID_CREDENTIALS", new BadCredentialsException(auth));
		}
	}
	@RequestMapping(value = "user/reset-password", method = RequestMethod.POST)
	public ResponseEntity<?> userPasswordReset(@RequestBody UserDto userRequest, @RequestHeader("Authorization") String auth) throws Exception {
		if(UserUtils.validateBasicAuth(auth)) {
			return userDao.userLogin(userRequest);
		}else {
			throw new Exception("INVALID_CREDENTIALS", new BadCredentialsException(auth));
		}
	}
	@RequestMapping(value = "user/verify-eotp", method = RequestMethod.POST)
	public ResponseEntity<?> userVerificationViaEmailOtp(@RequestBody UserDto userRequest, @RequestHeader("Authorization") String auth) throws Exception {
		if(UserUtils.validateBasicAuth(auth)) {
			return userDao.userVerifyOtp(userRequest);
		}else {
			throw new Exception("INVALID_CREDENTIALS", new BadCredentialsException(auth));
		}
	}
	@RequestMapping(value = "user/send-eotp", method = RequestMethod.POST)
	public ResponseEntity<?> sendEotp(@RequestBody UserDto userRequest, @RequestHeader("Authorization") String auth) throws Exception {
		if(UserUtils.validateBasicAuth(auth)) {
			return userDao.userLogin(userRequest);
		}else {
			throw new Exception("INVALID_CREDENTIALS", new BadCredentialsException(auth));
		}
	}
	@RequestMapping(value = "user/update-email", method = RequestMethod.PUT)
	public ResponseEntity<?> updateEmail(@RequestBody UserDto userRequest, @RequestHeader("Authorization") String auth) throws Exception {
		SecurityContext context = SecurityContextHolder.getContext();
		String userName = context.getAuthentication().getName();
		userRequest.setUserName(userName);
		return userDao.updateEmail(userRequest);
	}
	
	@RequestMapping(value = "user/update-email-verification", method = RequestMethod.PUT)
	public ResponseEntity<?> updateEmailVerification(@RequestBody UserDto userRequest, @RequestHeader("Authorization") String auth) throws Exception {
		SecurityContext context = SecurityContextHolder.getContext();
		String userName = context.getAuthentication().getName();
		userRequest.setUserName(userName);
		return userDao.userVerifyEmailOtpAfterUpdate(userRequest);
	}
}
