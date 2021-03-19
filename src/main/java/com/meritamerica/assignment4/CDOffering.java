package com.meritamerica.assignment4;
//package com.meritamerica.assignment4;

import java.util.ArrayList;
import java.util.Arrays;

public class CDOffering {
	
	double interestRate;
	int terms;
	
	public CDOffering(int terms, double interestRate){
		this.terms = terms;
		this.interestRate = interestRate;
	}

	int getTerm(){
		return terms;
	}
	
	double getInterestRate(){
		return interestRate;
	}
	
	static CDOffering readFromString(String cdOfferingDataString)
	{
		CDOffering cdo;
		
		try
		{
			ArrayList<String> x = new ArrayList<>(Arrays.asList(cdOfferingDataString.split(",")));
			int terms = Integer.parseInt(x.get(0));
			double ir = Double.parseDouble(x.get(1));
			cdo = new CDOffering(terms, ir);
		}
		catch(Exception ex)
		{
			throw new java.lang.NumberFormatException();
		}
		
		return cdo;
	}

}