package com.interkassa.models;

public class PaymentMethod {

	String name;

	String alias;

	String currencyAlias;

	public PaymentMethod(String name, String alias, String currencyAlias) {
		super();
		this.name = name;
		this.alias = alias;
		this.currencyAlias = currencyAlias;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getCurrencyAlias() {
		return currencyAlias;
	}

	public void setCurrencyAlias(String currencyAlias) {
		this.currencyAlias = currencyAlias;
	}



}
