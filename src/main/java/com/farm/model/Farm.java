package com.farm.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
public class Farm  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String productAltImg;
	private Integer category;
	MultipartFile imgFile;
	private String imgFilePath;

	private Integer prodId;
	private String prodDesc;
	private Blob prodImg;
	private String prodDeliveryMode;
	private String sellerId;
	private Date date;
	private Integer prodQuantity;
	private String prodName;
	@DateTimeFormat(pattern = "MM dd yyyy - hh:mm")
	private Date prodExpiry;

	private Boolean active;

	private String prodUnits;
	private BigDecimal prodPrice;
	private String addToCartprodId;
	

	public String getAddToCartprodId() {
		return addToCartprodId;
	}

	public void setAddToCartprodId(String addToCartprodId) {
		this.addToCartprodId = addToCartprodId;
	}
	
	List<BasketObject> basket =new ArrayList<>();
	private Boolean addToCart;


	public Boolean getAddToCart() {
		return addToCart;
	}

	public void setAddToCart(Boolean addToCart) {
		this.addToCart = addToCart;
	}

	public Farm() {
		super();
	}

	public Farm(List<BasketObject> basket) {
		super();
		this.basket = basket;
	}

	public List<BasketObject> getBasket() {
		return basket;
	}

	public void setBasket(List<BasketObject> basket) {
		this.basket = basket;
	}

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

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String string) {
		this.sellerId = string;
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
