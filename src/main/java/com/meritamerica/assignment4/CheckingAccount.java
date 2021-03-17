package com.meritamerica.assignment3;

import java.text.*;
import java.util.*;

public class CheckingAccount extends BankAccount
{
	
	CheckingAccount(double openingBalance)
	{
		super(openingBalance, 0.0001);	
	}
	
	CheckingAccount(long accountNumber, double balance, double interestRate, java.util.Date accountOpenedOn)
	{
		super(accountNumber, balance, interestRate, accountOpenedOn);	
	}
	
	//finish// 
	
		
		
	public static CheckingAccount readFromString(String accountData) throws java.lang.NumberFormatException{
		StringTokenizer token = new StringTokenizer(accountData, ",");
		int numAccount = Integer.parseInt(token.nextToken());
		double balance = Double.parseDouble(token.nextToken());
		double rate = Double.parseDouble(token.nextToken());
		Date date = new Date(token.nextToken());
		CheckingAccount checking = new CheckingAccount(numAccount, balance, rate, date);
		return checking;

	}
}

