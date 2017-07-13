package com.farm.entity;

import java.math.BigDecimal;

public class Order {

private Integer id;
private Integer buyerId;
private BigDecimal amount;
private String paymentMode;


public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public Integer getBuyerId() {
	return buyerId;
}
public void setBuyerId(Integer buyerId) {
	this.buyerId = buyerId;
}
public BigDecimal getAmount() {
	return amount;
}
public void setAmount(BigDecimal amount) {
	this.amount = amount;
}
public String getPaymentMode() {
	return paymentMode;
}
public void setPaymentMode(String paymentMode) {
	this.paymentMode = paymentMode;
}
}
