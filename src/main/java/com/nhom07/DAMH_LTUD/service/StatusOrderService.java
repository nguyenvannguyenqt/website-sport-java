package com.nhom07.DAMH_LTUD.service;

import com.nhom07.DAMH_LTUD.NotFoundByIdException;
import com.nhom07.DAMH_LTUD.model.KhachHang;
import com.nhom07.DAMH_LTUD.model.Region;
import com.nhom07.DAMH_LTUD.model.StatusOrder;
import com.nhom07.DAMH_LTUD.repository.KhachHangRepository;
import com.nhom07.DAMH_LTUD.repository.StatusOrderRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
@NoArgsConstructor
public class StatusOrderService {
    @Autowired
    private StatusOrderRepository statusOrderRepository;


    public List<StatusOrder> getAllStatusOrder()
    {
        return statusOrderRepository.findAll();
    }
    public StatusOrder getStatusOrderById(Long id) throws NotFoundByIdException {
        Optional<StatusOrder> optional = statusOrderRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new NotFoundByIdException("Không tìm thấy id: " + id);
    }

    public void addStatusOrder(StatusOrder statusOrder) {
        statusOrderRepository.save(statusOrder);
    }

    public void deleteStatusOrder(Long id) {
        if (!statusOrderRepository.existsById(id)) {
            throw new IllegalStateException("Không tìm thấy id: " + id);
        }
        statusOrderRepository.deleteById(id);
    }
}
