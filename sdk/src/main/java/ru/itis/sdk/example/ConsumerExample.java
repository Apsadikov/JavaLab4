package ru.itis.sdk.example;

import ru.itis.sdk.dto.Accept;
import ru.itis.sdk.dto.Complete;
import ru.itis.sdk.protocol.connector.JlqmWebSocketConnector;
import ru.itis.sdk.protocol.consumer.JlqmWebSocketConsumer;

public class ConsumerExample {
    public static void main(String[] args) throws InterruptedException {
        /*
         * StompWebSocket example
         */
//        JlqmStompWebSocketConnector.connector()
//                .url("ws://localhost:8080/queue")
//                .connect()
//                .consumer()
//                .implementation(new JlqmStompWebSocketConsumer())
//                .queue("test")
//                .onReceive((message, consumer) -> {
//                    System.out.println("ACCEPT: " + message.getUuid());
//                    consumer.accept(Accept.builder()
//                            .uuid(message.getUuid())
//                            .build());
//                    System.out.println("MESSAGE: " + message.getBody());
//                    System.out.println("COMPLETE: " + message.getUuid());
//                    consumer.complete(Complete.builder()
//                            .uuid(message.getUuid())
//                            .build());
//                })
//                .start();
        JlqmWebSocketConnector.connector()
                .url("ws://localhost:8080/queue")
                .connect()
                .consumer()
                .implementation(new JlqmWebSocketConsumer())
                .queue("test")
                .onReceive((message, consumer) -> {
                    System.out.println("ACCEPT: " + message.getUuid());
                    consumer.accept(Accept.builder()
                            .uuid(message.getUuid())
                            .build());
                    System.out.println("MESSAGE: " + message.getBody());
                    System.out.println("COMPLETE: " + message.getUuid());
                    consumer.complete(Complete.builder()
                            .uuid(message.getUuid())
                            .build());
                })
                .start();
    }
}
