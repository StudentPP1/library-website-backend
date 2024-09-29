package com.example.udemyfullstackstore.controller;

import com.example.udemyfullstackstore.entity.Payment;
import com.example.udemyfullstackstore.jwt.JWTService;
import com.example.udemyfullstackstore.request.PaymentInfoRequest;
import com.example.udemyfullstackstore.service.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/secure/payment")
public class PaymentController {
    private final PaymentService paymentService;
    private final JWTService jwtService;

    @PostMapping("/payment-intent")
    public ResponseEntity<String> createPaymentIntent(
            @RequestBody PaymentInfoRequest paymentInfoRequest
    ) throws StripeException {
        System.out.println("Payment: ok 1");
        PaymentIntent paymentIntent = paymentService.createPaymentIntent(paymentInfoRequest);
        String paymentStr = paymentIntent.toJson();
        System.out.println("Payment: ok 2");
        return new ResponseEntity<>(paymentStr, HttpStatus.OK);
    }

    @PutMapping("/payment-complete")
    public ResponseEntity<String> stripePaymentComplete(@RequestHeader(value = "Authorization") String token) throws Exception {
        String userEmail = jwtService.getEmail(token.substring(7));
        return paymentService.stripePayment(userEmail);
    }

    @GetMapping("/search/price")
    public Payment searchPrice(@RequestParam String bookId,
                          @RequestHeader(value = "Authorization") String token) {
        String userEmail = jwtService.getEmail(token.substring(7));
        System.out.println("/search/price");
        System.out.println(paymentService.searchPrice(userEmail, bookId));
        return paymentService.searchPrice(userEmail, bookId);
    }
}
