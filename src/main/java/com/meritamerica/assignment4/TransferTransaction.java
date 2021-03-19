package com.meritamerica.assignment4;
//package com.meritamerica.assignment4;

public class TransferTransaction extends Transaction{
	TransferTransaction(BankAccount sourceAccount, BankAccount targetAccount, double amount)
	{
		this.sourceAccount = sourceAccount;
		this.targetAccount = targetAccount;
		this.amount = amount;
	}
	
	public void process() throws NegativeAmountException, ExceedsAvailableBalanceException, ExceedsFraudSuspicionLimitException
	{
		if(amount < 0)
		{
			throw new NegativeAmountException("positive, man");
		}
		else if (amount > 1000)
		{
			throw new ExceedsFraudSuspicionLimitException("Looking mighty suspect there, pardner");
		}	
		else
		{
			sourceAccount.withdraw(amount);
			targetAccount.deposit(amount);
		}
	}
}