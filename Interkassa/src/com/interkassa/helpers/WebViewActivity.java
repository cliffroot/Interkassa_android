package com.interkassa.helpers;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

@EActivity(resName="webview_activity")
public class WebViewActivity extends Activity{

	@ViewById(resName="pay")
	WebView pay;

	@Extra 
	String html;

	@Extra 
	String baseUrl;

	@SuppressLint("SetJavaScriptEnabled")
	@AfterViews 
	void setStuff () {
		WebSettings webSettings = pay.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setDomStorageEnabled(true);
		webSettings.setLoadWithOverviewMode(true);
		webSettings.setLoadsImagesAutomatically(true);
		pay.getSettings().setBuiltInZoomControls(true);
		webSettings.setLoadWithOverviewMode(true);
		pay.getSettings().setUseWideViewPort(true);
		pay.setWebViewClient(new WebViewClient());

		pay.loadDataWithBaseURL(baseUrl, html,  "text/html", "CP-1251", "");
	}

}
