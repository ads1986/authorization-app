package com.authorization.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@Builder
public class Account {
    private int id;
    private int balance;
    private boolean status;
    private List<Transaction> history;
    public void addTransaction(Transaction transaction){
        if (history != null)
            history.add(transaction);
    }
}
