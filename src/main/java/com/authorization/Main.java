package com.authorization;

import com.authorization.model.Account;
import com.authorization.model.Rule;
import com.authorization.model.Transaction;
import com.authorization.service.AuthorizerService;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args){

        // Regular
        Rule ruleRegular = Rule.builder()
                .limitPerDay(500)
                .individualTransaction(200)
                .transactonPerHour(10)
                .startDate(LocalDateTime.now().withHour(8).withMinute(59))
                .endDate(LocalDateTime.now().withHour(17).withMinute(1))
                .build();

        Rule rulePremium = Rule.builder()
                .limitPerDay(1000)
                .individualTransaction(500)
                .transactonPerHour(20)
                .startDate(LocalDateTime.now().withHour(7).withMinute(59))
                .endDate(LocalDateTime.now().withHour(20).withMinute(1))
                .build();

        Transaction transaction = Transaction.builder()
                .amount(100)
                .date(LocalDateTime.now())
                .build();

        Account accountRegular = Account.builder()
                .id(1)
                .balance(500)
                .status(true)
                .regular(true)
                .history(new ArrayList<>())
                .build();

        Account accountPremium = Account.builder()
                .id(2)
                .balance(1000)
                .status(true)
                .regular(true)
                .history(new ArrayList<>())
                .build();

        AuthorizerService authorizerService = new AuthorizerService();

        System.out.println(authorizerService.execute(accountRegular, transaction, ruleRegular, LocalDateTime.now()));
        System.out.println(authorizerService.execute(accountPremium, transaction, rulePremium, LocalDateTime.now()));
    }
}
