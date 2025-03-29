package com.nhom07.DAMH_LTUD.service;

import com.nhom07.DAMH_LTUD.NotFoundByIdException;
import com.nhom07.DAMH_LTUD.model.Brand;
import com.nhom07.DAMH_LTUD.repository.BrandRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BrandService {
    @Autowired
    private final BrandRepository repository;


    public List<Brand> getAllBrand()
    {
        return repository.findAll();
    }

    public Brand getByIdBrand(Long id)throws NotFoundByIdException
    {
        Optional<Brand> optional = repository.findById(id);
        if(optional.isPresent())
        {
            return optional.get();
        }
        throw new NotFoundByIdException("khong tim thay id:"+id);
    }
    public void updateBrand(@NotNull Brand brand)
    {
        Brand brandExists = repository.findById(brand.getId())
                .orElseThrow(()-> new IllegalStateException("category with id:"+brand.getId()+"does not exist."));
        brandExists.setName(brand.getName());
        repository.save(brandExists);
    }
    public void addBrand(Brand brand)
    {
        repository.save(brand);
    }
    public void deleteBrand(Long id)
    {
        if(!repository.existsById(id))
        {
            throw new IllegalStateException("khong tim thay id:"+id);
        }
        repository.deleteById(id);
    }
}
