
package com.db.awmd.challenge.service;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.repository.AccountsRepository;
import lombok.Getter;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountsService implements AccountsRepository{

  @Getter
  private final AccountsRepository accountsRepository;

  @Autowired
  public AccountsService(AccountsRepository accountsRepository) {
    this.accountsRepository = accountsRepository;
  }

  public void createAccount(Account account) {
    this.accountsRepository.createAccount(account);
  }

  public Account getAccount(String accountId) {
    return this.accountsRepository.getAccount(accountId);
  }

@Override
public void clearAccounts() {
	// TODO Auto-generated method stub
	
	}

//new code for fund transfer
	public String fundTransfer(String accountId,String accountId2,BigDecimal amount) {
	  return this.accountsRepository.fundTransfer(accountId,accountId2,amount);
}

}


