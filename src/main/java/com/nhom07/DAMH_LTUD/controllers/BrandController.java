package com.nhom07.DAMH_LTUD.controllers;

import com.nhom07.DAMH_LTUD.NotFoundByIdException;
import com.nhom07.DAMH_LTUD.model.Brand;
import com.nhom07.DAMH_LTUD.service.BrandService;
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
public class BrandController {
    @Autowired
    private final BrandService brandService;


    @GetMapping("/brands/add")
    public String showFormAdd(Model model)
    {
        model.addAttribute("brand",new Brand());
        return "/brands/add-brand";
    }
    @PostMapping("/brands/add")
    public String saveBrand(@Valid Brand brand, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            return "/brands/add-brand";
        }
        brandService.addBrand(brand);
        return "redirect:/brands";
    }

    @GetMapping("/brands")
    public String showListCategory(Model model)
    {
        model.addAttribute("brands",brandService.getAllBrand());
        return "/brands/brands-list";
    }
    @GetMapping("/brands/edit/{id}")
    public String formBrand(@PathVariable("id")Long id, Model model)
    {
        try {
            Brand brand =  brandService.getByIdBrand(id);
            model.addAttribute("brand", brand);
            return "/brands/edit-brand";
        } catch (NotFoundByIdException e) {
            return "redirect:/brands";
        }
    }
    @PostMapping("/brands/edit/{id}")
    public String formUpdateBrand(@ModelAttribute("brand") Brand brand, BindingResult result)
    {
        if(result.hasErrors())
        {
            return "/brands/edit-brand";
        }
        brandService.updateBrand(brand);
        return "redirect:/brands";
    }

}
