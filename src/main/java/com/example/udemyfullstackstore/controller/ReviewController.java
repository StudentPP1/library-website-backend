package com.example.udemyfullstackstore.controller;

import com.example.udemyfullstackstore.jwt.JWTService;
import com.example.udemyfullstackstore.request.ReviewRequest;
import com.example.udemyfullstackstore.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/secure/reviews")
public class ReviewController {
    private final ReviewService reviewService;
    private final JWTService jwtService;

    @PostMapping("/postReview")
    public void postReview(@RequestHeader(value = "Authorization") String token,
                           @RequestBody ReviewRequest review) throws Exception {
        String userEmail = jwtService.getEmail(token.substring(7));
        reviewService.postReview(userEmail, review);
    }

    @GetMapping("/user/book")
    public Boolean reviewBookByUser(@RequestHeader(value = "Authorization") String token,
                                    @RequestParam Long bookId) {
        String userEmail = jwtService.getEmail(token.substring(7));
        return reviewService.userReviewListed(userEmail, bookId);
    }
}
