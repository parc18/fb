package com.fb.dao;

import java.sql.SQLException;

import javax.ws.rs.core.Response;

import org.springframework.http.ResponseEntity;

import com.fb.model.BasicUserDetails;
import com.khelacademy.dto.UserDto;
import com.khelacademy.www.pojos.BookingRequestObject;
import com.khelacademy.www.pojos.User;

public interface UserDao {
	Response getUserById(Integer userId) throws SQLException;

	Response getUserByEmailId(Integer emailId);

	String registerUser(User userDetails) throws SQLException;

	boolean updateStatus(String phone, Integer status);

	BasicUserDetails getJwt(String username, String password);
	
	public ResponseEntity<?> firstTimeRegistration(UserDto userReq) throws Exception;
	
	public ResponseEntity<?> userLogin(UserDto userReq) throws Exception;
	
	public ResponseEntity<?> userResetPassword(UserDto userReq) throws Exception;
	
	public ResponseEntity<?> userVerifyOtp(UserDto userReq) throws Exception;
	
	public ResponseEntity<?> sendEotp(UserDto userReq) throws Exception;
	
	public ResponseEntity<?> updateEmail(UserDto userReq) throws Exception;

	public String getUserNameByPhone(String phone);

	public String getUserNameByEmail(String email);

	ResponseEntity<?> userVerifyEmailOtpAfterUpdate(UserDto userRequest);
	
	public Long getUserIdByUserName(String userName);	
	
	public BasicUserDetails getUserByUserName(String userName);
	
	public User getAdvanceUserByUserName(String userName);
	
	public BasicUserDetails getUserByPhone(String phone);
	
}
