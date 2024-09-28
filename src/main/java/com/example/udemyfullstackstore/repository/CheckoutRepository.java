package com.example.udemyfullstackstore.repository;

import com.example.udemyfullstackstore.entity.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CheckoutRepository extends JpaRepository<Checkout, Long> {
    Checkout findByUserEmailAndBookId (String userEmail, Long bookId);
    List<Checkout> findBooksByUserEmail(String userEmail);

    // @Modifying = we can execute not only SELECT queries, but also INSERT, UPDATE, DELETE, and even DDL queries
    void deleteAllByBookId(Long bookId);
}
