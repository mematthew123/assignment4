package com.meritamerica.assignment4;
//package com.meritamerica.assignment4;

public class WithdrawTransaction extends Transaction{
	
	WithdrawTransaction(BankAccount targetAccount, double amount)
	{
		this.targetAccount = targetAccount;
		this.amount = amount;
	}
	
	public void process() throws NegativeAmountException, ExceedsAvailableBalanceException, ExceedsFraudSuspicionLimitException
	{
		if (amount < 0) 
		{
			throw new NegativeAmountException("Please enter a positive dollar amount.");
		}
		else if (amount > 1000)
		{
//			FraudQueue.addTransaction(amount);
			throw new ExceedsFraudSuspicionLimitException("Your transaction will be processed once it is cleared.");
		}
		else if (targetAccount.getBalance() < amount)
		{
			throw new ExceedsAvailableBalanceException("That's way too much money, buster");
		}
		else
		{
			targetAccount.withdraw(amount);
		}
	}
}