package com.javastart.customerservice.deposit.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
public class Deposit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long billId;

    private String email;

    private BigDecimal amount;

    private OffsetDateTime depositDate;

    public Deposit(Long billId, String email, BigDecimal amount, OffsetDateTime depositDate) {
        this.billId = billId;
        this.email = email;
        this.amount = amount;
        this.depositDate = depositDate;
    }
}
