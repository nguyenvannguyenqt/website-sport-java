package com.nhom07.DAMH_LTUD.service;

import com.nhom07.DAMH_LTUD.NotFoundByIdException;
import com.nhom07.DAMH_LTUD.model.LeagueClub;
import com.nhom07.DAMH_LTUD.repository.LeagueClubRepository;
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
public class LeagueClubService {

    @Autowired
    private LeagueClubRepository leagueClubRepository;

    public List<LeagueClub> getAllLeagueClubs() {
        return leagueClubRepository.findAll();
    }

    public LeagueClub getById(Long id) throws NotFoundByIdException {
        Optional<LeagueClub> optional = leagueClubRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new NotFoundByIdException("Không tìm thấy id: " + id);
    }

    public void updateLeagueClub(@NotNull LeagueClub leagueClub) {
        LeagueClub leagueClubExists = leagueClubRepository.findById(leagueClub.getId())
                .orElseThrow(() -> new IllegalStateException("LeagueClub with id: " + leagueClub.getId() + " does not exist."));
        leagueClubExists.setNameLeague(leagueClub.getNameLeague());
        // Cập nhật các thuộc tính khác của LeagueClub nếu cần
        leagueClubRepository.save(leagueClubExists);
    }

    public void addLeagueClub(LeagueClub leagueClub) {
        leagueClubRepository.save(leagueClub);
    }

    public void deleteLeagueClub(Long id) {
        if (!leagueClubRepository.existsById(id)) {
            throw new IllegalStateException("Không tìm thấy id: " + id);
        }
        leagueClubRepository.deleteById(id);
    }
}