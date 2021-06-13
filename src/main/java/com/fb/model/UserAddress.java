package com.fb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_address")
public class UserAddress {
	@Id
	@Column(name = "addr_id")
    private Long address;
	@Column(name = "user_id")
    private Long userId;
	@Column(name = "addr_1")
    private String addrLineOne;
	@Column(name = "addr_2")
    private String addrLineTwo;
	@Column(name = "area")
    private String area;
	@Column(name = "city")
    private String city;
	@Column(name = "state")
    private String state;
	@Column(name = "country")
    private String country;
	@Column(name = "addr_name")
    private String addressName;
	@Column(name = "pinCode")
    private Integer pinCode;
	@Column(name = "status")
    private String status;
	public Long getAddress() {
		return address;
	}
	public void setAddress(Long address) {
		this.address = address;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getAddrLineOne() {
		return addrLineOne;
	}
	public void setAddrLineOne(String addrLineOne) {
		this.addrLineOne = addrLineOne;
	}
	public String getAddrLineTwo() {
		return addrLineTwo;
	}
	public void setAddrLineTwo(String addrLineTwo) {
		this.addrLineTwo = addrLineTwo;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getAddressName() {
		return addressName;
	}
	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}
	public Integer getPinCode() {
		return pinCode;
	}
	public void setPinCode(Integer pinCode) {
		this.pinCode = pinCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
