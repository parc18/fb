package com.fb.www.pojos;

public class User {
    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
    private String passcode;
    private String contactNumber;
    private String city;
    private String address;
    private String status;
    private String welcomeDate;
    private String sessionValue;
    private String userName;
    private Integer gameId;
    private String underX;
    private Integer done;
    public Integer getGameId() {
		return gameId;
	}

	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}

	public String getUnderX() {
		return underX;
	}

	public void setUnderX(String underX) {
		this.underX = underX;
	}

	public Integer getDone() {
		return done;
	}

	public void setDone(Integer done) {
		this.done = done;
	}

	private Long userIdL;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWelcomeDate() {
        return welcomeDate;
    }

    public void setWelcomeDate(String welcomeDate) {
        this.welcomeDate = welcomeDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

	public String getSessionValue() {
		return sessionValue;
	}

	public void setSessionValue(String sessionValue) {
		this.sessionValue = sessionValue;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getUserIdL() {
		return userIdL;
	}

	public void setUserIdL(Long userIdL) {
		this.userIdL = userIdL;
	}

}
