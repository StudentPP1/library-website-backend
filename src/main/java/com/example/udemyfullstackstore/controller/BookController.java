package com.example.udemyfullstackstore.controller;

import com.example.udemyfullstackstore.entity.Book;
import com.example.udemyfullstackstore.jwt.JWTService;
import com.example.udemyfullstackstore.reposponse.CartResponse;
import com.example.udemyfullstackstore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/secure")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final JWTService jwtService;

    @PutMapping("/return")
    public void returnBook(@RequestParam Long bookId, @RequestHeader(value = "Authorization") String token) throws Exception {
        String userEmail = jwtService.getEmail(token.substring(7));
        bookService.returnBook(userEmail, bookId);
    }

    @GetMapping("/cart")
    public List<CartResponse> currentCart(@RequestHeader(value = "Authorization") String token) throws Exception {
        System.out.println("/secure/cart");
        String userEmail = jwtService.getEmail(token.substring(7));
        return bookService.currentCart(userEmail);
    }

    @PutMapping("/checkout")
    public Book checkoutBook(@RequestParam Long bookId, @RequestHeader(value = "Authorization") String token) throws Exception {
        String userEmail = jwtService.getEmail(token.substring(7));
        return bookService.checkoutBook(userEmail, bookId);
    }

    @GetMapping("/book/checked")
    public Boolean checkoutBookByUser(@RequestParam Long bookId, @RequestHeader(value = "Authorization") String token) {
        String userEmail = jwtService.getEmail(token.substring(7));
        return bookService.checkoutBookByUser(userEmail, bookId);
    }

    @GetMapping("/book/checked/count")
    public int currentCheckedCount(@RequestHeader(value = "Authorization") String token) {
        String userEmail = jwtService.getEmail(token.substring(7));
        return bookService.currentCartBooksCount(userEmail);
    }

    @PutMapping("/buy/book")
    public void buyBook(@RequestParam Long bookId, @RequestHeader(value = "Authorization") String token) throws Exception {
        String userEmail = jwtService.getEmail(token.substring(7));
        System.out.println("buy book");
        bookService.buyBook(userEmail, bookId);
    }
}
