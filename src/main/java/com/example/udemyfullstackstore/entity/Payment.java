package com.example.udemyfullstackstore.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="payment")
@Data
public class Payment {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="user_email")
    private String userEmail;

    @Column(name="amount")
    private double amount;
}
