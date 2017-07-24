package com.farm.model;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class User {
	private Integer uId;
	
	@NotEmpty
	@NotNull
	@Size(min=4, max=30)
	private String uName;
	@NotEmpty
	@NotNull
	private String uAddress;
	@NotNull
	private BigDecimal uLat;
	@NotNull
	private BigDecimal uLong;
	@NotEmpty
	private String uPhoneNo;
	@NotEmpty
	private String uAliasName;
	@NotEmpty
	@Size(min=4, max=30)
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
