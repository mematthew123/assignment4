package com.meritamerica.assignment4;
//package com.meritamerica.assignment4;

@SuppressWarnings("serial")
public class NegativeAmountException extends Exception{
	public NegativeAmountException(String errorMessage)
	{
		super(errorMessage);
	}

}