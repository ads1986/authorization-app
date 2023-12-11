package com.authorization;

import com.authorization.model.Account;
import com.authorization.model.Rule;
import com.authorization.model.Transaction;
import com.authorization.service.AccountService;
import com.authorization.service.AuthorizerService;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args){
        // Regular
        Rule rule = Rule.builder()
                .limitPerDay(500)
                .individualTransaction(200)
                .transactonPerHour(10)
                .startDate(LocalDateTime.now().withHour(8).withMinute(59))
                .endDate(LocalDateTime.now().withHour(17).withMinute(1))
                .build();

        Transaction transaction = Transaction.builder()
                .amount(100)
                .date(LocalDateTime.now())
                .build();

        Account account = Account.builder()
                .id(1)
                .balance(500)
                .status(true)
                .history(new ArrayList<>())
                .build();

        AccountService accountService = new AccountService(rule, LocalDateTime.now());
        accountService.addTransaction(account, transaction);

        System.out.println(account.getBalance());
    }
}
