package com.example.udemyfullstackstore.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
@Document(collection = "book")
public class Book {
    @Id
    @Column(name="id")
    private String id;
    @Column(name="title")
    private String title;
    @Column(name="author")
    private String author;
    @Column(name="description")
    private String description;
    @Column(name="copies")
    private int copies;
    @Column(name="copies_available")
    private int copiesAvailable;
    @Column(name="category")
    private String category;
    @Column(name="img")
    private String img;
    @Column(name="buy")
    private boolean buy;
}
