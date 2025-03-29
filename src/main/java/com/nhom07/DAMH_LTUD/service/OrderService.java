package com.nhom07.DAMH_LTUD.service;

import com.nhom07.DAMH_LTUD.model.Order;
import com.nhom07.DAMH_LTUD.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
@NoArgsConstructor
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getListOrder()
    {
        return orderRepository.findAll();
    }

    public void save(Order order)
    {
        orderRepository.save(order);
    }
}
