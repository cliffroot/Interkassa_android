package com.interkassa.models;

public class PaymentInfo {

	String psPrice;

	String exchangeRate;

	public PaymentInfo(String psPrice, String exchangeRate) {
		super();
		this.psPrice = psPrice;
		this.exchangeRate = exchangeRate;
	}

	public String getPsPrice() {
		return psPrice;
	}

	public void setPsPrice(String psPrice) {
		this.psPrice = psPrice;
	}

	public String getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(String exchangeRate) {
		this.exchangeRate = exchangeRate;
	}




}
