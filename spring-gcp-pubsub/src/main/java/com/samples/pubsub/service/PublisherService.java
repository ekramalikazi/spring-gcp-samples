package com.samples.pubsub.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.MessageHandler;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.cloud.gcp.pubsub.integration.outbound.PubSubMessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;

@Slf4j
@Configuration
public class PublisherService {

    private final String topicName;

    public PublisherService(@Value("${pubsub.topic.name}") String topicName) {
        this.topicName = topicName;
    }

    @Bean
    @ServiceActivator(inputChannel = "myOutputChannel")
    public MessageHandler messageSender(PubSubTemplate pubsubTemplate) {
        return new PubSubMessageHandler(pubsubTemplate, topicName);
    }

    @MessagingGateway(defaultRequestChannel = "myOutputChannel")
    public interface PubsubOutboundGateway {
        void sendToPubsub(String text);
    }

}
