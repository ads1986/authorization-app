package com.authorization;

import com.authorization.model.Account;
import com.authorization.model.Rule;
import com.authorization.model.Transaction;
import com.authorization.service.AuthorizerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AuthorizerServiceUnitTest {
    private Rule ruleRegular;
    private Rule rulePremium;

    private LocalDateTime validDate = LocalDateTime.of(2023,12, 8, 16, 0);

    private LocalDateTime invalidDate = LocalDateTime.of(2023,12, 10, 5, 0);

    private AuthorizerService authorizerService = new AuthorizerService();

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
    @DisplayName("Regular User | Exceeding individual amount (200)")
    void test1(){

        Transaction transaction = Transaction.builder()
                .amount(201)
                .date(validDate)
                .build();

        Account account = Account.builder()
                .id(1)
                .balance(500)
                .status(true)
                .regular(true)
                .history(new ArrayList<>())
                .build();

        Account accountReturn = authorizerService.execute(account, transaction, ruleRegular, validDate);

        Assertions.assertEquals(500, accountReturn.getBalance());
    }

    @Test
    @DisplayName("Regular User | Exceeding limite transactions per hour (10)")
    void test2(){
        List<Transaction> transactions = new ArrayList<>();

        Transaction transaction = Transaction.builder()
                .amount(20)
                .date(validDate)
                .build();

        for (int i=1; i < 11; i++) {
            transactions.add(transaction);
        }

        Account account = Account.builder()
                .id(1)
                .balance(500)
                .status(true)
                .regular(true)
                .history(transactions)
                .build();

        Account accountReturn = authorizerService.execute(account, transaction, ruleRegular, validDate);

        Assertions.assertEquals(500, accountReturn.getBalance());
    }

    @Test
    @DisplayName("Regular User | Exceeding limit per day (500)")
    void test3(){
        List<Transaction> transactions = new ArrayList<>();

        Transaction transaction = Transaction.builder()
                .amount(100)
                .date(validDate)
                .build();

        for (int i=1; i < 6; i++) {
            transactions.add(transaction);
        }

        Account account = Account.builder()
                .id(1)
                .balance(500)
                .status(true)
                .regular(true)
                .history(transactions)
                .build();

        Account accountReturn = authorizerService.execute(account, transaction, ruleRegular, validDate);

        Assertions.assertEquals(500, accountReturn.getBalance());
    }

    @Test
    @DisplayName("Regular User | Transaction on weekends ")
    void test4(){
        Transaction transaction = Transaction.builder()
                .amount(200)
                .date(invalidDate)
                .build();

        Account account = Account.builder()
                .id(1)
                .balance(500)
                .status(true)
                .regular(true)
                .history(new ArrayList<>())
                .build();

        Account accountReturn = authorizerService.execute(account, transaction, ruleRegular, invalidDate);

        Assertions.assertEquals(500, accountReturn.getBalance());
    }

    @Test
    @DisplayName("Regular User | Execute valid transaction")
    void test5(){
        Transaction transaction = Transaction.builder()
                .amount(200)
                .date(validDate)
                .build();

        Account account = Account.builder()
                .id(1)
                .balance(500)
                .status(true)
                .regular(true)
                .history(new ArrayList<>())
                .build();

        Account accountReturn = authorizerService.execute(account, transaction, ruleRegular, validDate);

        Assertions.assertEquals(300, accountReturn.getBalance());
    }

    @Test
    @DisplayName("Premium User | Exceeding individual amount (500)")
    void test6(){
        Transaction transaction = Transaction.builder()
                .amount(501)
                .date(validDate)
                .build();

        Account account = Account.builder()
                .id(1)
                .balance(500)
                .status(true)
                .regular(true)
                .history(new ArrayList<>())
                .build();

        Account accountReturn = authorizerService.execute(account, transaction, rulePremium, validDate);

        Assertions.assertEquals(500, accountReturn.getBalance());
    }

    @Test
    @DisplayName("Premium User | Exceeding limite transactions per hour (20)")
    void test7(){
        List<Transaction> transactions = new ArrayList<>();

        Transaction transaction = Transaction.builder()
                .amount(20)
                .date(validDate)
                .build();

        for (int i=1; i < 21; i++) {
            transactions.add(transaction);
        }

        Account account = Account.builder()
                .id(1)
                .balance(500)
                .status(true)
                .regular(true)
                .history(transactions)
                .build();

        Account accountReturn = authorizerService.execute(account, transaction, rulePremium, validDate);

        Assertions.assertEquals(500, accountReturn.getBalance());
    }

    @Test
    @DisplayName("Premium User | Exceeding limit per day (1000)")
    void test8(){
        List<Transaction> transactions = new ArrayList<>();

        Transaction transaction = Transaction.builder()
                .amount(200)
                .date(validDate)
                .build();

        for (int i=1; i < 6; i++) {
            transactions.add(transaction);
        }

        Account account = Account.builder()
                .id(1)
                .balance(500)
                .status(true)
                .regular(true)
                .history(transactions)
                .build();

        Account accountReturn = authorizerService.execute(account, transaction, rulePremium, validDate);

        Assertions.assertEquals(500, accountReturn.getBalance());
    }

    @Test
    @DisplayName("Premium User | Transaction on weekends ")
    void test9(){
        Transaction transaction = Transaction.builder()
                .amount(200)
                .date(invalidDate)
                .build();

        Account account = Account.builder()
                .id(1)
                .balance(500)
                .status(true)
                .regular(true)
                .history(new ArrayList<>())
                .build();

        Account accountReturn = authorizerService.execute(account, transaction, rulePremium, invalidDate);

        Assertions.assertEquals(500, accountReturn.getBalance());
    }

    @Test
    @DisplayName("Premium User | Execute valid transaction")
    void test10(){
        Transaction transaction = Transaction.builder()
                .amount(200)
                .date(validDate)
                .build();

        Account account = Account.builder()
                .id(1)
                .balance(500)
                .status(true)
                .regular(true)
                .history(new ArrayList<>())
                .build();

        Account accountReturn = authorizerService.execute(account, transaction, rulePremium, validDate);

        Assertions.assertEquals(300, accountReturn.getBalance());
    }
}
