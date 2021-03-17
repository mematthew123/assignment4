package com.meritamerica.assignment3;



import java.io.BufferedReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class AccountHolder implements Comparable<AccountHolder>
{
	/*INSTANCE VARIABLES*/
	
	private static final double BALANCE_LIMIT = 250000;
	private String firstName;
	private String middleName;
	private String lastName;
	private String ssn;
	protected static BufferedReader br;
	private CheckingAccount[] checkingAccountList = new CheckingAccount[10];
	private SavingsAccount[] savingsAccountList = new SavingsAccount[10];
	private CDAccount[] cdAccountList = new CDAccount[10];
	
		
	/*METHODS*/	
	AccountHolder(
			String firstName,
			String middleName,
			String lastName,
			String ssn			
			)
	{
		this.firstName = firstName;		
		this.middleName = middleName;
		this.lastName = lastName;
		this.ssn = ssn;
		
	}
		
	/* GETTERS/SETTERS */
	String getFirstName(){return firstName;}
	void setFirstName(String firstName){this.firstName = firstName;}
	
	String getMiddleName(){return middleName;}
	void setMiddleName(String middleName){this.middleName = middleName;}
	
	String getLastName(){return lastName;}
	void setLastName(String lastName){this.lastName = lastName;}
	
	String getSSN(){return ssn;}
	void setSSN(String ssn){this.ssn = ssn;}
	
	double getCombinedBalance(){return getCheckingBalance() + getSavingsBalance() + getCDBalance();}
	
	/* CHECKING ACCOUNT */
	CheckingAccount addCheckingAccount(double openingBalance)
	{
		if((openingBalance + (getCombinedBalance() - getCDBalance()) < BALANCE_LIMIT))
		{
			return addCheckingAccount(new CheckingAccount(openingBalance));
		}		
		return null;
	}
	
	CheckingAccount addCheckingAccount(CheckingAccount checkingAccount)
	{
		if((checkingAccount.getBalance() + (getCombinedBalance() - getCDBalance()) < BALANCE_LIMIT))
		{
			for(int i = 0; i < checkingAccountList.length; i++)
			{
				if(checkingAccountList[i] == null)
				{
					checkingAccountList[i] = checkingAccount;
					break;
				}
			}
		}
		return checkingAccount;
	}
	
	CheckingAccount[] getCheckingAccounts() 
	{
		return checkingAccountList;
	}
	
	int getNumberOfCheckingAccounts()
	{
		int numOfAccounts = 0;
		for(CheckingAccount i: checkingAccountList)
		{
			if(i != null)
			{
				numOfAccounts++;
			}
		}
		return numOfAccounts;
	}
	
	double getCheckingBalance()
	{
		double sum = 0;
		for(int i = 0; i < checkingAccountList.length + 1; i++)
		{
			if(checkingAccountList[i] == null)
			{
				break;
			}
			sum += checkingAccountList[i].getBalance();
		}
		return sum;
	}
	
	/* SAVINGS ACCOUNT */
	SavingsAccount addSavingsAccount(double openingBalance)
	{
		if((openingBalance + (getCombinedBalance() - getCDBalance()) < BALANCE_LIMIT))
				{
					return addSavingsAccount(new SavingsAccount(openingBalance));
				}		
		return null;
	}	
	
	SavingsAccount addSavingsAccount(SavingsAccount savingsAccount)
	{
		if((savingsAccount.getBalance() + (getCombinedBalance() - getCDBalance()) < BALANCE_LIMIT))
		{
			for(int i = 0; i < savingsAccountList.length; i++)
			{
				if(savingsAccountList[i] == null)
				{
					savingsAccountList[i] = savingsAccount;
					break;
				}
			}
		}
		return savingsAccount;
	}
	
	SavingsAccount[] getSavingsAccounts()
	{
		return savingsAccountList;
	}
	
	int getNumberOfSavingsAccounts()
	{
		int numOfAccounts = 0;
		for(SavingsAccount i: savingsAccountList)
		{
			if(i != null)
			{
				numOfAccounts++;
			}
		}
		return numOfAccounts;
	}
	
	double getSavingsBalance()
	{
		double sum = 0;
		for(int i = 0; i < savingsAccountList.length + 1; i++)
		{
			if(savingsAccountList[i] == null)
			{
				break;
			}
			sum += savingsAccountList[i].getBalance();
		}
		return sum;
	}
	
	/* CD ACCOUNT */
	CDAccount addCDAccount(CDOffering offering, double openingBalance)
	{
		if(MeritBank.getCDOfferings() == null)
		{
			return null;
		} else {
		return addCDAccount(new CDAccount(offering, openingBalance));
		}


	}
	
	CDAccount addCDAccount(CDAccount cdAccount)
	{
		if(cdAccountList == null)
		{
			return null;
		}
		for(int i = 0; i < cdAccountList.length; i++)
		{
			if(cdAccountList[i] == null)
			{
				cdAccountList[i] = cdAccount;
				break;
			}
		}
		return cdAccount;
	}
	
	CDAccount[] getCDAccounts()
	{
		return cdAccountList;
	}
	
	int getNumberOfCDAccounts()
	{
		int numOfAccounts = 0;
		for(CDAccount i: cdAccountList)
		{
			if(i != null)
			{
				numOfAccounts++;
			}			
		}
		return numOfAccounts;
	}
	
	double getCDBalance()
	{
		double sum = 0;
		for(int i = 0; i < cdAccountList.length + 1; i++)
		{
			if(cdAccountList[i] == null)
			{
				break;
			}
			sum += cdAccountList[i].getBalance();
		}
		return sum;
	}
	// ------------------- FINISH -------------//
	public static AccountHolder readFromString(String accountHolderData) throws java.lang.Exception{
		StringTokenizer tokenizer = new StringTokenizer(accountHolderData, ",");

		String firstName = tokenizer.nextToken();
		String middleName = "";
		if (tokenizer.countTokens() == 4) {
			middleName = tokenizer.nextToken();
		}
		String lastName = tokenizer.nextToken();
		String ssn = tokenizer.nextToken();

		AccountHolder account = new AccountHolder(firstName, middleName, lastName, ssn);
		return account;
	}
	
	public String writeToString() {
		String acString = getFirstName()+","+getMiddleName()+","+getLastName()+","+getSSN();
		return acString;
	}

	@Override
	public int compareTo(AccountHolder o) {
		// TODO Auto-generated method stub
		return 0;
	}
	//ArrayList is created for storing (1) all the savings account,(2) all the checking account,(3)all CDA account of an account holder
	private ArrayList<SavingsAccount> savingsAccList = new ArrayList<SavingsAccount>();
	private ArrayList<CheckingAccount> checkingAccList = new ArrayList<CheckingAccount>();
	private ArrayList<CDAccount> cdAccList = new ArrayList<CDAccount>();

	
	}
