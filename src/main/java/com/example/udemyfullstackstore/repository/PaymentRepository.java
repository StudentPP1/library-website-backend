package com.example.udemyfullstackstore.repository;

import com.example.udemyfullstackstore.entity.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentRepository extends MongoRepository<Payment, String> {
    Payment findByUserEmail(String userEmail);
}
