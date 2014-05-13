package com.interkassa.views;

import java.util.ArrayList;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.interkassa.Interkassa;
import com.interkassa.helpers.WebViewActivity_;
import com.interkassa.interact.R;
import com.interkassa.models.HTMLWithBaseUrl;
import com.interkassa.models.PaymentInfo;
import com.interkassa.models.PaymentMethod;

@EViewGroup(resName="payment_chooser_layout")
public class PaymentsMethodChooserLayout extends LinearLayout implements OnClickListener, OnTouchListener{

	@ViewById(resName="progress")
	ProgressBar progressBar;

	@ViewById(resName="systems")
	LinearLayout systems;

	@ViewById(resName="currencies")
	LinearLayout currencies;

	@ViewById(resName="pay_info")
	LinearLayout payInfo;

	@Bean 
	Interkassa interkassa;

	ArrayList<PaymentMethod> paymentMethods;

	final Integer NUM_OF_COLS = 3;

	LinearLayout.LayoutParams matchWrap;

	String chosenAlias;

	public PaymentsMethodChooserLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@AfterInject
	void getMethods () {
		interkassa.retirevePayingMethods(Interkassa.getCurrentCurrency(), Interkassa.getCurrentAmount(), new Interkassa.ResultListener<ArrayList<PaymentMethod>>() {

			@Override
			public void onSuccess(ArrayList<PaymentMethod> t) {
				paymentMethods = t;
				showSystems();
			}

			@Override
			public void onFail(ArrayList<PaymentMethod> t) { }
		});
	}

	@UiThread
	void showSystems () {
		progressBar.setVisibility(View.INVISIBLE);
		systems.setVisibility(View.VISIBLE);

		int cols = NUM_OF_COLS;
		ArrayList<String> uniquePaymentSystems = new ArrayList<String>();
		for (int i = 0; i < paymentMethods.size(); i++) {
			if (!uniquePaymentSystems.contains(paymentMethods.get(i).getName()) && contains(Interkassa.ALLOWED, paymentMethods.get(i).getName())) {
				uniquePaymentSystems.add(paymentMethods.get(i).getName());
			}
		}
		int rows = uniquePaymentSystems.size() / cols + (uniquePaymentSystems.size() % cols == 0? 0 : 1);

		matchWrap = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		matchWrap.bottomMargin = 5;
		matchWrap.topMargin = 5;

		LinearLayout.LayoutParams lip = new LinearLayout.LayoutParams(150, 150);
		lip.leftMargin = 10;
		lip.rightMargin = 10;

		for (int i = 0; i < rows; i++) {
			LinearLayout row = new LinearLayout(getContext());
			row.setGravity(Gravity.CENTER);
			systems.addView(row, matchWrap);
			for (int j = 0; j < cols; j++) {
				ImageView iv = new ImageView(getContext());
				iv.setBackgroundColor(Color.WHITE);
				if (uniquePaymentSystems.get(i * cols + j).contains("privat")) {
					iv.setImageResource(R.drawable.privat);
				} else if (uniquePaymentSystems.get(i * cols + j).contains("webmoney")) {
					iv.setImageResource(R.drawable.webmoney);
				} else if (uniquePaymentSystems.get(i * cols + j).contains("paxum")) {
					iv.setImageResource(R.drawable.paxum);
				} else if (uniquePaymentSystems.get(i * cols + j).contains("yand")) {
					iv.setImageResource(R.drawable.yandex);
				} else if (uniquePaymentSystems.get(i * cols + j).contains("tele")) {
					iv.setImageResource(R.drawable.telemoney);
				} else if (uniquePaymentSystems.get(i * cols + j).contains("perf")) {
					iv.setImageResource(R.drawable.perfectmoney);
				}
				iv.setTag(uniquePaymentSystems.get(i * cols + j));
				iv.setOnClickListener(this);
				iv.setOnTouchListener(this);
				row.addView(iv, lip);
			}
		}

	}

	@Override
	public void onClick(View v) {
		systems.setVisibility(View.GONE);
		currencies.setVisibility(View.VISIBLE);
		final RadioGroup radioGroup = new RadioGroup(getContext());
		LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		currencies.setGravity(Gravity.CENTER_HORIZONTAL);
		currencies.addView(radioGroup, llp);
		for (PaymentMethod paymentMethod: paymentMethods) {
			if (paymentMethod.getName().equals(v.getTag())) {
				RadioButton radioButton = new RadioButton(getContext());
				radioButton.setText(paymentMethod.getCurrencyAlias());
				radioButton.setTag(paymentMethod.getAlias());
				radioGroup.addView(radioButton, matchWrap);
			}
		}
		radioGroup.check(radioGroup.getChildAt(0).getId());
		Button next = new Button(getContext()); 
		next.setText("Продовжити"); //FIXME: move to resources to localize;
		currencies.addView(next);
		next.setPadding(10, 3, 10, 3);
		next.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				chosenAlias = (String) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId()).getTag();
				showPaymentInfo();
			}
		});
	}

	private void showPaymentInfo () {
		progressBar.setVisibility(View.VISIBLE);
		currencies.setVisibility(View.GONE);
		interkassa.retrievePaymentInfo(Interkassa.getCurrentCurrency(), Interkassa.getCurrentAmount(), chosenAlias, new Interkassa.ResultListener<PaymentInfo>() {

			@Override
			public void onSuccess(PaymentInfo t) {
				showPaymentInfo(t);
			}

			@Override
			public void onFail(PaymentInfo t) {
				//TODO: handle error;
			}
		});
	}

	@UiThread 
	public void showPaymentInfo (PaymentInfo t) {
		progressBar.setVisibility(View.GONE);
		payInfo.setVisibility(View.VISIBLE);
		TextView payPrice = new TextView(getContext());
		payPrice.setText(Html.fromHtml("<b>Загальна вартість:</b> " + t.getPsPrice()));
		payInfo.addView(payPrice, matchWrap);
		TextView payExchange = new TextView(getContext());
		payExchange.setText(Html.fromHtml("<b>Поточний курс:</b> " + t.getExchangeRate()));
		payInfo.addView(payExchange, matchWrap);
		TextView totalPay = new TextView(getContext());
		totalPay.setText(Html.fromHtml("<b>Всього платити: </b> " + String.format("%.2f", ((Double.valueOf(t.getPsPrice()) / Double.valueOf(t.getExchangeRate()))))
				+ " " + Interkassa.getCurrentCurrency()));
		payInfo.addView(totalPay, matchWrap);
		Button next = new Button(getContext()); 
		next.setText("Продовжити"); //FIXME: move to resources to localize;
		payInfo.addView(next);
		next.setPadding(10, 3, 10, 3);
		next.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				interkassa.retrievePayingForm(Interkassa.getCurrentCurrency(), Interkassa.getCurrentAmount(), chosenAlias, new Interkassa.ResultListener<HTMLWithBaseUrl>() {
					@Override
					public void onSuccess(HTMLWithBaseUrl t) {
						Intent intent = new Intent (getContext(), WebViewActivity_.class);
						intent.putExtra("baseUrl", t.getBaseUrl());
						intent.putExtra("html", t.getHTML());
						(getContext()).startActivity(intent);
					}

					@Override
					public void onFail(HTMLWithBaseUrl t) {
						// TODO handle fail
					}
				});
			}
		});
	}

	private boolean contains (String [] tokens, String name) {
		for (String token: tokens) {
			if (name.contains(token)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean onTouch(View view, MotionEvent event) {
		final int lightColor = Color.parseColor("#AAAAAA");
		final int darkColor = Color.parseColor("#000000");
		final int backColor = Color.parseColor("#CCCCCC");
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			ImageView i = (ImageView) view;
			Drawable d = i.getDrawable();
			ColorFilter cf = new LightingColorFilter(lightColor, darkColor);
			d.setColorFilter(cf);
			view.setBackgroundColor(backColor);
			view.invalidate();
		}
		if (event.getAction() == MotionEvent.ACTION_UP) {
			ImageView i = (ImageView) view;
			Drawable d = i.getDrawable();
			d.clearColorFilter();
			view.setBackgroundColor(Color.WHITE);
			view.invalidate();
		}
		return false;
	}


}
