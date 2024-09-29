package com.example.udemyfullstackstore.service;

import com.example.udemyfullstackstore.entity.Book;
import com.example.udemyfullstackstore.entity.Message;
import com.example.udemyfullstackstore.repository.BookRepository;
import com.example.udemyfullstackstore.repository.CheckoutRepository;
import com.example.udemyfullstackstore.repository.MessageRepository;
import com.example.udemyfullstackstore.repository.ReviewRepository;
import com.example.udemyfullstackstore.request.AddBookRequest;
import com.example.udemyfullstackstore.request.AdminQuestionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {
    private final BookRepository bookRepository;
    private final MessageRepository messageRepository;
    private final CheckoutRepository checkoutRepository;
    private final ReviewRepository reviewRepository;

    public void putMessage(AdminQuestionRequest adminQuestion, String adminEmail) throws Exception {
        Optional<Message> message = messageRepository.findById(adminQuestion.getId());
        if (message.isEmpty()) {
            throw new Exception("message not found");
        }
        message.get().setAdminEmail(adminEmail);
        message.get().setResponse(adminQuestion.getResponse());
        message.get().setClosed(true);
        messageRepository.save(message.get());
    }

    public void postBook(AddBookRequest request) {
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setDescription(request.getDescription());
        book.setCopies(request.getCopies());
        book.setCopiesAvailable(request.getCopies());
        book.setImg(request.getImg());
        book.setCategory(request.getCategory());
        bookRepository.save(book);
    }

    public void changeBookQuantity(String bookId, int quantity, boolean increase) throws Exception {
        Optional<Book> book = bookRepository.findById(bookId);

        if (book.isEmpty()) {
            throw new Exception("book does not exist");
        }

        int value;
        if (increase) {
            value = 1;
        }
        else {
            value = -1;
        }

        book.get().setCopiesAvailable(book.get().getCopiesAvailable() + value * quantity);
        book.get().setCopies(book.get().getCopies() + value * quantity);
        bookRepository.save(book.get());
    }

    public void deleteBook(String bookId) throws Exception {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isEmpty()) {
            throw new Exception("book does not exist");
        }
        String id = book.get().getId();
        bookRepository.deleteById(id);
        checkoutRepository.deleteAllByBookId(id);
        reviewRepository.deleteAllByBookId(id);
    }
}
