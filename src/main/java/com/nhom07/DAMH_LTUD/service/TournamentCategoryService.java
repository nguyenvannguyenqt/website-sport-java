package com.nhom07.DAMH_LTUD.service;

import com.nhom07.DAMH_LTUD.NotFoundByIdException;
import com.nhom07.DAMH_LTUD.model.Brand;
import com.nhom07.DAMH_LTUD.model.TournamentCategory;
import com.nhom07.DAMH_LTUD.repository.TournamentCategoryRepository;
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
public class TournamentCategoryService {

    @Autowired
    private TournamentCategoryRepository tournamentCategoryRepository;


    public List<TournamentCategory> getAllTournamentCategory()
    {
        return tournamentCategoryRepository.findAll();
    }

    public TournamentCategory getByIdCategory(Long id)throws NotFoundByIdException
    {
        Optional<TournamentCategory> optional = tournamentCategoryRepository.findById(id);
        if(optional.isPresent())
        {
            return optional.get();
        }
        throw new NotFoundByIdException("khong tim thay id:"+id);
    }
    public void updateTournamentCategory(@NotNull TournamentCategory category)
    {
        TournamentCategory tournamentCategoryExists = tournamentCategoryRepository.findById(category.getId())
                .orElseThrow(()-> new IllegalStateException("category with id:"+category.getId()+"does not exist."));
        tournamentCategoryExists.setNameCategoryTournament(category.getNameCategoryTournament());
        tournamentCategoryRepository.save(tournamentCategoryExists);
    }
    public void addTournamentCategory(TournamentCategory tournamentCategory)
    {
        tournamentCategoryRepository.save(tournamentCategory);
    }
    public void deleteTournamentCategory(Long id)
    {
        if(!tournamentCategoryRepository.existsById(id))
        {
            throw new IllegalStateException("khong tim thay id:"+id);
        }
        tournamentCategoryRepository.deleteById(id);
    }
}
