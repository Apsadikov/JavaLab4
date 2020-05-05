package ru.itis.queue.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.queue.entity.Message;
import ru.itis.queue.entity.Status;
import ru.itis.queue.repository.MessageRepository;

@Controller
public class TestController {
    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/test")
    public String test() {
//        messageRepository.save(Message.builder()
//                .uuid(String.valueOf(Math.random()))
//                .queueName("test")
//                .status(Status.NEW)
//                .body("").build());
        messageRepository.updateStatus(Message.builder()
                .uuid("0.5709435998381408")
                .status(Status.ACCEPTED)
                .build());
        System.out.println(messageRepository.getNewMessage());
        return "test";
    }
}
