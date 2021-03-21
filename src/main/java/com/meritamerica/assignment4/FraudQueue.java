package com.meritamerica.assignment4;
//package com.meritamerica.assignment4;

import java.util.*;

public class FraudQueue {
	
	FraudQueue(){}
	
	
	static ArrayList<Transaction> fraud = new ArrayList<Transaction>();
	
	
//	public static void addTransaction(double amount)
//	{
//		fraud.addTransaction(amount);
//	}
//	
	public static Transaction getTransaction()
	{
		Transaction fraudTransaction = fraud.get(0);
		fraud.remove(0);
		return fraudTransaction;
	}
	

}