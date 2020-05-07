package ru.itis.sdk.protocol.connector;

import ru.itis.sdk.protocol.consumer.JlqmConsumerBuilder;
import ru.itis.sdk.protocol.producer.JlqmProducerBuilder;

public interface JlqmConnector {
    JlqmConsumerBuilder consumer();

    JlqmProducerBuilder producer();
}
