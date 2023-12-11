package com.authorization.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
public class Account {
    private int id;
    private int balance;
    private boolean status;
    private List<Transaction> history = new ArrayList<Transaction>();

    public void addTransaction(Transaction transaction) {
        if (history != null) {
            history.add(transaction);
            this.updateBalance(transaction.getAmount());
        }
    }

    private void updateBalance(int amount) {
        this.balance -= amount;
    }
}