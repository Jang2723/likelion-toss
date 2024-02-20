package com.example.toss.service;

import com.example.toss.dto.PaymentConfirmDto;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange("/payments")
public interface TossHttpService {
    @PostExchange("/confirm")
    Object confirmPayment(@RequestBody PaymentConfirmDto dto);
}
