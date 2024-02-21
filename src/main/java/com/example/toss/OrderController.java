package com.example.toss;

import com.example.toss.dto.ItemOrderDto;
import com.example.toss.dto.PaymentCancelDto;
import com.example.toss.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService service;

    @GetMapping
    public List<ItemOrderDto> readAll() {
        return service.readAll();
    }

    @GetMapping("{id}")
    public ItemOrderDto readOne(
            @PathVariable("id")
            Long id
    ) {
        return service.readOne(id);
    }

    @GetMapping("{id}/payment")
    public Object readTossPayment(
            @PathVariable("id")
            Long id
    ){
        return service.readTossPayment(id);
    }

    @PostMapping("{id}/cancel")
    public Object cancelPayment(
            @PathVariable("id")
            Long id,
            @RequestBody
            PaymentCancelDto dto
    ) {
        return service.cancelPayment(id, dto);
    }
}
