package ru.itis.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itis.security.dto.UserDto;
import ru.itis.security.model.Role;
import ru.itis.security.service.UserService;

@Controller
public class SignUpController {
    private UserService userService;

    @Autowired
    public SignUpController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/sign-up")
    @PreAuthorize("permitAll()")
    public String signUp(Authentication authentication) {
        if (authentication != null) {
            String role = ((SimpleGrantedAuthority) authentication.getAuthorities().toArray()[0]).getAuthority();
            if (role.equals(Role.USER.toString())) {
                return "redirect:/user";
            } else {
                return "redirect:/admin";
            }
        }
        return "sign-up";
    }

    @PostMapping("/sign-up")
    @PreAuthorize("permitAll()")
    public void signUp(UserDto userDto) {
        userService.signUp(userDto);
    }
}
