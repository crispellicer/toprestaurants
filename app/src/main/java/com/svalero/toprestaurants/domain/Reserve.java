package com.svalero.toprestaurants.domain;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reserve {
    private long id;
    private int people;
    private int tables;
    private LocalDate reserveDate;
    private boolean isPaid;
    private boolean allergic;
}
