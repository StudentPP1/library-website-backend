package com.example.udemyfullstackstore.service;

import com.example.udemyfullstackstore.entity.Message;
import com.example.udemyfullstackstore.repository.MessageRepository;
import com.example.udemyfullstackstore.request.AdminQuestionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;

    public void postMessage(Message messageRequest, String userEmail) {
        Message message = new Message(
                messageRequest.getTitle(),
                messageRequest.getQuestion()
        );
        message.setUserEmail(userEmail);
        messageRepository.save(message);
    }

    public Page<Message> getMessages(String userEmail, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("title").descending());
        return messageRepository.findByUserEmail(userEmail, pageable);
    }

    public Page<Message> getMessagesByClosed(Boolean closed, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("title").descending());
        return messageRepository.findByClosed(closed, pageable);
    }
}
