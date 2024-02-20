package com.example.toss.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ItemOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;

    private String tossPaymentKey;
    private String tossOrderId;
    @Setter
    private String status;

}