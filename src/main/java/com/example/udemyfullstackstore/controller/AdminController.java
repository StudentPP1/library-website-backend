package com.example.udemyfullstackstore.controller;

import com.example.udemyfullstackstore.enums.UserRole;
import com.example.udemyfullstackstore.jwt.JWTService;
import com.example.udemyfullstackstore.request.AddBookRequest;
import com.example.udemyfullstackstore.request.AdminQuestionRequest;
import com.example.udemyfullstackstore.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/secure/admin")
public class AdminController {
    private final JWTService jwtService;
    private final AdminService adminService;

    private void validateAdminUser(String jwt) throws Exception {
        UserRole role =  jwtService.getRole(jwt);
        if (!(role == UserRole.ADMIN)) {
            throw new Exception("user hasn't authorities");
        }
    }

    @PutMapping("/message")
    public void putMessage(
            @RequestHeader(value = "Authorization") String token,
            @RequestBody AdminQuestionRequest adminQuestion) throws Exception {
        String jwt = token.substring(7);
        validateAdminUser(jwt);
        String adminEmail = jwtService.getEmail(jwt);
        adminService.putMessage(adminQuestion, adminEmail);
    }

    @PostMapping("/add/book")
    public void postBook(
            @RequestHeader(value = "Authorization") String token,
            @RequestBody AddBookRequest addBookRequest
            ) throws Exception {
        validateAdminUser(token.substring(7));
        adminService.postBook(addBookRequest);
    }

    @PostMapping("/increase/book/quantity")
    public void increaseBookQuantity(
            @RequestParam Long bookId,
            @RequestParam int quantity,
            @RequestHeader(value = "Authorization") String token) throws Exception {
        validateAdminUser(token.substring(7));
        adminService.changeBookQuantity(bookId, quantity, true);
    }

    @PostMapping("/decrease/book/quantity")
    public void decreaseBookQuantity(
            @RequestParam Long bookId,
            @RequestParam int quantity,
            @RequestHeader(value = "Authorization") String token) throws Exception {
        validateAdminUser(token.substring(7));
        adminService.changeBookQuantity(bookId, quantity, false);
    }

    @DeleteMapping("/delete/book")
    public void deleteBook(
            @RequestParam Long bookId,
            @RequestHeader(value = "Authorization") String token) throws Exception {
        validateAdminUser(token.substring(7));
        adminService.deleteBook(bookId);
    }
}
