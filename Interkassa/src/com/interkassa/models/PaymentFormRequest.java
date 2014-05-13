package com.interkassa.models;

import java.util.List;

import org.apache.http.NameValuePair;

public class PaymentFormRequest {

	List<NameValuePair> parameters;

	String action;

	public PaymentFormRequest(List<NameValuePair> parameters, String action) {
		super();
		this.parameters = parameters;
		this.action = action;
	}

	public List<NameValuePair> getParameters() {
		return parameters;
	}

	public void setParameters(List<NameValuePair> parameters) {
		this.parameters = parameters;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}



}
