package com.denik.vy.controllers;

import com.denik.vy.models.User;
import com.denik.vy.services.RoleService;
import com.denik.vy.services.UserService;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class homeController {
    private final UserService userService;
    private final RoleService roleService;
    public homeController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/")
    public String start(ModelMap model, Principal principal){
        model.addAttribute("user", userService.findUserByUsername(principal.getName()));
        model.addAttribute("users", userService.users());
        model.addAttribute("roles", roleService.rolesWithoutPrefix());
        model.addAttribute("emptyUser", new User());
        return "index";
    }
    @GetMapping("/profile")
    public String profile(ModelMap model, Principal principal){
        boolean isAdmin = false;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getAuthorities().stream().anyMatch(w->w.getAuthority().equals("ROLE_ADMIN"))) {
            isAdmin = true;
        }
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("user", userService.findUserByUsername(principal.getName()));
        model.addAttribute("users", userService.users());
        return "profile";
    }
    @PostMapping("/admin/create")
    public String create(User user){
        userService.create(user);
        return "redirect:/";
    }
    @PostMapping("/admin/edit")
    public String edit(User user){
        userService.edit(user);
        return "redirect:/";
    }
    @PostMapping("/admin/delete")
    public String delete(@RequestParam("id") long userId){
        userService.delete(userId);
        return "redirect:/";
    }
}
