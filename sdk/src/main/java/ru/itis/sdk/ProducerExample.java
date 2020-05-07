package ru.itis.sdk;

import ru.itis.sdk.dto.Send;
import ru.itis.sdk.protocol.connector.JlqmWebSocketConnector;
import ru.itis.sdk.protocol.producer.JlqmProducer;
import ru.itis.sdk.protocol.producer.JlqmWebSocketProducer;

public class ProducerExample {
    public static void main(String[] args) {
        JlqmProducer jlqmProducer = JlqmWebSocketConnector.connector()
                .url("ws://localhost:8080/queue")
                .connect()
                .producer()
                .implementation(new JlqmWebSocketProducer())
                .queue("test")
                .start();

        new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            jlqmProducer.send(Send.builder().body("first").build());
        }).start();


        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            jlqmProducer.send(Send.builder().body("second").build());
        }).start();


        new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            jlqmProducer.send(Send.builder().body("third").build());
        }).start();
    }
}
