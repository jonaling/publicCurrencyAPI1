package com.currencyCall;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

public abstract class Converter {
	protected double conversion;
	
	abstract HashMap<String, Double> getRates(); 
	
	Boolean isNull() {
		
		if(this != null) {
			return false;
		}else {
			return true;
		}
		
	}
	
	
	String getCredentials(String category) {
		Scanner myScan = null;
		try {
		File credFile = new File ("src/main/resources/Credentials.txt");
		myScan = new Scanner(credFile);
		while(myScan.hasNext()) {
			String line= myScan.nextLine();
			
			if (line.contains(category)) {
				String[] result = line.split("=");
				return result[1];
			}
		}
		
		return "Not found";
		}catch(Exception e) {
			
			e.printStackTrace();
			
			return category;
		}finally {
			myScan.close();
			
		}
		
	}
}
