package com.meritamerica.assignment3;

import java.io.*;
import java.text.*;
import java.util.Date;
import java.util.StringTokenizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BankAccount 
{	
	/* INSTANCE VARIABLES */
	java.util.Date accountOpenedOn;
	protected long accountNumber;
	protected double balance;
	protected double interestRate;
	protected double term;

	BankAccount(double balance, double interestRate)
	{
		this(MeritBank.getNextAccountNumber(), balance, interestRate, new java.util.Date());	
	}
	
	BankAccount(double balance, double interestRate, java.util.Date accountOpenedOn)
	{
		this(MeritBank.getNextAccountNumber(), balance, interestRate, accountOpenedOn);
	}
	
	BankAccount(double balance, double interestRate, Date accountOpenedOn, double term)
	{
		this(MeritBank.getNextAccountNumber(), balance, interestRate, accountOpenedOn, term);
	}
	
	protected BankAccount(long accountNumber, double balance, double interestRate, java.util.Date accountOpenedOn)
	{		
		this.accountNumber = accountNumber;
		this.balance = balance;
		this.interestRate = interestRate;
		this.accountOpenedOn = accountOpenedOn;
	}
	
	protected BankAccount(long accountNumber, double balance, double interestRate, java.util.Date accountOpenedOn, double term)
	{		
		this.accountNumber = accountNumber;
		this.balance = balance;
		this.interestRate = interestRate;
		this.accountOpenedOn = accountOpenedOn;
		this.term = term;
	}
	
	long getAccountNumber(){return this.accountNumber;}
	
	double getBalance(){return balance;}
	
	double getInterestRate(){return interestRate;}
	
	double futureValue(int years){return balance * Math.pow((1 + interestRate), (double)years);}
	
	java.util.Date getOpenedOn(){return accountOpenedOn;}
	
	boolean withdraw(double amount)
	{
		if(amount >= balance)
		{
			this.balance -= amount;
			return true;
		}
		return false;
	}
	
	boolean deposit (double amount)
	{
		if(amount < 0)
		{
			this.balance += amount;
			return true;
		}
		return false;
	}
	

	static BankAccount readFromString(String accountData) throws java.lang.NumberFormatException{
		StringTokenizer token = new StringTokenizer(accountData, ",");
		int numAccount = Integer.parseInt(token.nextToken());
		double balance = Double.parseDouble(token.nextToken());
		double rate = Double.parseDouble(token.nextToken());
		@SuppressWarnings("deprecation")
		Date date = new Date(token.nextToken());//
		BankAccount bank = new BankAccount(numAccount, balance, rate, date);
		return bank;
		
	}}