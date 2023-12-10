package com.authorization.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@Builder
@ToString
public class Account {
    private int id;
    private int balance;
    private boolean status;
    private boolean regular;
    private List<Transaction> history;
}
