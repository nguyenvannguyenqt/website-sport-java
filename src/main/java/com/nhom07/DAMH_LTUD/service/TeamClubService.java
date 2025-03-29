package com.nhom07.DAMH_LTUD.service;

import com.nhom07.DAMH_LTUD.NotFoundByIdException;
import com.nhom07.DAMH_LTUD.model.TeamClub;
import com.nhom07.DAMH_LTUD.repository.TeamClubRepository;
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
public class TeamClubService {

    @Autowired
    private TeamClubRepository teamClubRepository;

    public List<TeamClub> getAllTeamClubs() {
        return teamClubRepository.findAll();
    }

    public TeamClub getById(Long id) throws NotFoundByIdException {
        Optional<TeamClub> optional = teamClubRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new NotFoundByIdException("Không tìm thấy id: " + id);
    }

    public void updateTeamClub(@NotNull TeamClub teamClub) {
        TeamClub teamClubExists = teamClubRepository.findById(teamClub.getId())
                .orElseThrow(() -> new IllegalStateException("TeamClub with id: " + teamClub.getId() + " does not exist."));
        teamClubExists.setNameClub(teamClub.getNameClub());
        // Cập nhật các thuộc tính khác của TeamClub nếu cần
        teamClubRepository.save(teamClubExists);
    }

    public void addTeamClub(TeamClub teamClub) {
        teamClubRepository.save(teamClub);
    }

    public void deleteTeamClub(Long id) {
        if (!teamClubRepository.existsById(id)) {
            throw new IllegalStateException("Không tìm thấy id: " + id);
        }
        teamClubRepository.deleteById(id);
    }
}