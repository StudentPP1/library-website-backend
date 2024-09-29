package com.example.udemyfullstackstore.repository;

import com.example.udemyfullstackstore.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.RequestParam;

public interface ReviewRepository extends MongoRepository<Review, String> {
    Page<Review> findByBookId(@RequestParam("bookId") String bookId,
                              Pageable pageable);
    Review findByUserEmailAndBookId(String userEmail, String bookId);
    void deleteAllByBookId(String bookId);
}
