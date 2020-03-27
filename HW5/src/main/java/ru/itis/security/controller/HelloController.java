package ru.itis.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.security.model.Role;
import ru.itis.security.security.details.UserDetailsImpl;

@Controller
public class HelloController {

    @GetMapping("hello")
    @PreAuthorize("isAuthenticated()")
    public String hello(Authentication authentication) {
        String role = ((SimpleGrantedAuthority) authentication.getAuthorities().toArray()[0]).getAuthority();
        if (role.equals(Role.USER.toString())) {
            return "redirect:/user";
        } else {
            return "redirect:/admin";
        }
    }

    @GetMapping("/user")
    @PreAuthorize("isAuthenticated()")
    public String userHelloPage(Authentication authentication, Model model) {
        model.addAttribute("email", ((UserDetailsImpl) authentication.getPrincipal()).getUsername());
        return "/user";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String adminHelloPage(Authentication authentication, Model model) {
        model.addAttribute("email", ((UserDetailsImpl) authentication.getPrincipal()).getUsername());
        return "/admin";
    }
}
