package com.example.udemyfullstackstore.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

@Data
@Document(collection = "review")
public class Review {

    @Id
    private String id;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "date")
    @CreatedDate
    private Date date;

    @Column(name = "rating")
    private double rating;

    @Column(name = "book_id")
    private String bookId;

    @Column(name = "review_description")
    private String reviewDescription;
}
