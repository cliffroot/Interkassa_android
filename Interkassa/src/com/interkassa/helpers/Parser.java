package com.interkassa.helpers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.interkassa.constants.BaseUrl;
import com.interkassa.models.PaymentFormRequest;
import com.interkassa.models.PaymentInfo;
import com.interkassa.models.PaymentMethod;

public class Parser {

	public static ArrayList<PaymentMethod> parsePaymentMethodsList (String json) throws JSONException {
		ArrayList<PaymentMethod> payingMethods = new ArrayList<PaymentMethod>();
		try {
			JSONArray ms = new JSONObject(json).getJSONObject("resultData").getJSONArray("paywaySet");
			for (int i = 0; i < ms.length(); i++) {
				String name = ms.getJSONObject(i).getString("ps");
				String alias = ms.getJSONObject(i).getString("als");
				String curAls = ms.getJSONObject(i).getString("curAls");
				PaymentMethod pm = new PaymentMethod(name, alias, curAls);
				payingMethods.add(pm);
			}
		} catch (JSONException e) {
			throw e;
		}
		return payingMethods;
	}

	public static PaymentInfo parsePaymentInfo (String json) throws JSONException {
		PaymentInfo paymentInfo = null;
		try {
			JSONObject resp = new JSONObject(json).getJSONObject("resultData").getJSONObject("invoice");
			String psPrice = resp.getString("psPrice");
			String ikExchRate = resp.getString("ikExchRate");
			paymentInfo = new PaymentInfo(psPrice, ikExchRate);
		} catch (JSONException e) {
			throw e;
		}
		return paymentInfo;
	}

	@SuppressWarnings("rawtypes")
	public static PaymentFormRequest parseParameter (String json) throws JSONException {
		JSONObject response = new JSONObject(json).getJSONObject("resultData").getJSONObject("paymentForm");
		String action = response.getString("action");
		Iterator keys = response.getJSONObject("parameters").keys();
		JSONObject ps = response.getJSONObject("parameters");
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);  
		while (keys.hasNext()) {
			String key = (String) keys.next();
			String parameter = ps.getString(key);
			nameValuePairs.add(new BasicNameValuePair(key, parameter));  
		}
		PaymentFormRequest paymentFormRequest = new PaymentFormRequest(nameValuePairs, action);
		return paymentFormRequest;
	}

	public static String parseBaseURL (String action) {
		String baseUrl = "";
		if (action.contains("privat")) {
			baseUrl =  BaseUrl.getBaseUrl(BaseUrl.System.PRIVAT);
		} else if (action.contains("liq")) {
			baseUrl =  BaseUrl.getBaseUrl(BaseUrl.System.LIQPAY);
		} else if (action.contains("yandex")) {
			baseUrl = BaseUrl.getBaseUrl(BaseUrl.System.YANDEX);
		} else if (action.contains("webmoney")) {
			baseUrl = BaseUrl.getBaseUrl(BaseUrl.System.WEBMONEY);
		} else if (action.contains("w1")) {
			baseUrl = BaseUrl.getBaseUrl(BaseUrl.System.W1);
		} else if (action.contains("perfect")) {
			baseUrl = BaseUrl.getBaseUrl(BaseUrl.System.PERFECT);
		} else if (action.contains("paxum")) {
			baseUrl = BaseUrl.getBaseUrl(BaseUrl.System.PAXUM);
		} else if (action.contains("tele")) {
			baseUrl = BaseUrl.getBaseUrl(BaseUrl.System.TELEMONEY);
		}
		return baseUrl;
	}
}
