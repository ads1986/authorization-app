package com.authorization.service;

import com.authorization.model.Account;
import com.authorization.model.Rule;
import com.authorization.model.Transaction;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;

@AllArgsConstructor
public class AuthorizerService {

    private Rule rule;
    private LocalDateTime currentDate;

    public boolean authorize(Account account, Transaction transaction) {
        if (account == null || transaction == null) {
            return false;
        }

        boolean isWeekDay = isWeekDay(this.currentDate);
        boolean notExceedIndividualTransaction = transaction.getAmount() <= this.rule.getIndividualTransaction();

        if (isWeekDay && notExceedIndividualTransaction) {
            long currentTotalOfTransactions = getCurrentTotalOfTransactions(account, this.currentDate);
            boolean notExceedTransactionPerHour = currentTotalOfTransactions <= this.rule.getTransactonPerHour();

            if (notExceedTransactionPerHour) {
                int currentTotalAmount = getCurrentTotalAmount(account, transaction, this.rule);

                return currentTotalAmount <= this.rule.getLimitPerDay();
            }
        }
        return false;
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
        account.addTransaction(transaction);
    }
}