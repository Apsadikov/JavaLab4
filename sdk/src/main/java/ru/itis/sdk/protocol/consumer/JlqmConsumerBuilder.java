package ru.itis.sdk.protocol.consumer;

import java.net.URI;
import java.net.URISyntaxException;

public class JlqmConsumerBuilder {
    private JlqmWebSocketConsumer.OnReceive listener;
    private JlqmConsumer implementation;
    private String queue;
    private URI uri;

    public JlqmConsumerBuilder(String uri) {
        try {
            this.uri = new URI(uri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public JlqmConsumerBuilder onReceive(JlqmWebSocketConsumer.OnReceive listener) {
        this.listener = listener;
        return this;
    }

    public JlqmConsumerBuilder implementation(JlqmConsumer implementation) {
        this.implementation = implementation;
        return this;
    }

    public JlqmConsumerBuilder queue(String queue) {
        this.queue = queue;
        return this;
    }

    public JlqmConsumer start() {
        implementation.start(uri, listener, queue);
        return implementation;
    }
}
