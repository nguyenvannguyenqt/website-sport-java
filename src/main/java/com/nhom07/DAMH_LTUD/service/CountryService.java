package com.nhom07.DAMH_LTUD.service;

import com.nhom07.DAMH_LTUD.NotFoundByIdException;
import com.nhom07.DAMH_LTUD.model.Country;
import com.nhom07.DAMH_LTUD.repository.CountryRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CountryService {
    @Autowired
    private final CountryRepository repository;

    public List<Country> getAllCountry()
    {
        return repository.findAll();
    }

    public Country getByIdCountry(Long id)throws NotFoundByIdException
    {
        Optional<Country> optional = repository.findById(id);
        if(optional.isPresent())
        {
            return optional.get();
        }
        throw new NotFoundByIdException("khong tim thay id:"+id);
    }
    public void updateCountry(@NotNull Country country)
    {
        Country countryExists = repository.findById(country.getId())
                .orElseThrow(()-> new IllegalStateException("country with id:"+country.getId()+"does not exist."));
        countryExists.setNameCountry(country.getNameCountry());
        repository.save(countryExists);
    }
    public void addCountry(Country country)
    {
        repository.save(country);
    }
    public void deleteCountry(Long id)
    {
        if(!repository.existsById(id))
        {
            throw new IllegalStateException("khong tim thay id:"+id);
        }
        repository.deleteById(id);
    }
}