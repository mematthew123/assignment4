package com.meritamerica.assignment3;


import java.io.*;
import java.text.*;
import java.util.*;


public class MeritBank 
{
	 private static final String FILE_NAME = "src/test/testMeritBank_testing";
	private static CDOffering[] listOfCDOffers;
	private static AccountHolder[] listOfAccountHolders;
	private static long nextAccountNumber = 0;
		
	static void addAccountHolder(AccountHolder accountHolder)
	{
		for(int i = 0; i < listOfAccountHolders.length; i++)
		{
			if(listOfAccountHolders[i] == null)
			{
				listOfAccountHolders[i] = accountHolder;
				break;
			}
		}
	}
	
	static AccountHolder[] getAccountHolders(){return listOfAccountHolders;}
	
	static CDOffering[] getCDOfferings(){return listOfCDOffers;}
	
	static CDOffering getBestCDOffering(double depositAmount)
	{	
		if(listOfCDOffers == null) return null;
		double stored = futureValue(depositAmount, listOfCDOffers[0].getInterestRate(), listOfCDOffers[0].getTerm());
		int indexBiggest = 0;
		for(int i = 1; i < listOfCDOffers.length; i++)
		{
			double tempStored = futureValue(depositAmount, listOfCDOffers[i].getInterestRate(), listOfCDOffers[i].getTerm());
			if(tempStored > stored)
			{
				stored = tempStored;
				indexBiggest = i;
			}				
		}		
		return listOfCDOffers[indexBiggest];
	}
	
	static CDOffering getSecondBestCDOffering(double depositAmount)
	{
		if(listOfCDOffers == null) return null;
		double biggest = futureValue(depositAmount, listOfCDOffers[0].getInterestRate(), listOfCDOffers[0].getTerm());
		double secondBiggest = futureValue(depositAmount, listOfCDOffers[0].getInterestRate(), listOfCDOffers[0].getTerm());
		int indexBiggest = 0;
		int indexSecondBiggest = 0;
		for(int i = 1; i < listOfCDOffers.length; i++)
		{
			double tempStored = futureValue(depositAmount, listOfCDOffers[i].getInterestRate(), listOfCDOffers[i].getTerm());								
			if(tempStored > biggest)
			{
				indexSecondBiggest = indexBiggest;
				indexBiggest = i;			
			}
			if(tempStored > secondBiggest && tempStored != biggest)
			{
				indexSecondBiggest = i;
			}
		}		
		return listOfCDOffers[indexSecondBiggest];
	}
	
	static void clearCDOfferings(){listOfCDOffers = null;}
	
	static void setCDOfferings(CDOffering[] offerings){listOfCDOffers = offerings;}
	
	static long getNextAccountNumber(){return nextAccountNumber++;}
	
	static double totalBalances()
	{
		double total = 0;
		for(AccountHolder ah: listOfAccountHolders) 
		{
			if(ah != null)
			{
				total += ah.getCombinedBalance();
			}		
		}
		return total;
	 }
	
	public static double futureValue(double presentValue, double interestRate, int term)
	{
		return presentValue * Math.pow((1 + interestRate), term);
	}

	
	//finish//
	@SuppressWarnings("unused")
	static BankAccount readFromFile(String accountData) throws ParseException
	{		
		try(Scanner sc = new Scanner(new FileReader(accountData));)
		{																																	 
			setNextAccountNumber(Long.parseLong(sc.next()));										// store next acct num
			// -- CD OFFERS -- //
			CDOffering[] newCDarr = new CDOffering[sc.nextInt()];									// set 3 cd offers
			for(int i = 0; i < newCDarr.length; i++)
			{
				newCDarr[i] = CDOffering.readFromString(sc.next());									// send to CDOffer to return 				 
			}
			setCDOfferings(newCDarr);
			
			int newNumOfAcctHolder = sc.nextInt();													// set new num of acct holders
			// -- GET ACCT HOLDER INFO AND ADD --//
			for(int i = 0; i < newNumOfAcctHolder; i++)
			{
				AccountHolder tempAcct = AccountHolder.readFromString(sc.next());					// temp store num of account holders
				int numOfCheckAccts = sc.nextInt();													// store num of checking accounts
					for(int j = 0; j < numOfCheckAccts; j++)	
					{
						tempAcct.addCheckingAccount(CheckingAccount.readFromString(sc.next()));		// read from str and add obj to Acct Holder			
					}
				int numOfSavAccts = sc.nextInt();
					for(int j = 0; j < numOfSavAccts; j++)
					{
						SavingsAccount addSavingsAccount = tempAcct.addSavingsAccount(SavingsAccount.readFromString(sc.next()));		// read from str and add obj to Acct Holder
					}
				int numOfCDAccts = sc.nextInt();
					for(int j = 0; j < numOfCDAccts; j++)
					{
						tempAcct.addCDAccount(CDAccount.readFromString(sc.next()));					// read from str and add obj to Acct Holder
					}
					
				listOfAccountHolders[i] = tempAcct;													// create new Acct Holder list Array with new values
			}				
		}catch(Exception e)
		{
			e.printStackTrace();
		}		
		return null;
	}
	
	String writeToFile()
	{	
		try(BufferedWriter bw = new BufferedWriter(new FileWriter("src/test/testMeritBank_testing")))
		{
			bw.write(String.valueOf(nextAccountNumber)); bw.newLine();						// next acct num
			
			bw.write(String.valueOf(listOfCDOffers.length)); bw.newLine();					// num of CD Offers
				for(int i = 0; i < listOfCDOffers.length; i++)
				{
					bw.write(listOfCDOffers[i].writeToString()); bw.newLine();				// list CD offers
				}
				
			bw.write(String.valueOf(listOfAccountHolders.length)); bw.newLine();			// num of account holders
			sortAccountHolders();								// sort account holders then iterate over new array
				for(int i = 0; i < listOfAccountHolders.length; i++) 
				{									
					bw.write(listOfAccountHolders[i].getLastName() + "," +
							listOfAccountHolders[i].getMiddleName() + "," +
							listOfAccountHolders[i].getFirstName() + "," +
							listOfAccountHolders[i].getSSN()
							); bw.newLine();
					
					bw.write(String.valueOf(listOfAccountHolders[i].getNumberOfCheckingAccounts())); bw.newLine();
						for(int j = 0; j < listOfAccountHolders[i].getNumberOfCheckingAccounts(); i++)
						{
							bw.write(j); bw.newLine(); //get checking accounts info
						}
					bw.write(String.valueOf(listOfAccountHolders[i].getNumberOfSavingsAccounts())); bw.newLine();
						for(int j = 0; j < listOfAccountHolders[i].getNumberOfSavingsAccounts(); i++)
						{
							bw.write(listOfAccountHolders[i].compareTo(null)); bw.newLine(); //do something
						}
					bw.write(String.valueOf(listOfAccountHolders[i].getNumberOfCDAccounts())); bw.newLine();
						for(int j = 0; j < listOfAccountHolders[i].getNumberOfCDAccounts(); i++)
						{
							bw.write(listOfAccountHolders[i].compareTo(null)); bw.newLine(); //do something
						}
				}				
		}		
		catch(IOException e) 
		{
			System.out.println("File not found"); 
		}
		return null; // what to return ?? file?
	}
	
	static AccountHolder[] sortAccountHolders()
	{		
		Arrays.sort(listOfAccountHolders, Collections.reverseOrder());
		return listOfAccountHolders;											
	}
	
	private static void setNextAccountNumber(long nextAccountNumber){MeritBank.nextAccountNumber = nextAccountNumber;}

}