package com.interkassa.constants;

import java.util.HashMap;

public class Currency {

	public enum Money {Dollar, Euro, Hryvnia, Ruble, BRuble, Gold /* :O :O :O */, Test};

	private static HashMap <Money, Integer> dictionary;

	public static int getMoneyKey(Money money) {
		fillDictionary();
		return dictionary.get(money);
	}

	private static void fillDictionary () {
		if (dictionary == null) {
			dictionary = new HashMap <Money, Integer>();
			dictionary.put(Money.Dollar, 840);
			dictionary.put(Money.Euro, 978);
			dictionary.put(Money.Hryvnia, 980);
			dictionary.put(Money.Ruble, 643);
			dictionary.put(Money.BRuble, 974);
			dictionary.put(Money.Gold, 959);
			dictionary.put(Money.Test, 963);
		}
	}
}
