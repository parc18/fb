package com.fb.document;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Product extends BaseDocument implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1087282761591235153L;
	@Id
	private String id;
	private String category;
	private String SKU;
	private String name;
	private Integer index;
	private Double stock;
	private Double sold;
	private Double marketPrice;
	private Double effectiveMarketPrice;
	private String marketPriceUnit;
	private Double bulkPrice;
	private Double effectiveBulkPrice;
	private String bulkPriceUnit;
	private String status;
	private List<String> tax;
	private Double profit; // minus in case of loss
	private double revenue;
	private boolean isDiscounted;
	private String updatedByUserId;
	private List<String> imgUrls;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSKU() {
		return SKU;
	}
	public void setSKU(String sKU) {
		SKU = sKU;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	public Double getStock() {
		return stock;
	}
	public void setStock(Double stock) {
		this.stock = stock;
	}
	public Double getSold() {
		return sold;
	}
	public void setSold(Double sold) {
		this.sold = sold;
	}
	public Double getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
	}
	public String getMarketPriceUnit() {
		return marketPriceUnit;
	}
	public void setMarketPriceUnit(String marketPriceUnit) {
		this.marketPriceUnit = marketPriceUnit;
	}
	public Double getBulkPrice() {
		return bulkPrice;
	}
	public void setBulkPrice(Double bulkPrice) {
		this.bulkPrice = bulkPrice;
	}
	public String getBulkPriceUnit() {
		return bulkPriceUnit;
	}
	public void setBulkPriceUnit(String bulkPriceUnit) {
		this.bulkPriceUnit = bulkPriceUnit;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<String> getTax() {
		return tax;
	}
	public void setTax(List<String> tax) {
		this.tax = tax;
	}
	public Double getProfit() {
		return profit;
	}
	public void setProfit(Double profit) {
		this.profit = profit;
	}
	public double getRevenue() {
		return revenue;
	}
	public void setRevenue(double revenue) {
		this.revenue = revenue;
	}
	public Double getEffectiveMarketPrice() {
		return effectiveMarketPrice;
	}
	public void setEffectiveMarketPrice(Double effectiveMarketPrice) {
		this.effectiveMarketPrice = effectiveMarketPrice;
	}
	public Double getEffectiveBulkPrice() {
		return effectiveBulkPrice;
	}
	public void setEffectiveBulkPrice(Double effectiveBulkPrice) {
		this.effectiveBulkPrice = effectiveBulkPrice;
	}
	public boolean isDiscounted() {
		return isDiscounted;
	}
	public void setDiscounted(boolean isDiscounted) {
		this.isDiscounted = isDiscounted;
	}
	public String getUpdatedByUserId() {
		return updatedByUserId;
	}
	public void setUpdatedByUserId(String updatedByUserId) {
		this.updatedByUserId = updatedByUserId;
	}
	public List<String> getImgUrls() {
		return imgUrls;
	}
	public void setImgUrls(List<String> imgUrls) {
		this.imgUrls = imgUrls;
	}

}
