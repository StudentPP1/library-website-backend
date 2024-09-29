package com.example.udemyfullstackstore.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
@Document(collection = "message")
public class Message {
    @Id
    @Column(name="id")
    private String id;

    @Column(name="user_email")
    private String userEmail;

    @Column(name="title")
    private String title;

    @Column(name="question")
    private String question;

    @Column(name="admin_email")
    private String adminEmail;

    @Column(name="response")
    private String response;

    @Column(name="closed")
    private boolean closed;

    public Message() {}

    public Message(String title, String question) {
        this.title = title;
        this.question = question;
    }
}
