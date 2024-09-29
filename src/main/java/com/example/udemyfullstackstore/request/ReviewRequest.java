package com.example.udemyfullstackstore.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequest {
    private double rating;
    private String bookId;
    private Optional<String> reviewDescription;
}
