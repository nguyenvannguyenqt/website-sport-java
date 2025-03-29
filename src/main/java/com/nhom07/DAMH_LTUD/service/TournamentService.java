package com.nhom07.DAMH_LTUD.service;

import com.nhom07.DAMH_LTUD.NotFoundByIdException;
import com.nhom07.DAMH_LTUD.model.Tournament;
import com.nhom07.DAMH_LTUD.repository.TournamentRepository;
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
public class TournamentService {

    @Autowired
    private TournamentRepository tournamentRepository;

    public List<Tournament> getAllTournaments() {
        return tournamentRepository.findAll();
    }

    public Tournament getById(Long id) throws NotFoundByIdException {
        Optional<Tournament> optional = tournamentRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new NotFoundByIdException("Không tìm thấy id: " + id);
    }

    public void updateTournament(@NotNull Tournament tournament) {
        Tournament tournamentExists = tournamentRepository.findById(tournament.getId())
                .orElseThrow(() -> new IllegalStateException("Tournament with id: " + tournament.getId() + " does not exist."));
        tournamentExists.setName(tournament.getName());
        // Cập nhật các thuộc tính khác của Tournament nếu cần
        tournamentRepository.save(tournamentExists);
    }

    public void addTournament(Tournament tournament) {
        tournamentRepository.save(tournament);
    }

    public void deleteTournament(Long id) {
        if (!tournamentRepository.existsById(id)) {
            throw new IllegalStateException("Không tìm thấy id: " + id);
        }
        tournamentRepository.deleteById(id);
    }

}