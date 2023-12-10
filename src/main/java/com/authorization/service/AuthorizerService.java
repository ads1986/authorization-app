package com.authorization.service;

import com.authorization.model.Account;
import com.authorization.model.Rule;
import com.authorization.model.Transaction;

import java.time.LocalDateTime;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;

public class AuthorizerService {

    public Account execute(Account account, Transaction transaction, Rule rule, LocalDateTime now) {
        boolean isWeekDay = isWeekDay(now);
        boolean notExceedIndividualTransaction = transaction.getAmount() <= rule.getIndividualTransaction();

        if (isWeekDay && notExceedIndividualTransaction) {
            long currentTotalOfTransactions = getCurrentTotalOfTransactions(account, now);
            boolean notExceedTransactionPerHour = currentTotalOfTransactions <= rule.getTransactonPerHour();

            if (notExceedTransactionPerHour) {
                int currentTotalAmount = getCurrentTotalAmount(account, transaction, rule);
                boolean notExceedLimitPerDay =  currentTotalAmount <= rule.getLimitPerDay();

                if (notExceedLimitPerDay)
                    addTransaction(account, transaction);
            }
        }

        return account;
    }

    private boolean isWeekDay(LocalDateTime now){
        return now.getDayOfWeek().getValue() >= MONDAY.getValue()
                && now.getDayOfWeek().getValue() <= FRIDAY.getValue();
    }

    private long getCurrentTotalOfTransactions(Account account, LocalDateTime now){
        long numTransaction =  account.getHistory().stream()
                .filter(t -> t.getDate().getHour() == now.getHour())
                .count();

        return numTransaction + 1;
    }

    private int getCurrentTotalAmount(Account account, Transaction transaction, Rule rule){
        int dailyAmount = account.getHistory().stream()
                .filter(t -> t.getDate().isAfter(rule.getStartDate()) && t.getDate().isBefore(rule.getEndDate()))
                .mapToInt(Transaction::getAmount)
                .sum();

        return dailyAmount + transaction.getAmount();
    }

    private void addTransaction(Account account, Transaction transaction){
        int balance = account.getBalance() - transaction.getAmount();
        account.setBalance(balance);
        account.getHistory().add(transaction);
    }
}