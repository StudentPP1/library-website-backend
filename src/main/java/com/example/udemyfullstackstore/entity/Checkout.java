package com.example.udemyfullstackstore.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="checkout")
@Data
public class Checkout {

    public Checkout() {}

    public Checkout(String userEmail,
                    Long bookId) {
        this.userEmail = userEmail;
        this.bookId = bookId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="user_email")
    private String userEmail;

    @Column(name="book_id")
    private Long bookId;
}
