package ru.itis.security.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.security.model.Role;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(Authentication authentication) {
        if (authentication != null) {
            String role = ((SimpleGrantedAuthority) authentication.getAuthorities().toArray()[0]).getAuthority();
            if (role.equals(Role.USER.toString())) {
                return "redirect:/user";
            } else {
                return "redirect:/admin";
            }
        }
        return "login";
    }
}
