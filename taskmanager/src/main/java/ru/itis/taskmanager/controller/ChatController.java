package ru.itis.taskmanager.controller;

import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.taskmanager.dto.MessageDto;
import ru.itis.taskmanager.entity.User;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ChatController {
    private final List<User> clients = new ArrayList<>();

    @GetMapping("/messages/add")
    public void addMessage() {
        for (User user : clients) {
            synchronized (user) {
                System.out.println("notify");
                user.notifyAll();
            }
        }
    }

    @SneakyThrows
    @RequestMapping(value = "/messages", method = RequestMethod.GET)
    public ResponseEntity<List<MessageDto>> getMessages(Long id) {
        User currentUser = null;
        for (User user : clients) {
            if (user.getId().equals(id)) {
                currentUser = user;
            }
        }
        if (currentUser == null) {
            currentUser = User.builder().id(id).build();
            clients.add(currentUser);
        }
        synchronized (currentUser) {
            System.out.println("wait");
            currentUser.wait();
        }

        List<MessageDto> messages = new ArrayList<>();
        MessageDto message = new MessageDto(1L, "test");
        messages.add(message);
        messages.add(message);
        messages.add(message);

        return ResponseEntity.ok(messages);
    }
}
