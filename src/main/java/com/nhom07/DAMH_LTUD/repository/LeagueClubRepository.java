package com.nhom07.DAMH_LTUD.repository;

import com.nhom07.DAMH_LTUD.model.LeagueClub;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeagueClubRepository extends JpaRepository<LeagueClub,Long> {
    List<LeagueClub> findByTournamentId(Long tournamentId);
}
