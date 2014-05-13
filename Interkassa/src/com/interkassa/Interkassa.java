package com.interkassa;

import java.util.ArrayList;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.json.JSONException;

import com.interkassa.constants.Currency;
import com.interkassa.constants.Currency.Money;
import com.interkassa.helpers.Parser;
import com.interkassa.helpers.RequestManager;
import com.interkassa.models.HTMLWithBaseUrl;
import com.interkassa.models.PaymentFormRequest;
import com.interkassa.models.PaymentInfo;
import com.interkassa.models.PaymentMethod;

@EBean
public class Interkassa {

	// the list of allowed payment systems;
	public static String [] ALLOWED = new String [] {"privat", "yand", "webmoney", "perf", "paxum", "tele"};

	private RequestManager requestManager;
	private String id = "51237daa8f2a2d8413000000";
	private ArrayList<PaymentMethod> paymentMethods;	

	protected static Money currentCurrency = Currency.Money.Hryvnia;
	protected static double currentAmount  = 0.0d;

	public Interkassa() {
		super();
		requestManager = new RequestManager(id);
	}

	public static Money getCurrentCurrency() {
		return currentCurrency;
	}

	public static double getCurrentAmount() {
		return currentAmount;
	}

	public static void setCurrentAmount(double currentAmount) {
		Interkassa.currentAmount = currentAmount;
	}

	public static void setCurrentCurrency(Money currentCurrency) {
		Interkassa.currentCurrency = currentCurrency;
	}

	public void retirevePayingMethods (Currency.Money currency, Double amount, ResultListener<ArrayList<PaymentMethod>> resultListener) {
		if (paymentMethods == null) {
			getPayingMethods(Currency.getMoneyKey(currency), amount, resultListener);
		} else {
			resultListener.onSuccess(paymentMethods);
		}
	}

	@Background
	protected void getPayingMethods (Integer currency, Double amount, ResultListener<ArrayList<PaymentMethod>> resultListener) {
		String json = requestManager.retrievePayingMethods(currency, amount);
		try {
			resultListener.onSuccess(Parser.parsePaymentMethodsList(json));
		} catch (JSONException ex) {
			resultListener.onFail(null);
		}
	}

	public void retrievePaymentInfo (Currency.Money currency, Double amount, String paymetSystemAlias, ResultListener<PaymentInfo> resultListener) {
		getPaymentInfo(Currency.getMoneyKey(currency), amount, paymetSystemAlias, resultListener);
	}

	@Background 
	protected void getPaymentInfo (Integer currency, Double amount, String paymentSystemAlias, ResultListener<PaymentInfo> resultListener) {
		String json = requestManager.retrievePayingFee(currency, amount, paymentSystemAlias);
		try {
			resultListener.onSuccess(Parser.parsePaymentInfo(json));
		} catch (JSONException ex) {
			resultListener.onFail(null);
		}
	}

	public void retrievePayingForm (Currency.Money currency, Double amount, String paymetSystemAlias, ResultListener<HTMLWithBaseUrl> resultListener) {
		getPayingForm (Currency.getMoneyKey(currency), amount, paymetSystemAlias, resultListener);
	}

	@Background
	protected void getPayingForm (Integer currency, Double amount, String paymentSystemAlias, ResultListener<HTMLWithBaseUrl> resultListener) {
		String json = requestManager.retrievePayingForm(currency, amount, paymentSystemAlias);
		try {
			PaymentFormRequest paymentFormRequest = Parser.parseParameter(json);
			String HTML = requestManager.getPayingForm(paymentFormRequest.getAction(), paymentFormRequest.getParameters());
			resultListener.onSuccess(new HTMLWithBaseUrl(HTML, Parser.parseBaseURL(paymentFormRequest.getAction())));
		} catch (JSONException e) {
			resultListener.onFail(null);
			return;
		}

	}

	public interface ResultListener<T> {
		public void onSuccess (T t);
		public void onFail (T t);
	}

}
