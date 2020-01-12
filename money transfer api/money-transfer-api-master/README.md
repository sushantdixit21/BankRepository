one more  endpoint is added
	1.	GET /{accountId}/{accountId2}/{amount} -> transfer fund from one account to another

	•	there is no currencies, exchange rates, transactions charges.
	•	funds will be transfer in the account within same bank.
	•	accountId -> source account from which  money is debited.
	•	accountId2 -> destination account into which amount will be credited.
	•	amount -> money transfer


	•	To Perform the fund transfer we need this method  “fundTransfer()” which will take 3 parameters :-
	•	accountId -> source account from which  money is debited.
	•	accountId2 -> destination account into which amount will be credited.
	•	amount -> money transfer

	This method will first check wether the accounts are correct or not,If accounts are not correct then we will get Invalid account Exception .
	If correct Accounts are passed then We will check the balance in the source account.
	If balance is sufficient then the transfer operation is performed.
	Otherwise we will get Insufficient fund exception.
	