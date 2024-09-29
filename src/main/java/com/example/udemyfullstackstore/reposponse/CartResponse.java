package com.example.udemyfullstackstore.reposponse;

import com.example.udemyfullstackstore.entity.Book;
import lombok.Data;

@Data
public class CartResponse {
    private Book book;
    public CartResponse(Book book) {
        this.book = book;
    }
}
