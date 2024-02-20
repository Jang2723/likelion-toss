package com.example.toss;


import com.example.toss.dto.PaymentConfirmDto;
import com.example.toss.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequestMapping("/toss")
@RequiredArgsConstructor
public class TossController {
    private final OrderService service;

    @PostMapping("/confirm-payment")
    public Object confirmPayment(
            @RequestBody
            PaymentConfirmDto dto,
            @RequestHeader("Authorization")
            String authorization
    ) {
        log.info(authorization);
        log.info("received: {}", dto.toString());
        return service.confirmPayment(dto);
    }
}
