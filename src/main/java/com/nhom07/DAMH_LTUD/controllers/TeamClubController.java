package com.nhom07.DAMH_LTUD.controllers;

import com.nhom07.DAMH_LTUD.NotFoundByIdException;
import com.nhom07.DAMH_LTUD.model.TeamClub;
import com.nhom07.DAMH_LTUD.service.TeamClubService;
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
public class TeamClubController {
    @Autowired
    private final TeamClubService teamClubService;

    @GetMapping("/teamclubs/add")
    public String showFormAdd(Model model)
    {
        model.addAttribute("teamClub",new TeamClub());
        return "/teamclubs/add-teamclub";
    }
    @PostMapping("/teamclubs/add")
    public String saveTeamClub(@Valid TeamClub teamClub, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            return "/teamclubs/add-teamclub";
        }
        teamClubService.addTeamClub(teamClub);
        return "redirect:/teamclubs";
    }

    @GetMapping("/teamclubs")
    public String showListTeamClub(Model model)
    {
        model.addAttribute("teamClubs", teamClubService.getAllTeamClubs());
        return "/teamclubs/teamclubs-list";
    }
    @GetMapping("/teamclubs/edit/{id}")
    public String formTeamClub(@PathVariable("id")Long id, Model model)
    {
        try {
            TeamClub teamClub =  teamClubService.getById(id);
            model.addAttribute("teamClub", teamClub);
            return "/teamclubs/edit-teamclub";
        } catch (NotFoundByIdException e) {
            return "redirect:/teamclubs";
        }
    }
    @PostMapping("/teamclubs/edit/{id}")
    public String formUpdateTeamClub(@ModelAttribute("teamClub") TeamClub teamClub, BindingResult result)
    {
        if(result.hasErrors())
        {
            return "/teamclubs/edit-teamclub";
        }
        teamClubService.updateTeamClub(teamClub);
        return "redirect:/teamclubs";
    }
}
