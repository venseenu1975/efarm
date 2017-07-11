package com.farm.model;

import java.math.BigDecimal;

public class User {
	private Integer uId;
	private String uName;
	private String uAddress;
	private BigDecimal uLat;
	private BigDecimal uLong;
	private String uPhoneNo;
	private String uAliasName;
	private String uPass;
	
	
	public String getuAliasName() {
		return uAliasName;
	}
	public void setuAliasName(String uAliasName) {
		this.uAliasName = uAliasName;
	}
	public String getuPass() {
		return uPass;
	}
	public void setuPass(String uPass) {
		this.uPass = uPass;
	}
	public Integer getuId() {
		return uId;
	}
	public void setuId(Integer newUserId) {
		this.uId = newUserId;
	}
	public String getuName() {
		return uName;
	}
	public void setuName(String uName) {
		this.uName = uName;
	}
	public String getuAddress() {
		return uAddress;
	}
	public void setuAddress(String uAddress) {
		this.uAddress = uAddress;
	}
	public BigDecimal getuLat() {
		return uLat;
	}
	public void setuLat(BigDecimal uLat) {
		this.uLat = uLat;
	}
	public BigDecimal getuLong() {
		return uLong;
	}
	public void setuLong(BigDecimal uLong) {
		this.uLong = uLong;
	}
	public String getuPhoneNo() {
		return uPhoneNo;
	}
	public void setuPhoneNo(String uPhoneNo) {
		this.uPhoneNo = uPhoneNo;
	}
}
