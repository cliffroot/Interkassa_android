package com.interkassa.helpers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class RequestManager extends DefaultHttpClient{

	static final String URL = "https://sci.interkassa.com/";

	static String DESK = "51237daa8f2a2d8413000000"; 

	public RequestManager (String id) {
		super();
		DESK = id;
	}

	public String retrievePayingMethods(int currency, double amount) {
		HttpPost postRequest = new HttpPost(URL);

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);  
		nameValuePairs.add(new BasicNameValuePair("ik_co_id", DESK));  
		nameValuePairs.add(new BasicNameValuePair("ik_am", String.valueOf(amount)));  
		nameValuePairs.add(new BasicNameValuePair("ik_desc", "oy"));  
		nameValuePairs.add(new BasicNameValuePair("ik_int", "json"));  
		nameValuePairs.add(new BasicNameValuePair("ik_act", "payways"));  
		nameValuePairs.add(new BasicNameValuePair("ik_pm_no", "ID_4233"));  
		nameValuePairs.add(new BasicNameValuePair("ik_cur", String.valueOf(currency)));

		try {
			postRequest.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		try {
			HttpResponse getResponse = this.execute(postRequest);
			final int statusCode = getResponse.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				Log.i("OI", statusCode + "");
				return null;
			}
			HttpEntity getResponseEntity = getResponse.getEntity();
			if (getResponseEntity != null) {
				String a = EntityUtils.toString(getResponseEntity, HTTP.UTF_8);
				//Log.i("ARESPONSE", a);
				return a;
			}
		} 
		catch (IOException e) {
			postRequest.abort();
			e.printStackTrace();
		}

		return null;
	}

	public String retrievePayingFee(int currency, double amount, String alias) {
		HttpPost postRequest = new HttpPost(URL);

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);  
		nameValuePairs.add(new BasicNameValuePair("ik_co_id", DESK));  
		nameValuePairs.add(new BasicNameValuePair("ik_am", String.valueOf(amount)));  
		nameValuePairs.add(new BasicNameValuePair("ik_desc", "oy"));  
		nameValuePairs.add(new BasicNameValuePair("ik_int", "json"));  
		nameValuePairs.add(new BasicNameValuePair("ik_act", "payway"));  
		nameValuePairs.add(new BasicNameValuePair("ik_pw_via", alias));  
		nameValuePairs.add(new BasicNameValuePair("ik_pm_no", "ID_4233"));  
		nameValuePairs.add(new BasicNameValuePair("ik_cur", String.valueOf(currency)));

		try {
			postRequest.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		try {
			HttpResponse getResponse = this.execute(postRequest);
			final int statusCode = getResponse.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				Log.i("OI", statusCode + "");
				return null;
			}
			HttpEntity getResponseEntity = getResponse.getEntity();
			if (getResponseEntity != null) {
				String a = EntityUtils.toString(getResponseEntity, HTTP.UTF_8);
				//Log.i("ARESPONSE", a);
				return a;
			}
		} 
		catch (IOException e) {
			postRequest.abort();
			e.printStackTrace();
		}

		return null;
	}

	public String retrievePayingForm(int currency, double amount, String alias) {
		HttpPost postRequest = new HttpPost(URL);

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);  
		nameValuePairs.add(new BasicNameValuePair("ik_co_id", DESK));  
		nameValuePairs.add(new BasicNameValuePair("ik_am", String.valueOf(amount)));  
		nameValuePairs.add(new BasicNameValuePair("ik_desc", "oy"));  
		nameValuePairs.add(new BasicNameValuePair("ik_int", "json"));  
		nameValuePairs.add(new BasicNameValuePair("ik_act", "process"));  
		nameValuePairs.add(new BasicNameValuePair("ik_pw_via", alias));  
		nameValuePairs.add(new BasicNameValuePair("ik_pm_no", "ID_4233"));  
		nameValuePairs.add(new BasicNameValuePair("ik_cur", String.valueOf(currency)));

		try {
			postRequest.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		try {
			HttpResponse getResponse = this.execute(postRequest);
			final int statusCode = getResponse.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				Log.i("OI", statusCode + "");
				return null;
			}
			HttpEntity getResponseEntity = getResponse.getEntity();
			if (getResponseEntity != null) {
				String a = EntityUtils.toString(getResponseEntity, HTTP.UTF_8);
				Log.i("ARESPONSE", a);
				return a;
			}
		} 
		catch (IOException e) {
			postRequest.abort();
			e.printStackTrace();
		}

		return null;
	}

	public String getPayingForm (String url, List<NameValuePair> parameters) {
		HttpPost postRequest = new HttpPost(url);
		try {
			postRequest.setEntity(new UrlEncodedFormEntity(parameters));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		try {
			HttpResponse getResponse = this.execute(postRequest);
			final int statusCode = getResponse.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				Log.i("OI", statusCode + "");
				return null;
			}
			HttpEntity getResponseEntity = getResponse.getEntity();
			if (getResponseEntity != null) {
				String a = EntityUtils.toString(getResponseEntity, "CP-1251");
				Log.i("ARESPONSE", a);
				return a;
			}
		} 
		catch (IOException e) {
			postRequest.abort();
			e.printStackTrace();
		}

		return null;
	}
}
