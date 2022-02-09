package com.currencyCall;

import java.util.HashMap;

import com.google.gson.Gson;
import com.mashape.unirest.http.Unirest;

public class CurrencyIOConverter extends Converter{

	@Override
	HashMap<String, Double> getRates() {
		HashMap<String, Double> toReturn= new HashMap<String, Double>();
		toReturn.put("USD",1.0);
		
		//System.out.println("getCredentials return: "+getCredentials("currencyIOAccessKey"));
		try {

			String response = Unirest.get("http://api.currencylayer.com/live")
					.queryString("access_key",getCredentials("currencyIOAccessKey"))
					.asString()
					.getBody()
					;
			
			HashMap<String, Object> map = new Gson().fromJson(response, HashMap.class);
			HashMap<String, Double> currencyMap= sortCurrency(map.get("quotes").toString());
			return currencyMap;
		}catch(Exception e) {
			return toReturn;
		}
		
	}
	protected static HashMap<String, Double> sortCurrency(String quotes){
		HashMap<String, Double> toReturn= new HashMap<String, Double>();
		if (quotes.length()>2) {
			quotes= quotes.substring(1,quotes.length()-1);
			String parts[]= quotes.split(",");
			for (String part: parts) {
				String empdata[] = part.split("=");
				String strID=empdata[0].substring(4);
				Double rate= Double.parseDouble(empdata[1]);
				//System.out.println("ID: "+ strID +" rate: " + rate);
				toReturn.put(strID,rate);
			}
		}
		toReturn.put("USD",1.0);
		return toReturn;
		
	}
}
