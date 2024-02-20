package com.example.toss;

import com.example.toss.dto.PaymentConfirmDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequestMapping("/toss")
public class TossController {
    @PostMapping("/confirm-payment")
    public Object confirmPayment(
            @RequestBody
            PaymentConfirmDto dto
    ) {
        log.info(dto.toString());
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }
}
