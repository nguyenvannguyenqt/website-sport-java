package com.nhom07.DAMH_LTUD.service;

import com.nhom07.DAMH_LTUD.model.SizeProduct;
import com.nhom07.DAMH_LTUD.repository.SizeProductRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Size;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@NoArgsConstructor
public class SizeProductService {
    @Autowired
    private SizeProductRepository sizeProductRepository;

    public List<SizeProduct> getListAllSize()
    {
        return sizeProductRepository.findAll();
    }
}
