package com.samples.pubsub.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.cloud.gcp.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.util.ReflectionTestUtils;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SubscriberServiceTest {

    @Mock
    private PubSubTemplate pubSubTemplate;

    @Mock
    private MessageChannel messageChannel;

    @InjectMocks
    private SubscriberService subscriberService;

    @BeforeEach
    private void beforeEach() {
        subscriberService = new SubscriberService("ekram-example-topic-us-west1-subscription");
        ReflectionTestUtils.setField(subscriberService, "subscriptionName", "ekram-example-topic-us-west1-subscription");
    }

    @Test
    public void messageChannelAdapter() {
        PubSubInboundChannelAdapter pubSubInboundChannelAdapter = subscriberService.messageChannelAdapter(messageChannel, pubSubTemplate);
        assertNotNull(pubSubInboundChannelAdapter);
    }

    @Test
    public void pubsubInputChannel() {
        DirectChannel directChannel = (DirectChannel) subscriberService.myInputChannel();
        assertNotNull(directChannel);
    }

    @Test
    public void messageReceiver() {
        MessageHandler messageHandler = subscriberService.messageReceiver();
        assertNotNull(messageHandler);
    }

    @Test
    public void testSubscriptionMessage() {
        String messagePayload = "Hello World!";
        MessageChannel replies = new DirectChannel();
        Message<?> mockMessage = MessageBuilder.withPayload(messagePayload.getBytes(StandardCharsets.UTF_8))
                .setReplyChannel(replies)
                .build();
        subscriberService.processMessage(mockMessage);
    }

}