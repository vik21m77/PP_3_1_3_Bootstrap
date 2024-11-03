package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.UsersDetailService;

import java.security.Principal;

@Controller
@RequestMapping("/")
public class UsersController {

    @GetMapping("/user")
    public String userPage(Model model, @AuthenticationPrincipal User userAuth) {
        model.addAttribute("userAuth", userAuth); // Передаем объект User в модель

        String currentUrl = ServletUriComponentsBuilder.fromCurrentRequest().toUriString();
        model.addAttribute("currentUrl", currentUrl);

        return "user"; // Указывает на Thymeleaf-шаблон user.html
    }
}