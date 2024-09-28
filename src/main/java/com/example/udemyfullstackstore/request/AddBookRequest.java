package com.example.udemyfullstackstore.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddBookRequest {
    private String title;
    private String author;
    private String description;
    private int copies;
    private String category;
    private String img;
}
