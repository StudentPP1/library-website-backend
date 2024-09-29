package com.example.udemyfullstackstore.repository;

import com.example.udemyfullstackstore.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository<Message, String> {
    Page<Message> findByUserEmail(String userEmail, Pageable pageable);
    Page<Message> findByClosed(Boolean closed, Pageable pageable);
}
