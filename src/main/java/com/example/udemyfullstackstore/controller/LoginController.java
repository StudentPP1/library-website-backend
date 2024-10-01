package com.example.udemyfullstackstore.controller;

import com.example.udemyfullstackstore.enums.UserRole;
import com.example.udemyfullstackstore.jwt.JWTService;
import com.example.udemyfullstackstore.request.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class LoginController {
    private final JWTService jwtService;

    @Value("${application.security.superAdmin.username}")
    private String superAdminUsername;

    @Value("${application.security.superAdmin.password}")
    private String superAdminPassword;

    private final PasswordEncoder passwordEncoder;

    @PostMapping("/api/login")
    public HashMap<String, String> login(@RequestBody LoginRequest user) {
        System.out.println("ok");
        HashMap<String, String> map = new HashMap<>();
        UserRole role;

        String userPassword = passwordEncoder.encode(user.getPassword());
        String adminPassword = passwordEncoder.encode(superAdminPassword);

        if (Objects.equals(userPassword, adminPassword) && Objects.equals(user.getUsername(), superAdminUsername)) {
            role = UserRole.ADMIN;
        }
        else {
            role = UserRole.USER;
        }

        map.put("token", jwtService.buildToken(
                user.getEmail(),
                user.getUsername(),
                user.getPassword(),
                role
        ));
        map.put("role", String.valueOf(role));
        return map;
    }
}
