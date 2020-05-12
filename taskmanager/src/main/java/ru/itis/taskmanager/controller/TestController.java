package ru.itis.taskmanager.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.taskmanager.entity.User;

@RestController
public class TestController {
    @GetMapping("/test")
    public ResponseEntity<User> test() {
        return ResponseEntity.ok(User.builder().id(10L).build());
    }
}
