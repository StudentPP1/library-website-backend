package com.example.udemyfullstackstore.repository;

import com.example.udemyfullstackstore.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    // pageable allow to use ?page=0&?size=5
    Page<Book> findByTitleContaining(@RequestParam("title") String title, Pageable pageable);
    Page<Book> findByCategory(@RequestParam("category") String category, Pageable pageable);

    // spring don't know how to search that's why we do query
    @Query("select book from Book book where book.id in :book_ids")
    List<Book> findBooksByBookIds(@Param("book_ids") List<Long> bookIds);
}
