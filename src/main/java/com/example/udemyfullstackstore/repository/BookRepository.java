package com.example.udemyfullstackstore.repository;

import com.example.udemyfullstackstore.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

public interface BookRepository extends MongoRepository<Book, String> {
    // pageable allow to use ?page=0&?size=5
    Page<Book> findByTitleContaining(@RequestParam("title") String title, Pageable pageable);
    Page<Book> findByCategory(@RequestParam("category") String category, Pageable pageable);
}
