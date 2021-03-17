
package com.meritamerica.assignment3;

import java.text.*;

public class CDOffering 
{
	CDOffering cdOffering = CDOffering.readFromString();
	private int term;
	private double interestRate;
	
	CDOffering(int term, double interestRate)
	{
		this.term = term;
		this.interestRate = interestRate;
	}
	
	private static CDOffering readFromString() {
		// TODO Auto-generated method stub
		return null;
	}

	int getTerm(){return term;}
	
	double getInterestRate(){return interestRate;}
	
	// ---------------------- FINISH ------------//   		//expecting like this: 1,0.018

	public static CDOffering readFromString(String cdOfferingDataString) throws java.lang.NumberFormatException{
		CDOffering cd = null;

		if(cdOfferingDataString.indexOf(',')!=-1) {
			int term = Integer.parseInt(cdOfferingDataString.substring(0, cdOfferingDataString.indexOf(',')));  
			double rate = Double.parseDouble(cdOfferingDataString.substring(cdOfferingDataString.indexOf(',')+1));
			cd = new CDOffering(term,rate);
		} 
		else {
			throw new NumberFormatException();
		}

		return cd;
	}

	String writeToString()
	{
		String tempTerm = String.valueOf(term), tempIntRate = String.valueOf(interestRate);		
		return tempTerm + "," + tempIntRate;
	}

}
