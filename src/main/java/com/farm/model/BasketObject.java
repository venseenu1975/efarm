package com.farm.model;

import java.math.BigDecimal;

public class BasketObject {

	private String image;
	private Integer sellerProdId;
	private String deliveryMode;
	private String sellerId;
	private Integer quantity;
	private String name;
	private BigDecimal price;
	private BigDecimal itemPrice;
	private Boolean addToCart;
	private String prodUnits;
	private BigDecimal totalPrice;
	private Integer orderId;
	
	
	public BigDecimal getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(BigDecimal itemPrice) {
		this.itemPrice = itemPrice;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getSellerProdId() {
		return sellerProdId;
	}
	public void setSellerProdId(Integer sellerProdId) {
		this.sellerProdId = sellerProdId;
	}
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
	public String getSellerId() {
		return sellerId;
	}
	public void setSellerId(String sellerId) {
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
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
}
