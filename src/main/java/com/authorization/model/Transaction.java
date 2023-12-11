package com.authorization.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class Transaction {
    private int amount;
    private LocalDateTime date;
}
