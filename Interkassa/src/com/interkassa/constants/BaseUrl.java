package com.interkassa.constants;

import java.util.HashMap;

public class BaseUrl {

	public enum System {PRIVAT, LIQPAY, YANDEX, WEBMONEY, W1, PERFECT, PAXUM, TELEMONEY}

	private static HashMap <System, String> dictionary;

	public static String getBaseUrl (System system) {
		fillDictionary();
		return dictionary.get(system);
	}

	private static void fillDictionary () {
		if (dictionary == null) {
			dictionary = new HashMap <System, String>();
			dictionary.put(System.PRIVAT, "https://api.privatbank.ua/p24api/");
			dictionary.put(System.LIQPAY, "https://liqpay.com/api/");
			dictionary.put(System.YANDEX, "https://money.yandex.ru/");
			dictionary.put(System.WEBMONEY,"https://merchant.webmoney.ru/lmi/");
			dictionary.put(System.W1, "http://merchant.w1.ru/checkout/");
			dictionary.put(System.PERFECT, "https://perfectmoney.com/");
			dictionary.put(System.PAXUM, "https://www.paxum.com//payment//");
			dictionary.put(System.TELEMONEY, "https://telemoney.ru/");
		}
	}
}
