package ru.itis.queue.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.queue.entity.Message;
import ru.itis.queue.entity.Status;
import ru.itis.queue.repository.MessageRepository;

@Component
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public void save(Message message) {
        messageRepository.save(message);
    }

    @Override
    public void complete(String uuid) {
        messageRepository.updateStatus(Message.builder()
                .uuid(uuid)
                .status(Status.COMPLETED)
                .build());
    }

    @Override
    public void accept(String uuid) {
        messageRepository.updateStatus(Message.builder()
                .uuid(uuid)
                .status(Status.ACCEPTED)
                .build());
    }
}
