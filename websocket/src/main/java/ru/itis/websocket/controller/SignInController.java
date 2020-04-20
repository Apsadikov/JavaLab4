package ru.itis.websocket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itis.websocket.dto.UserDto;
import ru.itis.websocket.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class SignInController {
    private UserService userService;

    @Autowired
    public SignInController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/sign-in")
    public String getSignIn() {
        return "sign-in";
    }

    @PostMapping("/sign-in")
    public String signIn(HttpServletResponse response, UserDto userDto) {
        userService.signIn(userDto);
        if (userDto.getId() != null) {
            response.addCookie(new Cookie("token", userDto.getToken()));
            return "redirect:/";
        }
        return "redirect:/sign-in";
    }
}
