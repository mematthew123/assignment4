package com.meritamerica.assignment4;
//package com.meritamerica.assignment4;

@SuppressWarnings("serial")
public class ExceedsFraudSuspicionLimitException extends Exception{
	public ExceedsFraudSuspicionLimitException(String errorMessage)
	{
		super(errorMessage);
	}

}