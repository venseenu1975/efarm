package com.farm.entity;

import java.sql.Blob;

public class Product {
	
	private Integer prodId;
	private String prodDesc;
	private String prodName;
	private Blob prodImg;
	private Integer catId;
	
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
	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	public Blob getProdImg() {
		return prodImg;
	}
	public void setProdImg(Blob prodImg) {
		this.prodImg = prodImg;
	}
	public Integer getCatId() {
		return catId;
	}
	public void setCatId(Integer catId) {
		this.catId = catId;
	}
	
	
}
