package ru.itis.fileloader.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestContoller {
    @GetMapping("/test")
    public ResponseEntity test() {
        System.out.println("test");
        return ResponseEntity.ok().build();
    }
}
