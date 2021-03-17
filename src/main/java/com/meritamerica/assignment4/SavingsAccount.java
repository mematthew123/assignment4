package com.meritamerica.assignment3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.*;
import java.util.*;

public class SavingsAccount extends BankAccount
{
	SavingsAccount(double openingBalance)
	{
		super (openingBalance, 0.001);		
	}
	
	SavingsAccount(long accountNumber, double balance, double interestRate, java.util.Date accountOpenedOn)
	{
		super(accountNumber, balance, interestRate, accountOpenedOn);	
	}
	

public static SavingsAccount readFromString(String accountData) throws java.lang.NumberFormatException{
	StringTokenizer token = new StringTokenizer(accountData, ",");
	int numAccount = Integer.parseInt(token.nextToken());
	double balance = Double.parseDouble(token.nextToken());
	double rate = Double.parseDouble(token.nextToken());
	
	Date date = new Date(token.nextToken());
	Format f = new SimpleDateFormat("dd/MM/yy");
	String strDate = f.format(date);
	date = new Date(strDate);

		return new SavingsAccount(numAccount, balance, rate, date);
	}
}