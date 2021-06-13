package com.fb.daoimpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.fb.config.JwtTokenUtil;
import com.fb.dao.UserDao;
import com.fb.dto.UserDto;
import com.fb.model.AdvancedUserDetail;
import com.fb.model.BasicUserDetails;
import com.fb.model.UserUpdate;
import com.fb.service.JwtUserDetailsService;
import com.fb.utils.UserUtils;
import com.fb.www.pojos.ApiFormatter;
import com.fb.www.pojos.MyErrors;
import com.fb.www.pojos.User;
import com.fb.www.services.PresenceStatus;
import com.fb.www.services.ServiceUtil;
import com.fb.www.utils.DBArrow;
import com.fb.www.utils.SMSService;
import com.fb.utils.EmailService;
import com.fb.utils.UserConstants;

@Component
@Transactional
public class UserDaoImpl implements UserDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);
	DBArrow SQLArrow = DBArrow.getArrow();
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private JwtUserDetailsService userDetailsService;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	// deprecated mthod
	@Override
	public Response getUserById(Integer userId) throws SQLException {
		
		/// deprecated method	
		LOGGER.info("GET USER IS CALLED FOR ID: " + userId);
		// ApiFormatter<List<User>> userResponse=null;
		PreparedStatement statement;
		List<User> allUser = new ArrayList<User>();
		if (userId == -1) {
			statement = SQLArrow.getPreparedStatement("SELECT * FROM user");
		} else {
			statement = SQLArrow.getPreparedStatement("SELECT * FROM user where id= ?");
			statement.setInt(1, userId);
		}
		try (ResultSet rs = SQLArrow.fire(statement)) {
			User user = new User();
			while (rs.next()) {
				user.setUserId(rs.getInt("id"));
				user.setFirstName(rs.getString("firstname"));
				user.setLastName(rs.getString("lastname"));
				user.setEmail(rs.getString("email"));
				user.setCity(rs.getString("city"));
				user.setStatus(rs.getString("status"));
				user.setContactNumber(rs.getString("phone"));
				allUser.add(user);
			}
		} catch (Exception e) {
			LOGGER.error("ERROR IN GETTING USER DETAILE FOR ID: " + userId);
			MyErrors error = new MyErrors(e.getMessage());
			ApiFormatter<MyErrors> err = ServiceUtil.convertToFailureResponse(error, "true", 500);
			return Response.ok(new GenericEntity<ApiFormatter<MyErrors>>(err) {
			}).build();
		}
		if (allUser.size() == 1) {
			ApiFormatter<User> userResponse = ServiceUtil.convertToSuccessResponse(allUser.get(0));
			return Response.ok(new GenericEntity<ApiFormatter<User>>(userResponse) {
			}).build();
		} else

		{
			LOGGER.debug("TOTAL USER RETRIEVED: " + allUser.size());
			ApiFormatter<List<User>> userResponse = ServiceUtil.convertToSuccessResponse(allUser);
			return Response.ok(new GenericEntity<ApiFormatter<List<User>>>(userResponse) {
			}).build();
		}
	}
	// deprecated mthod
	@Override
	public Response getUserByEmailId(Integer emailId) {
		return null;
	}

	@Override
	public boolean updateStatus(String phone, Integer status) {
		PreparedStatement statement = SQLArrow.getPreparedStatement("UPDATE user SET status=? where phone=?");
		try {
			statement.setInt(1, status);
			statement.setString(2, phone);
			if (SQLArrow.fireBowfishing(statement) == 1) {
				return true;
			}
		} catch (SQLException e) {
			LOGGER.debug("ERROR AFTER VERIFYING STATUS FOR PHONE : " + phone + "WITH ERROR " + e.getMessage());
			return false;
		}

		return true;
	}
	// deprecated mthod
	@Override
	public String registerUser(User userDetails) {
		PreparedStatement statement = SQLArrow.getPreparedStatement("SELECT  * from user  WHERE phone =?");
		try {
			statement.setString(1, userDetails.getContactNumber());
			ResultSet rs = SQLArrow.fire(statement);
			if (rs.next()) {
				return PresenceStatus.EXISTS.toString();
			}

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		statement = SQLArrow.getPreparedStatement(
				"INSERT INTO user  (firstname, lastname, email, passcode,phone,city,status,welcomedate ) values (?, ?, ?, ?, ?, ?, ?,NOW())");
		try {
			statement.setString(1, userDetails.getFirstName());
			statement.setString(2, userDetails.getLastName());
			statement.setString(3, userDetails.getEmail());
			if (userDetails.getPasscode() == null)
				statement.setString(4, UserUtils.getSaltString(5));
			else
				statement.setString(4, userDetails.getPasscode());
			statement.setString(5, userDetails.getContactNumber());
			statement.setString(6, userDetails.getCity());
			statement.setString(7, userDetails.getStatus());
			if (SQLArrow.fireBowfishing(statement) == 1) {
//            	SMSService msg = new SMSService();
//            	String a= msg.sendSMS(userDetails.getContactNumber());
//            	return a;
				SQLArrow.relax(null);
				return PresenceStatus.REGISTRED_SUCCESSFULLY_BUT_NOT_OTP_VERIFIED.toString();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return PresenceStatus.COUDNT_REGISTER.toString();
	}

	@Override
	public BasicUserDetails getJwt(String username, String password) {
		Session session = this.sessionFactory.getCurrentSession();
		String hql = "FROM BasicUserDetails E WHERE E.userName =:uname";
		@SuppressWarnings("unchecked")
		Query<BasicUserDetails> query = session.createQuery(hql);
		query.setString("uname", username);
		List<BasicUserDetails> results = query.list();
		return results.get(0);
		// return null;
	}

	@Override
	public ResponseEntity<?> firstTimeRegistration(UserDto userReq) throws Exception {
		if (!StringUtils.isEmpty((userReq.getEmail()))) {
			return signUpUsingEmail(userReq);
		} else if (!StringUtils.isEmpty((userReq.getPhone()))) {
			return signUpUsingPhone(userReq);
		}
		MyErrors error = new MyErrors("User already registered, Please login");
		ApiFormatter<MyErrors> err = ServiceUtil.convertToFailureResponse(error, "true", 406);
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(err);
	}

	private ResponseEntity<?> signUpUsingPhone(UserDto userReq) {
		if (SMSService.verifyOTPV2(userReq.getSessionDetail(), userReq.getOtp(), userReq.getPhone())) {
			Session session = this.sessionFactory.getCurrentSession();
			String hql = "FROM BasicUserDetails E WHERE E.phone =:phone";
			@SuppressWarnings("unchecked")
			Query<BasicUserDetails> query = session.createQuery(hql);
			query.setString("phone", userReq.getPhone());
			List<BasicUserDetails> results = query.list();
			String token = null;
			if (results != null && results.size() == 1) {
				token = jwtTokenUtil.generateToken(results.get(0), results.get(0).getUserType());
			} else {
				BasicUserDetails user = new BasicUserDetails(userReq.getPhone(), UserConstants.USER_OTP_VERIFIED);
				user.setPhoneVerified(1);
				session.save(user);
				token = jwtTokenUtil.generateToken(user, user.getUserType());
			}
			ApiFormatter<String> success = ServiceUtil.convertToSuccessResponse(token);
			return ResponseEntity.status(HttpStatus.OK).body(success);
		} else {
			MyErrors error = new MyErrors("OTP mismatch");
			ApiFormatter<MyErrors> err = ServiceUtil.convertToFailureResponse(error, "true", 406);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(err);
		}
	}

	@SuppressWarnings("deprecation")
	private ResponseEntity<?> signUpUsingEmail(UserDto userReq) throws Exception {
		if (userReq.getPassWord().equals(userReq.getPassWordVerify()) && UserUtils.isValid(userReq.getEmail())) {
			Session session = this.sessionFactory.getCurrentSession();
			String passWord = UserUtils.getEncryptedPass(userReq.getPassWord());
			String hql = "FROM BasicUserDetails E WHERE E.email =:email";
			@SuppressWarnings("unchecked")
			Query<BasicUserDetails> query = session.createQuery(hql);
			query.setString("email", userReq.getEmail());
			List<BasicUserDetails> results = query.list();
			if (results != null && results.size() > 0) {
				MyErrors error = new MyErrors("User already registered, Please login");
				ApiFormatter<MyErrors> err = ServiceUtil.convertToFailureResponse(error, "true", 406);
				return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(err);
			}
			BasicUserDetails basicuserDetails = new BasicUserDetails(userReq.getEmail(), passWord,
					UserConstants.USER_SIGNUP_CAPTURED);
			try {
				Integer otp = EmailService.sendEmailOTP("null", userReq.getEmail());
				basicuserDetails.setOtp(111111);
				// user.setOtp(otp);
				basicuserDetails.setEmailVerified(0);
				basicuserDetails.setPhoneVerified(0);
				long timeNow = Calendar.getInstance().getTimeInMillis() + 600000;
				basicuserDetails.setOtpExpire(new Timestamp(timeNow));
			} catch (Exception e) {
				LOGGER.error("Not able to send otp email", e);
				basicuserDetails.setOtp(null);
			}

			session.save(basicuserDetails);
		} else {
			MyErrors error = new MyErrors("Password mismatch or invalid email id");
			ApiFormatter<MyErrors> err = ServiceUtil.convertToFailureResponse(error, "true", 406);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(err);
		}
		ApiFormatter<String> success = ServiceUtil.convertToSuccessResponse("You have been successfully registered");
		return ResponseEntity.status(HttpStatus.OK).body(success);
	}

	@Override
	public ResponseEntity<?> userLogin(UserDto userReq) throws Exception {
		if (!StringUtils.isEmpty((userReq.getEmail()))) {
			return loginUsingEmail(userReq);
		} else if (!StringUtils.isEmpty((userReq.getPhone()))) {
			return signUpUsingPhone(userReq);
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	private ResponseEntity<?> loginUsingEmail(UserDto userReq) {
		// final UserDetails userDetails =
		// userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		Session session = this.sessionFactory.getCurrentSession();
		String passWord = UserUtils.getEncryptedPass(userReq.getPassWord());
		String hql = "FROM BasicUserDetails E WHERE E.email =:email and E.passWord = :pass";
		@SuppressWarnings("unchecked")
		Query<BasicUserDetails> query = session.createQuery(hql);
		query.setString("email", userReq.getEmail());
		query.setString("pass", UserUtils.getEncryptedPass(userReq.getPassWord()));
		List<BasicUserDetails> results = query.list();
		if (results != null && results.size() == 1) {
			final String token = jwtTokenUtil.generateToken(results.get(0), results.get(0).getUserType());
			ApiFormatter<String> success = ServiceUtil.convertToSuccessResponse(token);
			return ResponseEntity.status(HttpStatus.OK).body(success);

			// return ResponseEntity.ok(new JwtResponse(token));
		}
		MyErrors error = new MyErrors("Invalid email/password");
		ApiFormatter<MyErrors> err = ServiceUtil.convertToFailureResponse(error, "true", 406);
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(err);
	}

	@Override
	public ResponseEntity<?> userResetPassword(UserDto userReq) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		if (userReq.getPassWord().equals(userReq.getPassWordVerify()) && UserUtils.isValid(userReq.getEmail())) {
			String hql = "FROM BasicUserDetails E WHERE E.email =:email and E.otp = :otp";
			@SuppressWarnings("unchecked")
			Query<BasicUserDetails> query = session.createQuery(hql);
			query.setString("email", userReq.getEmail());
			query.setInteger("otp", Integer.parseInt(userReq.getOtp()));
			List<BasicUserDetails> results = query.list();
			if (results != null && results.size() == 1) {
				results.get(0).setStatus(UserConstants.USER_SIGNUP_VERIFIED);
				results.get(0).setPassWord(userReq.getPassWord());
				session.update(results.get(0));
			}
			ApiFormatter<String> success = ServiceUtil.convertToSuccessResponse("success");
			return ResponseEntity.status(HttpStatus.OK).body(success);
		}
		MyErrors error = new MyErrors("INTERNAL SERVER ERROR");
		ApiFormatter<MyErrors> err = ServiceUtil.convertToFailureResponse(error, "true", 500);
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(err);
	}

	@Override
	public ResponseEntity<?> userVerifyOtp(UserDto userReq) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String hql = "FROM BasicUserDetails E WHERE E.email =:email and E.otp = :otp and E.status = :status";
		@SuppressWarnings("unchecked")
		Query<BasicUserDetails> query = session.createQuery(hql);
		query.setString("email", userReq.getEmail());
		query.setInteger("otp", Integer.parseInt(userReq.getOtp()));
		query.setString("status", UserConstants.USER_SIGNUP_CAPTURED);
		List<BasicUserDetails> results = query.list();
		if (results != null && results.size() == 1) {
			long timeNow = Calendar.getInstance().getTimeInMillis();
			if (results.get(0).getOtpExpire().after(new Timestamp(timeNow))) {
				String userName = getUserName();
				results.get(0).setStatus(UserConstants.USER_SIGNUP_VERIFIED);
				results.get(0).setUserName(userName);
				results.get(0).setEmailVerified(1);
				session.update(results.get(0));
				AdvancedUserDetail advancedUserDetails = new AdvancedUserDetail();
				advancedUserDetails.setUserId(results.get(0).getId());
				advancedUserDetails.setEmail(results.get(0).getEmail());
				advancedUserDetails.setStatus(UserConstants.USER_SIGNUP_VERIFIED);
				advancedUserDetails.setUserName(userName);
				session.save(advancedUserDetails);
				ApiFormatter<String> success = ServiceUtil.convertToSuccessResponse("success");
				return ResponseEntity.status(HttpStatus.OK).body(success);
			}
		}
		MyErrors error = new MyErrors("INTERNAL SERVER ERROR");
		ApiFormatter<MyErrors> err = ServiceUtil.convertToFailureResponse(error, "true", 500);
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(err);
	}

	private String getUserName() {
		Session session = this.sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(BasicUserDetails.class);
		c.addOrder(Order.desc("userName"));
		c.setMaxResults(1);
		c.add(Restrictions.isNotNull("userName"));
		List<BasicUserDetails> users = (List<BasicUserDetails>) c.list();
		if (users.size() > 0)
			return UserUtils.getAlphaNumericNextUserNameForGivenLastUserName(users.get(0).getUserName());
		return "A";
	}

	@Override
	public ResponseEntity<?> sendEotp(UserDto userReq) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String hql = "FROM BasicUserDetails E WHERE E.email =:email";
		@SuppressWarnings("unchecked")
		Query<BasicUserDetails> query = session.createQuery(hql);
		query.setString("email", userReq.getEmail());
		List<BasicUserDetails> results = query.list();
		if (results != null && results.size() == 1) {
			Integer otp = EmailService.sendEmailOTP("null", userReq.getEmail());
			results.get(0).setOtp(otp);
			session.update(results.get(0));
			ApiFormatter<String> success = ServiceUtil.convertToSuccessResponse("success");
			return ResponseEntity.status(HttpStatus.OK).body(success);
		}
		ApiFormatter<String> success = ServiceUtil.convertToSuccessResponse("You have been successfully registered");
		return ResponseEntity.status(HttpStatus.OK).body(success);
	}

	@Override
	public ResponseEntity<?> updateEmail(UserDto userReq) throws Exception {
		if (userReq.getEmail() != null) {
			Session session = this.sessionFactory.getCurrentSession();
			String hql = "FROM BasicUserDetails E WHERE E.userName =:userName";
			@SuppressWarnings("unchecked")
			Query<BasicUserDetails> query = session.createQuery(hql);
			query.setString("userName", userReq.getUserName());
			List<BasicUserDetails> results = query.list();
			if (results != null && results.size() == 1) {
				Integer otp = EmailService.sendEmailOTP("null", userReq.getEmail());
				if(otp !=null)
					results.get(0).setOtp(otp);
				else 
					results.get(0).setOtp(111111);
				results.get(0).setEmail(userReq.getEmail());
				results.get(0).setEmailVerified(0);
				session.update(results.get(0));

				hql = "FROM UserUpdate E WHERE E.userName =:userName and E.email =:email";
				@SuppressWarnings("unchecked")
				Query<UserUpdate> query1 = session.createQuery(hql);
				query1.setString("userName", userReq.getUserName());
				query1.setString("email", userReq.getEmail());
				List<UserUpdate> results1 = query1.list();
				if (results1 == null || results1.size() == 0) {
					UserUpdate userUpdate = new UserUpdate(results.get(0).getUserName(), results.get(0).getId(),
							results.get(0).getEmail(), UserConstants.NOT_VERIFIED);
					session.save(userUpdate);
				}
				ApiFormatter<String> success = ServiceUtil.convertToSuccessResponse("success");
				return ResponseEntity.status(HttpStatus.OK).body(success);
			}
		}
		return null;
	}

	@Override
	public String getUserNameByPhone(String phone) {
		Session session = this.sessionFactory.getCurrentSession();
		String hql = "FROM BasicUserDetails E WHERE E.phone =:phone";
		@SuppressWarnings("unchecked")
		Query<BasicUserDetails> query = session.createQuery(hql);
		query.setString("phone", phone);
		List<BasicUserDetails> results = query.list();
		if (results != null && results.size() == 1) {
			return results.get(0).getUserName();
		}
		return null;
	}
	@Override
	public BasicUserDetails getUserByPhone(String phone) {
		Session session = this.sessionFactory.getCurrentSession();
		String hql = "FROM BasicUserDetails E WHERE E.phone =:phone";
		@SuppressWarnings("unchecked")
		Query<BasicUserDetails> query = session.createQuery(hql);
		query.setString("phone", phone);
		List<BasicUserDetails> results = query.list();
		if (results != null && results.size() == 1) {
			return results.get(0);
		}
		return null;
	}
	@Override
	public String getUserNameByEmail(String email) {
		Session session = this.sessionFactory.getCurrentSession();
		String hql = "FROM BasicUserDetails E WHERE E.email =:email";
		@SuppressWarnings("unchecked")
		Query<BasicUserDetails> query = session.createQuery(hql);
		query.setString("email", email);
		List<BasicUserDetails> results = query.list();
		if (results != null && results.size() == 1) {
			return results.get(0).getUserName();
		}
		return null;
	}

	@Override
	public ResponseEntity<?> userVerifyEmailOtpAfterUpdate(UserDto userReq) {

		Session session = this.sessionFactory.getCurrentSession();
		String hql = "FROM BasicUserDetails E WHERE E.email =:email and E.otp = :otp";
		@SuppressWarnings("unchecked")
		Query<BasicUserDetails> query = session.createQuery(hql);
		query.setString("email", userReq.getEmail());
		query.setInteger("otp", Integer.parseInt(userReq.getOtp()));
		List<BasicUserDetails> results = query.list();
		if (results != null && results.size() == 1) {
			long timeNow = Calendar.getInstance().getTimeInMillis();
			if (results.get(0).getOtpExpire().after(new Timestamp(timeNow))) {
				results.get(0).setStatus(UserConstants.USER_EMAIL_UPDATE_VERIFIED);
				results.get(0).setEmailVerified(1);
				session.update(results.get(0));

				hql = "FROM AdvancedUserDetail E WHERE E.userName =:username";
				Query<AdvancedUserDetail> query1 = session.createQuery(hql);
				query1.setString("username", userReq.getUserName());
				List<AdvancedUserDetail> results1 = query1.list();
				results1.get(0).setEmail(results.get(0).getEmail());
				results1.get(0).setStatus(UserConstants.USER_EMAIL_UPDATE_VERIFIED);
				session.update(results1.get(0));
				// update table
				hql = "FROM UserUpdate E WHERE E.userName =:username and E.email =:email and status =:status";
				Query<UserUpdate> query2 = session.createQuery(hql);
				query2.setString("username", userReq.getUserName());
				query2.setString("email", userReq.getEmail());
				query2.setString("status", UserConstants.NOT_VERIFIED);
				List<UserUpdate> results2 = query2.list();
				if (results2 != null && results2.size() == 1) {
					results2.get(0).setStatus(UserConstants.UPDATE_VERIFIED);
					session.update(results2.get(0));
				}
				ApiFormatter<String> success = ServiceUtil.convertToSuccessResponse("success");
				return ResponseEntity.status(HttpStatus.OK).body(success);
			}
		}
		MyErrors error = new MyErrors("INTERNAL SERVER ERROR");
		ApiFormatter<MyErrors> err = ServiceUtil.convertToFailureResponse(error, "true", 500);
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(err);

	}

	@Override
	public Long getUserIdByUserName(String userName) {
		Session session = this.sessionFactory.getCurrentSession();
		String hql = "FROM BasicUserDetails E WHERE E.userName =:username";
		@SuppressWarnings("unchecked")
		Query<BasicUserDetails> query = session.createQuery(hql);
		query.setString("username", userName);
		List<BasicUserDetails> results = query.list();
		if (results != null && results.size() == 1) {
			return results.get(0).getId();
		}
		return null;
	}

	@Override
	public BasicUserDetails getUserByUserName(String userName) {
		Session session = this.sessionFactory.getCurrentSession();
		String hql = "FROM BasicUserDetails E WHERE E.userName =:userName";
		@SuppressWarnings("unchecked")
		Query<BasicUserDetails> query = session.createQuery(hql);
		query.setString("userName", userName);
		List<BasicUserDetails> results = query.list();		
		return results.get(0);
	}

	@Override
	public User getAdvanceUserByUserName(String userName) {
		Session session = this.sessionFactory.getCurrentSession();
		String hql = "FROM AdvancedUserDetail E WHERE E.userName =:userName";
		@SuppressWarnings("unchecked")
		Query<AdvancedUserDetail> query = session.createQuery(hql);
		query.setString("userName", userName);
		List<AdvancedUserDetail> results = query.list();
		if(results.size() == 1) {
			User user =  new User();
			String[] splitEmail = results.get(0).getEmail().split("@");
			if(results.get(0).getEmail().length()>=2)
				user.setEmail(splitEmail[0].substring(0, 2)+"****"+splitEmail[0].substring(splitEmail[0].length()-2)+ "@"+ splitEmail[1]);
			else
				user.setEmail(splitEmail[0]+"****@"+splitEmail[1]);
			user.setFirstName(results.get(0).getFirstName());
			user.setLastName(results.get(0).getLastName());
			user.setUserName(userName);
			user.setUserIdL(results.get(0).getUserId());
			return user;
		}
		return null;
	}
}
