package ru.itis.sdk.protocol.connector;

import ru.itis.sdk.protocol.consumer.JlqmConsumerBuilder;
import ru.itis.sdk.protocol.producer.JlqmProducerBuilder;

public class JlqmStompWebSocketConnector implements JlqmConnector {
    private String uri;

    private JlqmStompWebSocketConnector(String uri) {
        this.uri = uri;
    }

    public static JlqmStompWebSocketConnector.Builder connector() {
        return new JlqmStompWebSocketConnector.Builder();
    }

    @Override
    public JlqmConsumerBuilder consumer() {
        return new JlqmConsumerBuilder(uri);
    }

    @Override
    public JlqmProducerBuilder producer() {
        return new JlqmProducerBuilder(uri);
    }

    public static class Builder {
        private String url;

        public JlqmStompWebSocketConnector.Builder url(String url) {
            this.url = url;
            return this;
        }

        public JlqmStompWebSocketConnector connect() {
            return new JlqmStompWebSocketConnector(url);
        }
    }
}
