package com.authorization.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Transaction {
    private int amount;
    private LocalDateTime date;
}
