package ru.itis.stomp.queue.protocol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.stomp.queue.dto.Accept;
import ru.itis.stomp.queue.dto.Complete;
import ru.itis.stomp.queue.dto.Send;
import ru.itis.stomp.queue.entity.Message;
import ru.itis.stomp.queue.entity.Status;
import ru.itis.stomp.queue.service.MessageService;
import ru.itis.stomp.queue.util.UuidGenerator;

@Component
public class JlqmWebSocketServer implements JlqmServer {
    private MessageService messageService;

    @Autowired
    public JlqmWebSocketServer(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void accept(Accept accept) {
        messageService.accept(accept.getUuid());
    }

    @Override
    public void complete(Complete complete) {
        messageService.complete(complete.getUuid());
    }

    @Override
    public void send(Send send) {
        Message newMessage = Message.builder()
                .uuid(UuidGenerator.generate())
                .status(Status.NEW)
                .body(send.getBody())
                .queueName(send.getQueueName())
                .build();
        messageService.save(newMessage);
    }
}
