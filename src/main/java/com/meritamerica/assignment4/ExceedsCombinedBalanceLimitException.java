package com.meritamerica.assignment4;
//package com.meritamerica.assignment4;

import java.lang.Exception;

@SuppressWarnings("serial")
public class ExceedsCombinedBalanceLimitException extends Exception{
	public ExceedsCombinedBalanceLimitException(String errorMessage)
	{
		super(errorMessage);
	}

}