package com.nhom07.DAMH_LTUD.controllers;

import com.nhom07.DAMH_LTUD.service.TournamentCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class TournamentCategoryController {
    @Autowired
    private TournamentCategoryService tournamentCategoryService;

    @GetMapping("/category")
    public String showListTournamentCategory(Model model)
    {
        model.addAttribute("brands",tournamentCategoryService.getAllTournamentCategory());
        return "/brands/brands-list";
    }
}
