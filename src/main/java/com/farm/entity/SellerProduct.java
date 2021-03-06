package com.farm.entity;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Timestamp;

public class SellerProduct {
	private Integer id;
	private Integer  prodId;
	private String prodDesc;
	private String prodName;
	private Blob prodImg;
	private String prodDeliveryMode;
	private String sellerId;
	private Timestamp date	;
	private Integer prodQuantity;
	private Timestamp productExpiry;
	private Boolean active;
	private BigDecimal productPrice;
	private String units;
	public String getUnits() {
		return units;
	}
	public void setUnits(String units) {
		this.units = units;
	}
	public BigDecimal getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getProdId() {
		return prodId;
	}
	public void setProdId(Integer prodId) {
		this.prodId = prodId;
	}
	public String getProdDesc() {
		return prodDesc;
	}
	public void setProdDesc(String prodDesc) {
		this.prodDesc = prodDesc;
	}
	public Blob getProdImg() {
		return prodImg;
	}
	public void setProdImg(Blob prodImg) {
		this.prodImg = prodImg;
	}
	public String getProdDeliveryMode() {
		return prodDeliveryMode;
	}
	public void setProdDeliveryMode(String prodDeliveryMode) {
		this.prodDeliveryMode = prodDeliveryMode;
	}
	public String getSellerId() {
		return sellerId;
	}
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	public Integer getProdQuantity() {
		return prodQuantity;
	}
	public void setProdQuantity(Integer prodQuantity) {
		this.prodQuantity = prodQuantity;
	}
	public Timestamp getProductExpiry() {
		return productExpiry;
	}
	public void setProductExpiry(Timestamp productExpiry) {
		this.productExpiry = productExpiry;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	
	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}	
	
}
