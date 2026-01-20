package com.charlyken.bibliotech.controller;

import com.charlyken.bibliotech.dto.UserDto;
import com.charlyken.bibliotech.exception.BusinessException;
import com.charlyken.bibliotech.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AuthController {
    private final UserService userService;

    @GetMapping("")
    public String home(){
        return "home";
    }

    @GetMapping("/login")
    public String showLoginForm(){
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        model.addAttribute("userDto", new UserDto());
        return "register";
    }

   @PostMapping("/register")
    public String processRegistration(@Valid @ModelAttribute("userDto") UserDto userDto, BindingResult result, Model model){

        if(result.hasErrors()){
            return "register";
        }

        try {
            userService.registrationUser(userDto);
            return "redirect:/login?success=true";
        } catch (BusinessException e) {
            result.rejectValue("username", "error.userDto", e.getMessage());
            return "register";}

    }



}
