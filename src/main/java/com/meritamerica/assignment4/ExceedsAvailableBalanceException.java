package com.meritamerica.assignment4;
//package com.meritamerica.assignment4;

@SuppressWarnings("serial")
public class ExceedsAvailableBalanceException extends Exception {
	public ExceedsAvailableBalanceException(String errorMessage)
	{
		super(errorMessage);
	}

}