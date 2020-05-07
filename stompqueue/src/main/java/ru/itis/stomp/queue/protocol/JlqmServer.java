package ru.itis.stomp.queue.protocol;


import ru.itis.stomp.queue.dto.Accept;
import ru.itis.stomp.queue.dto.Complete;
import ru.itis.stomp.queue.dto.Send;

public interface JlqmServer {
    void accept(Accept accept);

    void complete(Complete complete);

    void send(Send send);
}
