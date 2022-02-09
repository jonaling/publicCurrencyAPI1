package com.currencyCall;

public class GetCurrencyFactory  {
	public Converter getConverter(String converter) {
		if(converter == null) {
			return null;
		}
		if(converter.equalsIgnoreCase("IO")) {
			return new CurrencyIOConverter();
		}
		return null;
	}
}
