package com.meritamerica.assignment4;
//package com.meritamerica.assignment4;

import java.io.*;
//import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.*;

public class MeritBank {

	static FraudQueue fraud;
	static String[] accountData = new String[24];
	static long accountNumber;
	static AccountHolder[] accountHolders;
	static CDOffering[] CDOfferingsArray = new CDOffering[0];

	static boolean readFromFile(String fileName) {
		clearCDOfferings();
		accountHolders = new AccountHolder[0];

		try {
			BufferedReader rd = new BufferedReader(new FileReader(fileName));
			System.out.println("MeritBank.java");
			System.out.println(fileName);
// read account number
			long num = Long.parseLong(rd.readLine().trim());
			System.out.println("\nNext Account: " + num);
			setNextAccountNumber(num);

// read number of CD Offerings in an Array
			int cdo = Integer.parseInt(rd.readLine().trim());
			System.out.println("CD offerings: " + cdo);
			CDOffering[] cdoArr = new CDOffering[cdo];
			for (int x = 0; x < cdoArr.length; x++) {
				cdoArr[x] = CDOffering.readFromString(rd.readLine().trim());
			}
			setCDOfferings(cdoArr);

// reads number of account holders
			int acctHolder = Integer.parseInt(rd.readLine().trim());
			System.out.println("Account Holders: " + acctHolder);
			AccountHolder[] acArr = new AccountHolder[acctHolder];
			for (int x = 0; x < acArr.length; x++) {

// reads account holder info
				acArr[x] = AccountHolder.readFromString(rd.readLine().trim());

// reads number of checking accounts
				int numChk = Integer.parseInt(rd.readLine().trim());
				for (int c = 0; c < numChk; c++) {

// making our checking accounts
					CheckingAccount chk = CheckingAccount.readFromString(rd.readLine().trim());
					acArr[x].addCheckingAccount(chk);

// reads checking account transactions
					int numTran = Integer.parseInt(rd.readLine().trim());
					for (int t = 0; t < numTran; t++) {

// making our checking account transactions
						Transaction tran = Transaction.readFromString(rd.readLine().trim());
						acArr[x].checkingArray.get(c).addTransaction(tran);
					}
				}

// Savings Account 				
				int numSav = Integer.parseInt(rd.readLine().trim());
				for (int s = 0; s < numSav; s++) {
					SavingsAccount sav = SavingsAccount.readFromString(rd.readLine().trim());
					acArr[x].addSavingsAccount(sav);
					int numTran = Integer.parseInt(rd.readLine().trim());
					for (int t = 0; t < numTran; t++) {
						Transaction tran = Transaction.readFromString(rd.readLine().trim());
						acArr[x].savingsArray.get(s).addTransaction(tran);
					}
				}
// CD Account
				int numCD = Integer.parseInt(rd.readLine().trim());
				for (int d = 0; d < numCD; d++) {
					CDAccount cdA = CDAccount.readFromString(rd.readLine().trim());
					acArr[x].addCDAccount(cdA);
					int numTran = Integer.parseInt(rd.readLine().trim());
					for (int t = 0; t < numTran; t++) {
						Transaction tran = Transaction.readFromString(rd.readLine().trim());
						acArr[x].cdAccountArray.get(d).addTransaction(tran);
					}
				}
				addAccountHolder(acArr[x]);
			}
			rd.close();
		} catch (IOException e) {
			return false;
		} catch (ParseException e) {

			return false;
		} catch (java.lang.NumberFormatException e) {
			return false;
		} catch (java.lang.Exception e) {
			return false;
		}

		return true;
	}

	static boolean writeToFile(String fileName) {
		// TODO Should also write BankAccount transactions and the FraudQueue
		return false;
	}

	static void addAccountHolder(AccountHolder accountHolder) {
		AccountHolder[] temp = Arrays.copyOf(accountHolders, accountHolders.length + 1);
		accountHolders = temp;
		accountHolders[accountHolders.length - 1] = accountHolder;
	}

	static AccountHolder[] getAccountHolders() {
		return accountHolders;
	}

	static CDOffering[] getCDOfferings() {
		return CDOfferingsArray;
	}

	static CDOffering getBestCDOffering(double depositAmount) {
		double bestValue = 0.0;
		CDOffering bestOfferingAvailable = null;
		if (CDOfferingsArray == null) {
			return null;
		} else {
			for (CDOffering offering : CDOfferingsArray) {
				if (futureValue(depositAmount, offering.getInterestRate(), offering.getTerm()) > bestValue) {
					bestOfferingAvailable = offering;
					bestValue = futureValue(depositAmount, offering.getInterestRate(), offering.getTerm());
				}
			}
		}
		return bestOfferingAvailable;

	}

	static CDOffering getSecondBestCDOffering(double depositAmount) {
		CDOffering secondBest = null;
		double bestValue = 0.0;
		CDOffering bestOfferingAvailable = null;
		if (CDOfferingsArray == null) {
			return null;
		} else {
			for (CDOffering offering : CDOfferingsArray) {
				if (futureValue(depositAmount, offering.getInterestRate(), offering.getTerm()) > bestValue) {
					secondBest = bestOfferingAvailable;
					bestOfferingAvailable = offering;
					bestValue = futureValue(depositAmount, offering.getInterestRate(), offering.getTerm());
				}
			}
		}
		return secondBest;
	}

	static void clearCDOfferings() {
		CDOfferingsArray = null;
	}

	static void clearAccountHolders() {
		accountHolders = null;
	}

	static void setCDOfferings(CDOffering[] offerings) {
		CDOfferingsArray = offerings;
	}

	static long getNextAccountNumber() {
		return accountNumber;
	}

	static void setNextAccountNumber(long nextAccountNumber) {

		accountNumber = nextAccountNumber;
	}

	static double totalBalances() {
		double totalBalance = 0.0;
		for (AccountHolder accountHolder : accountHolders) {
			totalBalance += accountHolder.getCombinedBalance();
		}
		return totalBalance;
	}

	static double futureValue(double presentValue, double interestRate, int term) {
		double futureValue = recursiveFutureValue(presentValue, term, interestRate);
		// double futureValue = presentValue* Math.pow((1+ interestRate),term);
		return futureValue;
	}

	static AccountHolder[] sortAccountHolders() {
		Arrays.sort(accountHolders);

		return accountHolders;
	}

	/**
	 * Existing futureValue methods that used to call Math.pow() should now call the
	 * following method: recursiveFutureValue()
	 * 
	 * @param amount
	 * @param years
	 * @param interestRate
	 * @return
	 */
	public static double recursiveFutureValue(double balance, int years, double interestRate) {
//		double interRate = 0.01;
//		public static double recursiveFutureValue(double balance, double intr, int term){
		if (years == 0)
			return balance;
		else {
			System.out.println("\nRam's fix: ");
			System.out.print(years + "  " + balance);
			balance = balance * (1 + interestRate);
			System.out.println("  " + balance);
			return recursiveFutureValue(balance, --years, interestRate);
		}
	}
//			years--;
//			balance = balance + recursiveFutureValue(balance, years, interestRate);
//			balance = balance + (balance * interestRate * years);

//			System.out.println("Balance: " + balance);
//			System.out.println("Years: " + years);
//			System.out.println("intRate: " + interestRate);

//			return (1+interestRate)*recursiveFutureValue(balance, years-1, interestRate);

//		return balance;
//		

	public static boolean processTransaction(Transaction transaction)
			throws NegativeAmountException, ExceedsAvailableBalanceException, ExceedsFraudSuspicionLimitException {

		try {
			transaction.process();
		} catch (NegativeAmountException e1) {
			throw e1;

		} catch (ExceedsAvailableBalanceException e2) {
			throw e2;

		} catch (ExceedsFraudSuspicionLimitException e3) {
			throw e3;

		}
		return true;
	}

	public static FraudQueue getFraudQueue() {
		return fraud;
	}

	public static BankAccount getBankAccount(long accountId) {

		if (accountId == 0) {

			for (AccountHolder a : MeritBank.getAccountHolders()) {
				for (BankAccount b : a.getCheckingAccounts()) {
					if (b.getAccountNumber() == accountId)
						return b;
				}
				for (BankAccount b : a.getSavingsAccounts()) {
					if (b.getAccountNumber() == accountId)
						return b;
				}
				for (BankAccount b : a.getCDAccounts()) {
					if (b.getAccountNumber() == accountId)
						return b;
				}
			}
//			return null;
		}
		return null;

	}

}
