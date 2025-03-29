package com.nhom07.DAMH_LTUD.controllers;

import com.nhom07.DAMH_LTUD.model.ContactUser;
import com.nhom07.DAMH_LTUD.service.ContactUserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/contact")
@RequiredArgsConstructor
public class ContactController {

    @Autowired
    private ContactUserService contactUserService;

    @GetMapping
    public String showContact(@NotNull Model model)
    {
        model.addAttribute("contact",new ContactUser());
        return "contact/contact";
    }

    @PostMapping("/save_contact")
    public String saveInformationContact(@Valid @ModelAttribute("contact")ContactUser contactUser, BindingResult bindingResult, RedirectAttributes redirectAttributes)
    {
        if(bindingResult.hasErrors())
        {
            return "contact/contact";
        }
        contactUser.setDate_send(LocalDateTime.now());
        contactUserService.addContactUser(contactUser);
        redirectAttributes.addFlashAttribute("successMessage", "Bạn đã gửi thông tin liên hệ thành công!");
        return "redirect:/contact";
    }

}
