package com.farm.model;

import java.math.BigDecimal;

public class BasketObject {

	private String image;
	
	private String deliveryMode;
	private Integer sellerId;
	private Integer quantity;
	private String name;
	
	

	private String prodUnits;
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getDeliveryMode() {
		return deliveryMode;
	}
	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}
	public Integer getSellerId() {
		return sellerId;
	}
	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProdUnits() {
		return prodUnits;
	}
	public void setProdUnits(String prodUnits) {
		this.prodUnits = prodUnits;
	}
	public BigDecimal getProdPrice() {
		return prodPrice;
	}
	public void setProdPrice(BigDecimal prodPrice) {
		this.prodPrice = prodPrice;
	}
	private BigDecimal prodPrice;
	
}
