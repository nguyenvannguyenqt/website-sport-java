package com.nhom07.DAMH_LTUD.controllers;

import com.nhom07.DAMH_LTUD.NotFoundByIdException;
import com.nhom07.DAMH_LTUD.model.Region;
import com.nhom07.DAMH_LTUD.service.RegionService;
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
public class RegionController {
    @Autowired
    private final RegionService regionService;

    @GetMapping("/regions/add")
    public String showFormAdd(Model model)
    {
        model.addAttribute("region",new Region());
        return "/regions/add-region";
    }
    @PostMapping("/regions/add")
    public String saveRegion(@Valid Region region, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            return "/regions/add-region";
        }
        regionService.addRegion(region);
        return "redirect:/regions";
    }

    @GetMapping("/regions")
    public String showListRegion(Model model)
    {
        model.addAttribute("regions",regionService.getAllRegions());
        return "/regions/regions-list";
    }
    @GetMapping("/regions/edit/{id}")
    public String formRegion(@PathVariable("id")Long id, Model model)
    {
        try {
            Region region =  regionService.getById(id);
            model.addAttribute("region", region);
            return "/regions/edit-region";
        } catch (NotFoundByIdException e) {
            return "redirect:/regions";
        }
    }
    @PostMapping("/regions/edit/{id}")
    public String formUpdateRegion(@ModelAttribute("region") Region region, BindingResult result)
    {
        if(result.hasErrors())
        {
            return "/regions/edit-region";
        }
        regionService.updateRegion(region);
        return "redirect:/regions";
    }
}
