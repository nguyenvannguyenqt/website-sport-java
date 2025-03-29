package com.nhom07.DAMH_LTUD.service;

import com.nhom07.DAMH_LTUD.model.KhachHang;
import com.nhom07.DAMH_LTUD.repository.KhachHangRepository;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
@NoArgsConstructor
public class KhachHangService {

    @Autowired
    private KhachHangRepository khachHangRepository;


    public List<KhachHang> getAllKhachHang()
    {
        return khachHangRepository.findAll();
    }

    public void save(KhachHang khachHang)
    {
         khachHangRepository.save(khachHang);
    }
}
