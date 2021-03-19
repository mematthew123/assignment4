package com.meritamerica.assignment4;
//package com.meritamerica.assignment4;

import java.util.ArrayList;
import java.util.Arrays;

public class AccountHolder implements Comparable<AccountHolder> {
	// Class variables
	private String firstName;
	private String middleName;
	private String lastName;
	private String ssn;
	ArrayList<CheckingAccount> checkingArray = new ArrayList<CheckingAccount>();
	ArrayList<SavingsAccount> savingsArray = new ArrayList<SavingsAccount>();
	ArrayList<CDAccount> cdAccountArray = new ArrayList<CDAccount>();

	/**
	 * This constructs account holder's attributes
	 * 
	 * @param first  the first name of the account holder
	 * @param middle the middle name of the account holder
	 * @param last   the last name of the account holder
	 * @param ssn    the social security number of the account holder
	 */
	public AccountHolder(String first, String middle, String last, String ssn) {
		this.firstName = first;
		this.middleName = middle;
		this.lastName = last;
		this.ssn = ssn;
	}

	/**
	 * This method validates that aggregate account balances are less than
	 * $250,000.00 and add checking account to account holder by taking opening
	 * balance as parameter
	 * 
	 * @param openingBalance Account holder's initial opening balance
	 * @return
	 * @throws Exception 
	 */
	public CheckingAccount addCheckingAccount(double openingBalance) throws ExceedsCombinedBalanceLimitException {

		try {
			if (openingBalance >=250000) {
				throw ExceedsCombinedBalanceLimitException("Total deposit exceeds the $250,000 cap.");
			}
			
			if (getCheckingBalance() + getSavingsBalance() + openingBalance >= 250000) {
				throw ExceedsCombinedBalanceLimitException("Combined balance limit exeeds $250,000.");
			}
		
			CheckingAccount chk = new CheckingAccount(0, .0001);
			DepositTransaction depositTransaction = new DepositTransaction(chk, openingBalance);
			MeritBank.processTransaction(depositTransaction);
			return chk;
	
		} catch (ExceedsCombinedBalanceLimitException e) {

		} catch (Exception e) {
			System.out.println("Your combined balance limit has been reached.");
		}
		CheckingAccount chk = new CheckingAccount(0, .0001);
		return chk;
	}


	/**
	 * This method used to add checking account to account holder by validating
	 * aggregate balances are less than $250,000.00
	 * 
	 * @param checkingAccount checking account amount
	 */
	public CheckingAccount addCheckingAccount(CheckingAccount checkingAccount) throws ExceedsCombinedBalanceLimitException {
		
		try {
			if (checkingAccount.getBalance() + getCheckingBalance() + getSavingsBalance() >= 250000) {
				System.out.println("Unable to open Checking Account. Combined balance exceeds the $250,000 cap.");
				throw ExceedsCombinedBalanceLimitException("Total deposit exceeds the $250,000 cap.");
			}
			checkingArray.add(checkingAccount);
				
		} catch (ExceedsCombinedBalanceLimitException e) {
			System.out.println("Reached combined balance limit.");
		
		} catch (Exception e) {
			System.out.println("Reached combined balance limit.");
		}
		return checkingAccount;
	}

	/**
	 * This method calculates the aggregate balance of checking accounts
	 * 
	 * @return the total amount of the checking accounts
	 */
	public double getCheckingBalance() {
		double total = 0.0;
		int i;
		for (i = 0; i < checkingArray.size(); i++) {
			total += checkingArray.get(i).getBalance();
		}
		return total;
	}

	/**
	 * This method adds savings account to account holder by validating the
	 * aggregate balance is less than $250,000.00
	 * 
	 * @param openingBalance the initial opening balance of the saving account
	 */
	public SavingsAccount addSavingsAccount(double openingBalance) throws ExceedsCombinedBalanceLimitException {
//			DepositTransaction depositTransaction = new DepositTransaction(null, 1000);

		try {
			if (openingBalance >=250000) {
				throw ExceedsCombinedBalanceLimitException("Total deposit exceeds the $250,000 cap.");
			}

			if (getCheckingBalance() + getSavingsBalance() + openingBalance >= 250000) {
				System.out.println("Combined balance limit exeeds $250,000.");
				throw ExceedsCombinedBalanceLimitException("Combined balance limit exeeds $250,000.");
			}
			
			SavingsAccount sav = new SavingsAccount(0, .0001);
			DepositTransaction depositTransaction = new DepositTransaction(sav, openingBalance);
			MeritBank.processTransaction(depositTransaction);
			return sav;
			
		} catch (ExceedsCombinedBalanceLimitException e) {

		} catch (Exception e) {
			System.out.println("Your combined balance limit has been reached.");
		}
		SavingsAccount sav = new SavingsAccount(openingBalance, .01);
		return sav;
	}

	/**
	 * This method validates account balance is less than $250,00.00 and adds
	 * savings account to account holder
	 * 
	 * @param savingsAccount the savings account amount
	 */
	public SavingsAccount addSavingsAccount(SavingsAccount savingsAccount) throws ExceedsCombinedBalanceLimitException {

		try {
			if (savingsAccount.getBalance() + getCheckingBalance() + getSavingsBalance() >= 250000) {
				System.out.println("Unable to open Savings Account. Combined balance exceeds the $250,000 cap.");
				throw ExceedsCombinedBalanceLimitException("Total deposit exceeds the $250,000 cap.");
			}
			savingsArray.add(savingsAccount);
			
		} catch (ExceedsCombinedBalanceLimitException e) {
			System.out.println("Reached combined balance limit.");
	
		} catch (Exception e) {
			System.out.println("Reached combined balance limit.");
		}
		return savingsAccount;
	}

	/**
	 * @return the total balance in savings accounts
	 */
	public double getSavingsBalance() {
		double total = 0.0;
		for (SavingsAccount balance : savingsArray) {
			total += balance.getBalance();
		}
		return total;

	}

	/**
	 * 
	 * @return savings account
	 */
	public ArrayList<SavingsAccount> getSavingsAccounts() {
		return savingsArray;
	}

	/**
	 * @return the total number of savings accounts
	 */
	public int getNumberOfSavingsAccounts() {
		return savingsArray.size();
	}

	/**
	 * This method used to add CDAccount to the account holder
	 * 
	 * @param offering    the CDOffering
	 * @param openingBalance the opening balance
	 * @return
	 * @throws ExceedsFraudSuspicionLimitException
	 */
	public CDAccount addCDAccount(CDOffering offering, double openingBalance) throws ExceedsFraudSuspicionLimitException {
		if (openingBalance > 1000) {
			throw new ExceedsFraudSuspicionLimitException("Exceeds balance limit.");
		}

		CDAccount newA = new CDAccount(offering, openingBalance);
		return newA;
	}

	public CDAccount addCDAccount(CDAccount cdAccount) {
		cdAccountArray.add(cdAccount);
		return cdAccount;
	}

	private Exception ExceedsCombinedBalanceLimitException(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	public ArrayList<CDAccount> getCDAccounts() {
		return cdAccountArray;
	}

	public int getNumberOfCDAccounts() {
		return cdAccountArray.size();
	}

	public double getCDBalance() {
		double total = 0.0;
		for (CDAccount balance : cdAccountArray) {
			total += balance.getBalance();
		}
		return total;
	}

	public double getCombinedBalance() {
		return getCDBalance() + getSavingsBalance() + getCheckingBalance();
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String first) {
		this.firstName = first;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middle) {
		this.middleName = middle;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String last) {
		this.lastName = last;
	}

	public String getSSN() {
		return ssn;
	}

	public void setSSN(String ssn) {
		this.ssn = ssn;
	}

	public ArrayList<CheckingAccount> getCheckingAccounts() {
		return checkingArray;
	}

	public int getNumberOfCheckingAccounts() {
		return checkingArray.size();
	}

	static AccountHolder readFromString(String accountHolderData) throws Exception {
		AccountHolder ah;
		try {
			ArrayList<String> x = new ArrayList<>(Arrays.asList(accountHolderData.split(",")));
			ah = new AccountHolder(x.get(0), x.get(1), x.get(2), x.get(3));
		} catch (Exception ex) {
			throw new java.lang.Exception();
		}
		return ah;
	}

	public int compareTo(AccountHolder otherAccountHolder) {
		double x = this.getCombinedBalance();
		double y = otherAccountHolder.getCombinedBalance();
		if (x == y) {
			return 0;
		} else if (this.getCombinedBalance() > otherAccountHolder.getCombinedBalance()) {
			return 1;
		} else {
			return -1;
		}

	}

}










