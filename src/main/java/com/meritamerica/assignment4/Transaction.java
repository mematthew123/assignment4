package com.meritamerica.assignment4;
//package com.meritamerica.assignment4;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public abstract class Transaction
{
	static FraudQueue fraud;
	BankAccount sourceAccount;
	BankAccount targetAccount;
	double amount;
	Date date;
	boolean isProcessed = false;
	String reason;
	static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	
	public BankAccount getSourceAccount()
	{
		return sourceAccount;
	}
	
	public void setSourceAccount(BankAccount sourceAccount)
	{
		this.sourceAccount = sourceAccount;
	}
	
	public BankAccount getTargetAccount()
	{
		return targetAccount;
	}
	
	public void setTargetAccount(BankAccount targetAccount)
	{
		this.targetAccount = targetAccount;
	}
	
	public double getAmount()
	{
		return amount;
	}
	
	public void setAmount(double amount)
	{
		this.amount = amount;
	}
	
	public java.util.Date getTransactionDate()
	{
		return date;
	}
	
	public void setTransactionDate(java.util.Date date)
	{
		this.date = date;
	}
	
	public String writeToString()
	{
		return "yo";
	}
	
	public static Transaction readFromString(String transactionDataString)
	{
		DepositTransaction dep;
		WithdrawTransaction wit;
		TransferTransaction transfer;
		
		ArrayList<String> x = new ArrayList<>(Arrays.asList(transactionDataString.split(",")));
		
			try {
				long source = Long.parseLong(x.get(0));
				long target = Long.parseLong(x.get(1));
				double amount = Double.parseDouble(x.get(2));
				Date date = formatter.parse(x.get(3));
				if (source < 0 && amount > 0)
				{
					 dep = new DepositTransaction(MeritBank.getBankAccount(target), amount);
					 dep.setTransactionDate(date);
					 return dep;
				}
				if (source < 0 && amount < 0)
				{
					wit = new WithdrawTransaction(MeritBank.getBankAccount(target), amount);
					wit.setTransactionDate(date);
					return wit;
				}
				if(source > 0)
				{
					transfer = new TransferTransaction(MeritBank.getBankAccount(source), MeritBank.getBankAccount(target), amount);
					transfer.setTransactionDate(date);
					return transfer;
				}
				
			}
			catch(ParseException ex) {
				throw new java.lang.NumberFormatException();
			}
		return null;
	}
	
	public abstract void process() throws NegativeAmountException, ExceedsAvailableBalanceException, ExceedsFraudSuspicionLimitException;
	
	public boolean isProcessedByFraudTeam()
	{
		return isProcessed;
	}
	
	public void setProcessedByFraudTeam(boolean isProcessed)
	{
		this.isProcessed = isProcessed;
	}
	
	public String getRejectionReason()
	{
		return reason;
	}
	
	public void setRejectionReason(String reason)
	{
		this.reason = reason;
	}

}