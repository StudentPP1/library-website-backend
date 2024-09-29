package com.example.udemyfullstackstore.service;

import com.example.udemyfullstackstore.entity.Payment;
import com.example.udemyfullstackstore.repository.BookRepository;
import com.example.udemyfullstackstore.repository.CheckoutRepository;
import com.example.udemyfullstackstore.entity.Book;
import com.example.udemyfullstackstore.entity.Checkout;
import com.example.udemyfullstackstore.repository.PaymentRepository;
import com.example.udemyfullstackstore.reposponse.CartResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final CheckoutRepository checkoutRepository;
    private final PaymentRepository paymentRepository;

    public Book checkoutBook(String userEmail, String bookId) throws Exception {
        Optional<Book> book = bookRepository.findById(bookId);

        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);

        if (book.isEmpty() || validateCheckout != null || book.get().getCopiesAvailable() <= 0) {
            throw new Exception("book doesn't exist");
        }

        // create a new userPayment
        Payment userPayment = paymentRepository.findByUserEmail(userEmail);
        if (userPayment == null) {
            Payment payment = new Payment();
            payment.setAmount(00.00);
            payment.setUserEmail(userEmail);
            paymentRepository.save(payment);
        }

        book.get().setCopiesAvailable(book.get().getCopiesAvailable() - 1);
        bookRepository.save(book.get());

        Checkout checkout = new Checkout(
                userEmail,
                book.get().getId()
        );
        checkoutRepository.save(checkout);

        return book.get();
    }

    public boolean checkoutBookByUser(String userEmail, String bookId) {
        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);
        return validateCheckout != null;
    }

    public int currentCartBooksCount(String userEmail) {
        return checkoutRepository.findBooksByUserEmail(userEmail).size();
    }

    public List<CartResponse> currentCart(String userEmail) {
        List<CartResponse> responses = new ArrayList<>();
        // all book user checked out
        List<Checkout> checkoutList = checkoutRepository.findBooksByUserEmail(userEmail);
        List<String> bookIdList = new ArrayList<>();

        for (Checkout checkout: checkoutList) {
            bookIdList.add(checkout.getBookId());
        }
        List<Book> books = new ArrayList<>();
        for (String bookId: bookIdList) {
            Optional<Book> findBook = bookRepository.findById(bookId);
            findBook.ifPresent(books::add);
        }

        for (Book book : books) {
            // check book in checkout list
            Optional<Checkout> checkout = checkoutList.stream().filter(
                    x -> Objects.equals(x.getBookId(), book.getId())
            ).findFirst();

            if (checkout.isPresent()) {
                responses.add(new CartResponse(book));
            }
        }
        return responses;
    }

    public void returnBook(String userEmail, String bookId) throws Exception {
        Optional<Book> book = bookRepository.findById(bookId);
        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);

        if (book.isEmpty() || validateCheckout == null) {
            throw new Exception("Book does not exist or not checked out");
        }

        book.get().setCopiesAvailable(book.get().getCopiesAvailable() + 1);
        bookRepository.save(book.get());
        checkoutRepository.deleteById(validateCheckout.getId());
    }

    public void buyBook(String userEmail, String bookId) throws Exception {
        Optional<Book> book = bookRepository.findById(bookId);
        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);

        if (book.isEmpty() || validateCheckout == null) {
            throw new Exception("Book does not exist or not checked out");
        }

        book.get().setBuy(true);
        bookRepository.save(book.get());
    }
}
