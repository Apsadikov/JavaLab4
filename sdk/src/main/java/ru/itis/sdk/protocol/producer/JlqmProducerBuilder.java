package ru.itis.sdk.protocol.producer;

import java.net.URI;
import java.net.URISyntaxException;

public class JlqmProducerBuilder {
    private JlqmProducer implementation;
    private String queue;
    private URI uri;

    public JlqmProducerBuilder(String uri) {
        try {
            this.uri = new URI(uri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public JlqmProducerBuilder implementation(JlqmProducer implementation) {
        this.implementation = implementation;
        return this;
    }

    public JlqmProducerBuilder queue(String queue) {
        this.queue = queue;
        return this;
    }

    public JlqmProducer start() {
        implementation.start(uri, queue);
        return implementation;
    }
}
