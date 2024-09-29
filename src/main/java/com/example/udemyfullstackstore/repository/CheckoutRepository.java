package com.example.udemyfullstackstore.repository;

import com.example.udemyfullstackstore.entity.Checkout;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CheckoutRepository extends MongoRepository<Checkout, String> {
    Checkout findByUserEmailAndBookId (String userEmail, String bookId);
    List<Checkout> findBooksByUserEmail(String userEmail);
    void deleteAllByBookId(String bookId);
}
