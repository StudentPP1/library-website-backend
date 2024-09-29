package com.example.udemyfullstackstore.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import javax.persistence.Id;


@Data
@Document(collection = "checkout")
public class Checkout {

    public Checkout() {}

    public Checkout(String userEmail,
                    String bookId) {
        this.userEmail = userEmail;
        this.bookId = bookId;
    }

    @Id
    @Column(name="id")
    private String id;

    @Column(name="user_email")
    private String userEmail;

    @Column(name="book_id")
    private String bookId;
}
