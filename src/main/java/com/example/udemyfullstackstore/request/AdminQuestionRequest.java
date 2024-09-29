package com.example.udemyfullstackstore.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminQuestionRequest {
    private String id;
    private String response;
}
