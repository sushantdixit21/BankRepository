package com.db.awmd.challenge.repository;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.stereotype.Repository;

import com.db.awmd.challenge.domain.Account;

import com.db.awmd.challenge.exception.DuplicateAccountIdException;
import com.db.awmd.challenge.exception.InsufficientBalanceException;
import com.db.awmd.challenge.exception.InvalidAccountEcxeption;


@Repository
	public class AccountsRepositoryInMemory implements AccountsRepository {

	  private final Map<String, Account> accounts = new ConcurrentHashMap<>();
	  
	  private Lock bankLock;
	  
	  @Override
	  public void createAccount(Account account) throws DuplicateAccountIdException {
	    Account previousAccount = accounts.putIfAbsent(account.getAccountId(), account);
	    if (previousAccount != null) {
	      throw new DuplicateAccountIdException(
	        "Account id " + account.getAccountId() + " already exists!");
	    }
	  }
	  

	  @Override
	  public Account getAccount(String accountId) {
	    return accounts.get(accountId);
	  }

	  @Override
	  public void clearAccounts() {
	    accounts.clear();
	  }

	  // new code
	@Override
	public String fundTransfer(String accountId, String accountId2, BigDecimal amount) throws InsufficientBalanceException,InvalidAccountEcxeption {
		Account fromAccount = getAccount(accountId);
		Account toAccount = getAccount(accountId2);
		
		 bankLock = new ReentrantLock();
		
	
		if(fromAccount!= null && toAccount!= null) {
			

			if(fromAccount.getBalance().compareTo(amount) == -1) {
			throw new InsufficientBalanceException("Insufficient funds in source account with accountId :" + accountId);
			}
			
			
			 else if(fromAccount.getBalance().compareTo(amount) ==1) {
				 
				 bankLock.lock();
				 
				 try {
					 
				 fromAccount.setAmount(fromAccount.getBalance().subtract(amount));
				 toAccount.setAmount(toAccount.getBalance().add(amount));
				 return "success";
				 
				 }
				 finally {
				            bankLock.unlock();
				        }
				 }
				return "failure";
				 }
		
		throw new InvalidAccountEcxeption(
		        "Invalid account please enter the correct AccountId");
		    }



	}


	

	




