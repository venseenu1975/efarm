package com.farm.model;

import java.math.BigDecimal;

public class BasketObject {

	private String image;
	
	private String deliveryMode;
	private Integer sellerId;
	private Integer quantity;
	private String name;
	private BigDecimal price;
	private Boolean addToCart;

	public BasketObject() {
		super();
	}
	public BasketObject(Integer quantity, String name,
			BigDecimal price, Boolean addToCart, String prodUnits) {
		super();
		this.quantity = quantity;
		this.name = name;
		this.price = price;
		this.addToCart = addToCart;
		this.prodUnits = prodUnits;
	}
	
	
	@Override
	public String toString() {
		return "BasketObject [ deliveryMode=" + deliveryMode 
				+ ", quantity=" + quantity + ", name=" + name + ", price=" + price + ", addToCart=" + addToCart
				+ ", prodUnits=" + prodUnits+ "]";
	}


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
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Boolean getAddToCart() {
		return addToCart;
	}
	public void setAddToCart(Boolean addToCart) {
		this.addToCart = addToCart;
	}
	
}
