package com.svalero.toprestaurants.domain;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    private long id;
    private String name;
    private String surname;
    private String telephone;
    private LocalDate birthDate;
    private boolean vip;
}
