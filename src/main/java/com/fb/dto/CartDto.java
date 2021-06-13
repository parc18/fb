package com.fb.dto;

import java.io.Serializable;

public class CartDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String userName;
	private String sku;
	private String quantity;
	private Double price;
	private Double updatedPrice;
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getUpdatedPrice() {
		return updatedPrice;
	}
	public void setUpdatedPrice(Double updatedPrice) {
		this.updatedPrice = updatedPrice;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
