package com.authorization.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
public class Rule {
    private int limitPerDay;
    private int transactonPerHour;
    private int individualTransaction;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
