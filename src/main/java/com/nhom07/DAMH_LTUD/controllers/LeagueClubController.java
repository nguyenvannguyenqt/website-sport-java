package com.nhom07.DAMH_LTUD.controllers;

import com.nhom07.DAMH_LTUD.NotFoundByIdException;
import com.nhom07.DAMH_LTUD.model.LeagueClub;
import com.nhom07.DAMH_LTUD.service.LeagueClubService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class LeagueClubController {
    @Autowired
    private final LeagueClubService leagueClubService;

    @GetMapping("/leagueclubs/add")
    public String showFormAdd(Model model)
    {
        model.addAttribute("leagueClub",new LeagueClub());
        return "/leagueclubs/add-leagueclub";
    }
    @PostMapping("/leagueclubs/add")
    public String saveLeagueClub(@Valid LeagueClub leagueClub, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            return "/leagueclubs/add-leagueclub";
        }
        leagueClubService.addLeagueClub(leagueClub);
        return "redirect:/leagueclubs";
    }

    @GetMapping("/leagueclubs")
    public String showListLeagueClub(Model model)
    {
        model.addAttribute("leagueClubs",leagueClubService.getAllLeagueClubs());
        return "/leagueclubs/leagueclubs-list";
    }
    @GetMapping("/leagueclubs/edit/{id}")
    public String formLeagueClub(@PathVariable("id")Long id, Model model)
    {
        try {
            LeagueClub leagueClub =  leagueClubService.getById(id);
            model.addAttribute("leagueClub", leagueClub);
            return "/leagueclubs/edit-leagueclub";
        } catch (NotFoundByIdException e) {
            return "redirect:/leagueclubs";
        }
    }
    @PostMapping("/leagueclubs/edit/{id}")
    public String formUpdateLeagueClub(@ModelAttribute("leagueClub") LeagueClub leagueClub, BindingResult result)
    {
        if(result.hasErrors())
        {
            return "/leagueclubs/edit-leagueclub";
        }
        leagueClubService.updateLeagueClub(leagueClub);
        return "redirect:/leagueclubs";
    }
}