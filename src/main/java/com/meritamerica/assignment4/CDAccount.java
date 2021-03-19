package com.meritamerica.assignment4;
//package com.meritamerica.assignment4;

import java.text.*;
import java.util.*;

//import chex.meritamerica.assignment4.CDOffering;
//import chex.meritamerica.assignment4.MeritBank;

//import com.meritamerica.assignment4.CDOffering;
//import com.meritamerica.assignment4.MeritBank;

public class CDAccount extends BankAccount {

	CDOffering offerings;
	int term;

	public CDAccount(CDOffering offerings, double balance) {
		super(balance, offerings.interestRate);
		this.offerings = offerings;

	}

	public CDAccount(long accountNumber, double balance, double interestRate, java.util.Date dateOpenedOn, int term) {
		super(accountNumber, balance, interestRate, dateOpenedOn);
		this.term = term;

	}

	public boolean withdraw(double amount) {
		return false;
	}

	public boolean deposit(double amount) {
		return false;
	}

	public long getAccountNumber() {
		return accountNumber;

	}

	int getTerm() {
		return term;
	}

	public double futureValue() throws NegativeAmountException, ExceedsFraudSuspicionLimitException {
		
		double recInterest = offerings.getInterestRate() + 1;
		double recAmmount;
		for (int i = 0; i < (offerings.getTerm()-1); i++) {
			recInterest = recInterest * (offerings.getInterestRate() + 1);
		}
		

		recAmmount = recInterest * balance;
		return recAmmount;
	}

	static CDAccount readFromString(String accountData) throws ParseException {
		CDAccount cda;

		try {
			ArrayList<String> x = new ArrayList<>(Arrays.asList(accountData.split(",")));
			long acNum = Long.parseLong(x.get(0));
			double b = Double.parseDouble(x.get(1));
			double ir = Double.parseDouble(x.get(2));
			Date date = formatter.parse(x.get(3));
			int term = Integer.parseInt(x.get(4));
			cda = new CDAccount(acNum, b, ir, date, term);
		} catch (ParseException ex) {
			throw new java.lang.NumberFormatException();
		}
		return cda;
	}

}