package com.farm.model;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

public class Farm {
	private String productAltImg;
	private Integer category;
	MultipartFile imgFile;
	private String imgFilePath;

	private Integer prodId;
	private String prodDesc;
	private Blob prodImg;
	private String prodDeliveryMode;
	private Integer sellerId;
	private Date date;
	private Integer prodQuantity;
	private String prodName;
	@DateTimeFormat(pattern = "MM dd yyyy - hh:mm")
	private Date prodExpiry;

	private Boolean active;

	private String prodUnits;
	private BigDecimal prodPrice;

	public String getImgFilePath() {
		return imgFilePath;
	}

	public void setImgFilePath(String imgFilePath) {
		this.imgFilePath = imgFilePath;
	}

	public MultipartFile getImgFile() {
		return imgFile;
	}

	public void setImgFile(MultipartFile imgFile) {
		this.imgFile = imgFile;
	}

	public String getProductAltImg() {
		return productAltImg;
	}

	public void setProductAltImg(String productAltImg) {
		this.productAltImg = productAltImg;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
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

	public Integer getSellerId() {
		return sellerId;
	}

	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}

	public Integer getProdQuantity() {
		return prodQuantity;
	}

	public void setProdQuantity(Integer prodQuantity) {
		this.prodQuantity = prodQuantity;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Date getDate() {
		return date;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public void setDate(Date date) {
		this.date = date;
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

	public Date getProdExpiry() {
		return prodExpiry;
	}

	public void setProdExpiry(Date prodExpiry) {
		this.prodExpiry = prodExpiry;
	}
}
