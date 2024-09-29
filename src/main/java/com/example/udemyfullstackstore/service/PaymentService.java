package com.example.udemyfullstackstore.service;

import com.example.udemyfullstackstore.entity.Payment;
import com.example.udemyfullstackstore.repository.PaymentRepository;
import com.example.udemyfullstackstore.request.PaymentInfoRequest;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository,
                          @Value("${stripe.key.secret}") String secretKey) {
        this.paymentRepository = paymentRepository;
        Stripe.apiKey = secretKey;
    }

    public PaymentIntent createPaymentIntent(PaymentInfoRequest request) throws StripeException {
        List<String> paymentMethodTypes = new ArrayList<>();
        paymentMethodTypes.add("card");

        Map<String, Object> params = new HashMap<>();
        params.put("amount", request.getAmount());
        params.put("currency", request.getCurrency());
        params.put("payment_method_types", paymentMethodTypes);

        return  PaymentIntent.create(params);
    }

    // change amount of user to zero after success transaction
    public ResponseEntity<String> stripePayment(String userEmail) throws Exception {
        Payment payment = paymentRepository.findByUserEmail(userEmail);
        if (payment == null) {
            throw new Exception("information is missing");
        }
        payment.setAmount(00.00);
        paymentRepository.save(payment);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public Payment searchPrice(String userEmail, String bookId) {
        Payment payment = paymentRepository.findByUserEmail(userEmail);
        // 10 is book's price (just for example)
        if (payment.getAmount() == 0) {
            payment.setAmount(10);
        }
        return payment;
    }
}
