package ru.itis.websocket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itis.websocket.dto.UserDto;
import ru.itis.websocket.service.UserService;

@Controller
public class SignUpController {
    private UserService userService;

    @Autowired
    public SignUpController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/sign-up")
    public String getSignUp() {
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String signUp(UserDto userDto) {
        userService.signUp(userDto);
        if (userDto.getId() != null) {
            return "redirect:/sign-up";
        }
        return "redirect:/sign-in";
    }
}
