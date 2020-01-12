
package com.db.awmd.challenge.repository;
import java.math.BigDecimal;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.exception.DuplicateAccountIdException;
import com.db.awmd.challenge.repository.*;

public interface AccountsRepository {
	void createAccount(Account account) throws DuplicateAccountIdException;

	  Account getAccount(String accountId);

	  void clearAccounts();
	  
	  String fundTransfer(String accountId,String accountId2,BigDecimal amount);
	
	 

}

