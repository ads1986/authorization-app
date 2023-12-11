package com.authorization;

import com.authorization.model.Account;
import com.authorization.model.Rule;
import com.authorization.model.Transaction;
import com.authorization.service.AccountService;
import com.authorization.service.AuthorizerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class AccountServiceUnitTest {
    private Rule ruleRegular;
    private Rule rulePremium;
    private LocalDateTime validDate = LocalDateTime.of(2023,12, 8, 16, 0);

    private LocalDateTime invalidDate = LocalDateTime.of(2023,12, 10, 5, 0);
    @BeforeEach
    void init(){
        this.ruleRegular = Rule.builder()
                .limitPerDay(500)
                .individualTransaction(200)
                .transactonPerHour(10)
                .startDate(validDate.withHour(8).withMinute(59))
                .endDate(validDate.withHour(17).withMinute(1))
                .build();
        this.rulePremium = Rule.builder()
                .limitPerDay(1000)
                .individualTransaction(500)
                .transactonPerHour(20)
                .startDate(validDate.withHour(7).withMinute(59))
                .endDate(validDate.withHour(20).withMinute(1))
                .build();
    }
    @Test
    @DisplayName("Regular User | Adding a valid transaction")
    void test1(){
        Transaction transaction = Transaction.builder()
                .amount(200)
                .date(validDate)
                .build();

        Account account = Account.builder()
                .id(1)
                .balance(500)
                .status(true)
                .history(new ArrayList<>())
                .build();

        new AccountService(ruleRegular, validDate).addTransaction(account, transaction);

        Assertions.assertEquals(300, account.getBalance());
    }

    @Test
    @DisplayName("Premium User | Adding a valid transaction")
    void test2(){
        Transaction transaction = Transaction.builder()
                .amount(200)
                .date(validDate)
                .build();

        Account account = Account.builder()
                .id(1)
                .balance(500)
                .status(true)
                .history(new ArrayList<>())
                .build();

        new AccountService(rulePremium, validDate).addTransaction(account, transaction);

        Assertions.assertEquals(300, account.getBalance());
    }

}
