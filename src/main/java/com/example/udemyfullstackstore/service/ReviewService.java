package com.example.udemyfullstackstore.service;

import com.example.udemyfullstackstore.repository.ReviewRepository;
import com.example.udemyfullstackstore.entity.Review;
import com.example.udemyfullstackstore.request.ReviewRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public void postReview(String userEmail, ReviewRequest requestReview) throws Exception {
        Review validateReview = reviewRepository.findByUserEmailAndBookId(userEmail, requestReview.getBookId());
        if (validateReview != null) {
            throw new Exception("review already created");
        }
        Review review = new Review();
        review.setBookId(requestReview.getBookId());
        review.setRating(requestReview.getRating());
        review.setUserEmail(userEmail);
        if (requestReview.getReviewDescription().isPresent()) {
            // copy all data in optional to string
            review.setReviewDescription(requestReview.getReviewDescription().map(
                    Object::toString
            ).orElse(null));
        }
        review.setDate(Date.valueOf(LocalDate.now()));
        reviewRepository.save(review);
    }

    // already send review or not
    public Boolean userReviewListed(String userEmail, Long bookId) {
        Review validateReview = reviewRepository.findByUserEmailAndBookId(userEmail, bookId);
        return validateReview != null;
    }
}
