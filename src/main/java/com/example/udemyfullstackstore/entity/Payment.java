package com.example.udemyfullstackstore.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import javax.persistence.Id;


@Data
@Document(collection = "payment")
public class Payment {
    @Id
    @Column(name="id")
    private String id;

    @Column(name="user_email")
    private String userEmail;

    @Column(name="amount")
    private double amount;
}
