package com.fb.document;

public class CartItem {
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

}
