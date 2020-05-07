package ru.itis.queue.protocol;

import ru.itis.queue.dto.*;

public interface JlqmServer {
    void subscribe(Subscribe subscribe, Client client);

    void accept(Accept accept);

    void complete(Complete complete);

    void send(Send send);
}
