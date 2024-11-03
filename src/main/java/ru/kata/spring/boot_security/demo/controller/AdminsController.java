package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminsController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

//    @GetMapping()
//    public String adminPage() {
//        return "/admin"; // Имя шаблона для админской страницы
//    }

    @GetMapping ()
    public String showAllUsers(Model model, @AuthenticationPrincipal User userAuth) {
        List<User> allUsers = userService.getAllUsers();
        model.addAttribute("allUsers", allUsers);
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("userAuth", userAuth);

        String currentUrl = ServletUriComponentsBuilder.fromCurrentRequest().toUriString();
        model.addAttribute("currentUrl", currentUrl);

        return "admin";
    }

    @PostMapping()
    public String saveNewUser(@ModelAttribute("user") User user) {
        userService.saveOrUpdateUser(user);
        return "redirect:/admin";
    }

    @GetMapping (value = "/addNewUser")
    public String addNewUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleRepository.findAll());
        return "user-addForm.html";
    }
//
//    @PostMapping("/addNewUser")
//    public String saveNewUser(@ModelAttribute("user") User user) {
//        userService.saveOrUpdateUser(user);
//        return "redirect:/admin";
//    }

    @GetMapping(value = "/update")
    public String updateUser(@RequestParam("id") long id, Model model) {
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", roleRepository.findAll());
        return "admin";
    }

    @PostMapping(value = "/delete")
    public String deleteUser(@RequestParam("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

}
