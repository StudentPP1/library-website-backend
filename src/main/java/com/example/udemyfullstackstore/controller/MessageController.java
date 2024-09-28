package com.example.udemyfullstackstore.controller;

import com.example.udemyfullstackstore.entity.Message;
import com.example.udemyfullstackstore.enums.UserRole;
import com.example.udemyfullstackstore.jwt.JWTService;
import com.example.udemyfullstackstore.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/secure")
public class MessageController {
    private final MessageService messageService;
    private final JWTService jwtService;

    @PostMapping("/add/message")
    public void postMessage(
            @RequestHeader(value = "Authorization") String token,
            @RequestBody Message messageRequest) {
        String userEmail = jwtService.getEmail(token.substring(7));
        messageService.postMessage(messageRequest, userEmail);
    }

    @GetMapping("/messages")
    public Page<Message> getMessages(
            @RequestParam(name="page") String page,
            @RequestParam(name="size") String size,
            @RequestHeader(value = "Authorization") String token) {
        String userEmail = jwtService.getEmail(token.substring(7));
        return messageService.getMessages(userEmail, Integer.parseInt(page), Integer.parseInt(size));
    }

    @GetMapping("/messages/closed")
    public Page<Message> getMessages(
            @RequestHeader(value = "Authorization") String token,
            @RequestParam(name="closed") Boolean closed,
            @RequestParam(name="page") String page,
            @RequestParam(name="size") String size) throws Exception {
        UserRole role =  jwtService.getRole(token.substring(7));
        if (role == UserRole.ADMIN) {
            return messageService.getMessagesByClosed(closed, Integer.parseInt(page), Integer.parseInt(size));
        }
        else {
            throw new Exception("user hasn't authorities");
        }
    }
}
