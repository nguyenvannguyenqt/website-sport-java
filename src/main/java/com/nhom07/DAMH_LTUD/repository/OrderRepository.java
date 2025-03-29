package com.nhom07.DAMH_LTUD.repository;

import com.nhom07.DAMH_LTUD.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
