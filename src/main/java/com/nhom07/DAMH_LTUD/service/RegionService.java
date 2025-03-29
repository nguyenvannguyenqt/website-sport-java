package com.nhom07.DAMH_LTUD.service;

import com.nhom07.DAMH_LTUD.NotFoundByIdException;
import com.nhom07.DAMH_LTUD.model.Region;
import com.nhom07.DAMH_LTUD.repository.RegionRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
@Transactional
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;

    public List<Region> getAllRegions() {
        return regionRepository.findAll();
    }

    public Region getById(Long id) throws NotFoundByIdException {
        Optional<Region> optional = regionRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new NotFoundByIdException("Không tìm thấy id: " + id);
    }

    public void updateRegion(@NotNull Region region) {
        Region regionExists = regionRepository.findById(region.getId())
                .orElseThrow(() -> new IllegalStateException("Region with id: " + region.getId() + " does not exist."));
        regionExists.setNameRegion(region.getNameRegion());
        regionRepository.save(regionExists);
    }

    public void addRegion(Region region) {
        regionRepository.save(region);
    }

    public void deleteRegion(Long id) {
        if (!regionRepository.existsById(id)) {
            throw new IllegalStateException("Không tìm thấy id: " + id);
        }
        regionRepository.deleteById(id);
    }
}