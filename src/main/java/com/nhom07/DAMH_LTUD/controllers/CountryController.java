package com.nhom07.DAMH_LTUD.controllers;

import com.nhom07.DAMH_LTUD.NotFoundByIdException;
import com.nhom07.DAMH_LTUD.model.Country;
import com.nhom07.DAMH_LTUD.service.CountryService;
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
public class CountryController {
    @Autowired
    private final CountryService countryService;

    @GetMapping("/countries/add")
    public String showFormAdd(Model model)
    {
        model.addAttribute("country",new Country());
        return "country/add-country"; // corrected the path
    }
    @PostMapping("/countries/add")
    public String saveCountry(@Valid Country country, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            return "/country/add-country";
        }
        countryService.addCountry(country);
        return "redirect:/countries";
    }

    @GetMapping("/countries")
    public String showListCountry(Model model)
    {
        model.addAttribute("countries",countryService.getAllCountry());
        return "country/countrys-list";
    }
    @GetMapping("/countries/edit/{id}")
    public String formCountry(@PathVariable("id")Long id, Model model)
    {
        try {
            Country country =  countryService.getByIdCountry(id);
            model.addAttribute("country", country);
            return "/country/edit-country";
        } catch (NotFoundByIdException e) {
            return "redirect:/countries";
        }
    }
    @PostMapping("/countries/edit/{id}")
    public String formUpdateCountry(@ModelAttribute("country") Country country, BindingResult result)
    {
        if(result.hasErrors())
        {
            return "/country/edit-country";
        }
        countryService.updateCountry(country);
        return "redirect:/countries";
    }
}
