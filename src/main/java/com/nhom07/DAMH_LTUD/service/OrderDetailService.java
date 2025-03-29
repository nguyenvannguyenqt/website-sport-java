package com.nhom07.DAMH_LTUD.service;

import com.nhom07.DAMH_LTUD.model.Order;
import com.nhom07.DAMH_LTUD.model.OrderDetail;
import com.nhom07.DAMH_LTUD.repository.OrderDetailRepository;
import com.nhom07.DAMH_LTUD.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
@NoArgsConstructor
public class OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public List<OrderDetail> getListOrderDetail()
    {
        return orderDetailRepository.findAll();
    }

    public void save(OrderDetail order)
    {
        orderDetailRepository.save(order);
    }
}
