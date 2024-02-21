package com.example.toss.repo;

import com.example.toss.entity.ItemOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<ItemOrder, Long> {
}
