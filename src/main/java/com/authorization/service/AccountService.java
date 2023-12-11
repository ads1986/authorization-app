package com.authorization.service;

import com.authorization.model.Account;
import com.authorization.model.Rule;
import com.authorization.model.Transaction;

import java.time.LocalDateTime;

public class AccountService {

    private final AuthorizerService authorizerService;

    public AccountService(Rule rule, LocalDateTime currentDate) {
        this.authorizerService = new AuthorizerService(rule, currentDate);
    }

    public void addTransaction(Account account, Transaction transaction){
        boolean authorize = authorizerService.authorize(account, transaction);
        if (authorize) {
            int balance = account.getBalance() - transaction.getAmount();
            account.setBalance(balance);
            account.addTransaction(transaction);
        }
    }

}