package com.currencyCall;


import java.util.HashMap;

import com.mashape.unirest.http.Unirest;
import com.google.gson.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;



public class Main {
	
	

	public static void main(String[] args) {
		GetCurrencyFactory currencyFactory= new GetCurrencyFactory();
		try {
		System.out.print("Enter the name of the Provider used for currency Generation: ");  
	    BufferedReader br=new BufferedReader(new InputStreamReader(System.in)); 
	     
	    String planName=br.readLine();
	    Converter converter=currencyFactory.getConverter(planName);
		if(converter.isNull() ) {
			System.out.println("Provider does not exist.");
			return;
		}
		
		HashMap<String, Double> currencyMap= converter.getRates();
		System.out.print("Enter the amount to be converted : "); 
		Double amount= doubleVal(br.readLine());
		
		System.out.print("Enter the current currency : "); 
		String curr1=br.readLine();
		
		System.out.print("Enter the currency to be converted to : "); 
		String curr2=br.readLine();
		
		br.close();
		
		System.out.println("The result is: "+convertCurrency(amount,curr1,curr2,currencyMap));
		}catch( Exception e) {
			System.out.println(e);
		}
		
		

		
	}
	
	protected static String[] arguementValidation(String[] args) {
		
		if(args.length == 0){
			System.out.println("Since there are no default value, we will use 1 US dollar to AUS.");
			String[] newArgs=  new String[3];
			newArgs[0]="1";
			newArgs[1]="USD";
			newArgs[2]="AUD";
			return newArgs;
		}else if(args.length > 3){
			System.out.println("Will only use the first three arguments");
		}
		try {
			 Double doub = Double.parseDouble(args[0]);
		 }catch (NumberFormatException ex) {
	         System.out.println("Given String is not parsable to double, defaulting value to 1");
	         args[0] = "1";
	      }
		
		
		
		return args;
	}
	
	protected static double doubleVal(String num) {
		try {
		 return Double.parseDouble(num);
		}catch(Exception e) {
			System.out.println("Input is not a double. Defaulting to 1.");
			return 1.0;
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
				System.out.println("ID: "+ strID +" rate: " + rate);
				toReturn.put(strID,rate);
			}
		}
		toReturn.put("USD",1.0);
		return toReturn;
		
	}
	
	protected static Double convertCurrency(String[] args, HashMap<String, Double> currencyMap ) {
		Double result=0.0;
		
		try {
			System.out.println(args[0]+" "+args[1]+" "+args[2]);
			System.out.println(currencyMap.get(args[1])+" "+currencyMap.get(args[2]));
			result = Double.parseDouble(args[0])/ currencyMap.get(args[1]) * currencyMap.get(args[2]);
		}catch(Exception e) {
			System.out.println("Argument Error. please check for valid arguments");
			System.out.println(e);
		}
		
		return result;
	}
	
	protected static Double convertCurrency(Double amount,String curr1,String curr2, HashMap<String, Double> currencyMap ) {
		Double result=0.0;
		
		try {
			
			result = amount/ currencyMap.get(curr1) * currencyMap.get(curr2);
		}catch(Exception e) {
			System.out.println("Argument Error. please check for valid arguments");
			System.out.println(e);
		}
		
		return result;
	}

}
