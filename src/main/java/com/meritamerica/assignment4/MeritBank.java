package com.meritamerica.assignment4;

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

	static AccountHolder[] sortedAccountHolders() {
		Arrays.sort(accountHolders);

		return accountHolders;
	}
	
	static boolean readFromFile(String fileName) {
		clearCDOfferings();
//		accountHolders = new AccountHolder[0];
//		Set<String> stringTransactions = new HashSet<>();
		List<String> stringTransactions = new ArrayList<>();

		try {
			BufferedReader rd = new BufferedReader(new FileReader(fileName));
			System.out.println("MeritBank.java");
			System.out.println(fileName);
// read account number
			long num = Long.parseLong(rd.readLine().trim());
			System.out.println("\nNext Account Number is: " + num);
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
			accountHolders = new AccountHolder[acctHolder];
//			AccountHolder[] acArr = new AccountHolder[acctHolder];

			for (int x = 0; x < accountHolders.length; x++) {
// reads account holder info
				accountHolders[x] = AccountHolder.readFromString(rd.readLine().trim());

// reads number of checking accounts
				int numChk = Integer.parseInt(rd.readLine().trim());
				//addAccountHolder(acArr[x]);
//				System.out.println("Number of Checking Accounts: " + numChk);

				for (int c = 0; c < numChk; c++) {
// add checking accounts
					CheckingAccount chk = CheckingAccount.readFromString(rd.readLine().trim());
					accountHolders[x].addCheckingAccount(chk);

// reads checking account transactions
					int numTran = Integer.parseInt(rd.readLine().trim());
					for (int t = 0; t < numTran; t++) {

// making four checking account transactions: 
// no violation | negative amount | withdrawal exceeds available balance | over $1,000 limit-->fraud 
						stringTransactions.add(rd.readLine().trim());
					}
				}

// Savings Account 				
				int numSav = Integer.parseInt(rd.readLine().trim());
				for (int s = 0; s < numSav; s++) {
					SavingsAccount sav = SavingsAccount.readFromString(rd.readLine().trim());
					accountHolders[x].addSavingsAccount(sav);
					int numTran = Integer.parseInt(rd.readLine().trim());
					for (int t = 0; t < numTran; t++) {
						stringTransactions.add(rd.readLine().trim());
					}
				}
// CD Account
				int numCD = Integer.parseInt(rd.readLine().trim());
				for (int d = 0; d < numCD; d++) {
					CDAccount cdA = CDAccount.readFromString(rd.readLine().trim());
					accountHolders[x].addCDAccount(cdA);
					int numTran = Integer.parseInt(rd.readLine().trim());
					for (int t = 0; t < numTran; t++) {
						stringTransactions.add(rd.readLine().trim());
					}
				}
			}
			rd.close();
			int count = 0;
			for (String stringTransaction : stringTransactions) {
				
				try {
					count++;
					System.out.println(stringTransaction);
					Transaction transaction = Transaction.readFromString(stringTransaction);
					transaction.process();
//					if (count == 7)
//						break;
				} catch (NegativeAmountException nae) {
					nae.printStackTrace();
				} catch (ExceedsAvailableBalanceException eabe) {
					eabe.printStackTrace();
				} catch (ExceedsFraudSuspicionLimitException efsle) {
					efsle.printStackTrace();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
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

		if (years == 0)
			return balance;
		else {
//			System.out.print(years + "  " + balance);
			balance = balance * (1 + interestRate);
//			System.out.println("  " + balance);
			return recursiveFutureValue(balance, --years, interestRate);
		}
	}

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

		if (accountId > 0) {

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
		}
		return null;

	}

}
 