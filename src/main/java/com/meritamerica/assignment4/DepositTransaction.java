package com.meritamerica.assignment4;
//package com.meritamerica.assignment4;

public class DepositTransaction extends Transaction {

	
	DepositTransaction(BankAccount targetAccount, double amount)
	{
		this.targetAccount = targetAccount;
		this.amount = amount;
	}
	
	// TODO Review this throw NegativeAmountException, ExceedsFraud
	public void process() throws NegativeAmountException,  ExceedsFraudSuspicionLimitException
	{
		if (amount < 0) 
		{
			throw new NegativeAmountException("Sorry, you can't deposit a negative amount into this account.");
		}
		else if (amount > 1000)
		{
			throw new ExceedsFraudSuspicionLimitException("I'm sorry, we cannot complete this transaction at this time");
		}
		// this one doesn't work
		else
		{
			targetAccount.deposit(amount);
		}
	}
}