package com.example.toss;

import com.example.toss.dto.ItemOrderDto;
import com.example.toss.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
