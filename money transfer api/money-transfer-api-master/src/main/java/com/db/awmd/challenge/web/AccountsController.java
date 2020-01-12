
package com.db.awmd.challenge.web;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.exception.DuplicateAccountIdException;
import com.db.awmd.challenge.service.AccountsService;
import com.db.awmd.challenge.service.NotificationService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/accounts")
@Slf4j
public class AccountsController {
	
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AccountsController.class);

	private final AccountsService accountsService;
	
	private final NotificationService notificationService;
	
  

  @Autowired
  public AccountsController(AccountsService accountsService,NotificationService notificationService) {
	  
    this.accountsService = accountsService;
    this.notificationService=notificationService;
  }
  
  

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> createAccount(@RequestBody @Valid Account account) {
	  log.info("Creating account {}", account);

    try {
    this.accountsService.createAccount(account);
    } catch (DuplicateAccountIdException daie) {
      return new ResponseEntity<>(daie.getMessage(), HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping(path = "/{accountId}")
  public Account getAccount(@PathVariable String accountId) {
    log.info("Retrieving account for id {}", accountId);
    return this.accountsService.getAccount(accountId);
  }
  
  @GetMapping(path = "/{accountId}/{accountId2}/{amount}")
  public void fundTransfer(@PathVariable String accountId,@PathVariable String accountId2,@PathVariable BigDecimal amount) {
    log.info("Transfering the amount from "+accountId+ "to" +accountId2);
    
    String transferstatus= this.accountsService.fundTransfer(accountId,accountId2,amount);
   
    if(transferstatus=="success") {	
    	
    	this.notificationService.notifyAboutTransfer(getAccount(accountId2), "Amount :" +amount + "transfered to your account " + accountId2 + " succesfully");
    	this.notificationService.notifyAboutTransfer(getAccount(accountId), "Amount :" +amount + "transfered from your account "+ accountId + " succesfully ");
    }
    
    else {
    	this.notificationService.notifyAboutTransfer(getAccount(accountId2),  "fund transfer was unsuccessfull for account " + accountId2);
    	this.notificationService.notifyAboutTransfer(getAccount(accountId), "fund transfer was unsuccessfull for account " + accountId);
    	
    }
  }

}


