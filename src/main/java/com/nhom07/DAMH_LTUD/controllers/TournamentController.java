package com.nhom07.DAMH_LTUD.controllers;

import com.nhom07.DAMH_LTUD.service.TournamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tournaments")
@RequiredArgsConstructor
public class TournamentController {
    @Autowired
    private TournamentService tournamentService;

}
