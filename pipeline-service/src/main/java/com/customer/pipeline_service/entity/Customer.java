package com.customer.pipeline_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "customers")
@Data
public class Customer {

    @Id
    private String customerId;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    @Column(columnDefinition = "TEXT")
    private String address;

    private LocalDate dateOfBirth;
    private BigDecimal accountBalance;
    private LocalDateTime createdAt;
}
