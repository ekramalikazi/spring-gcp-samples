package com.samples.pubsub.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.cloud.gcp.pubsub.integration.AckMode;
import org.springframework.cloud.gcp.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import org.springframework.cloud.gcp.pubsub.support.BasicAcknowledgeablePubsubMessage;
import org.springframework.cloud.gcp.pubsub.support.GcpPubSubHeaders;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import java.nio.charset.StandardCharsets;

@Slf4j
@Configuration
public class SubscriberService {

    private final String subscriptionName;

    public SubscriberService(@Value("${pubsub.subscription.name}") String subscriptionName) {
        this.subscriptionName = subscriptionName;
    }

    /**
     *  Created an inbound channel adapter to listen to the subscription
     *  and send messages to the input message channel.
     */
    @Bean
    public PubSubInboundChannelAdapter messageChannelAdapter(
            @Qualifier("myInputChannel") MessageChannel inputChannel,
            PubSubTemplate pubSubTemplate) {
        PubSubInboundChannelAdapter adapter =
                new PubSubInboundChannelAdapter(pubSubTemplate, subscriptionName);
        adapter.setOutputChannel(inputChannel);
        adapter.setAckMode(AckMode.MANUAL);
        return adapter;
    }

    /**
     * Created a message channel for messages arriving from the subscription.
     */
    @Bean
    public MessageChannel myInputChannel() {
        return new DirectChannel();
    }

    /**
     * Defined what happens to the messages arriving in the message channel.
     */
    @Bean
    @ServiceActivator(inputChannel = "myInputChannel")
    public MessageHandler messageReceiver() {
        return message -> {
            BasicAcknowledgeablePubsubMessage originalMessage =
                    message.getHeaders().get(GcpPubSubHeaders.ORIGINAL_MESSAGE,
                            BasicAcknowledgeablePubsubMessage.class);
            originalMessage.ack();
            processMessage(message);
        };
    }


    public void processMessage(Message<?> message) {
        String messagePayload = new String((byte[]) message.getPayload(), StandardCharsets.UTF_8);
        log.info("Message arrived! [Timestamp= {}, Payload= {}]: ", message.getHeaders().getTimestamp(), messagePayload);
    }

}
