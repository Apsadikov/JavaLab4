package ru.itis.sdk.protocol.connector;

import ru.itis.sdk.protocol.consumer.JlqmConsumerBuilder;
import ru.itis.sdk.protocol.producer.JlqmProducerBuilder;

public class JlqmWebSocketConnector implements JlqmConnector {
    private String uri;

    private JlqmWebSocketConnector(String uri) {
        this.uri = uri;
    }

    public static Builder connector() {
        return new Builder();
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

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public JlqmWebSocketConnector connect() {
            return new JlqmWebSocketConnector(url);
        }
    }
}
