package com.interkassa.models;

public class HTMLWithBaseUrl {

	String HTML;

	String baseUrl;

	public HTMLWithBaseUrl(String hTML, String baseUrl) {
		super();
		HTML = hTML;
		this.baseUrl = baseUrl;
	}

	public String getHTML() {
		return HTML;
	}

	public void setHTML(String hTML) {
		HTML = hTML;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}


}
