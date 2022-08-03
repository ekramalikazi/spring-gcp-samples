package com.samples.pubsub.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.messaging.MessageChannel;

@ExtendWith(MockitoExtension.class)
class PublisherServiceTest {

    @Mock
    private PubSubTemplate pubSubTemplate;

    @Mock
    private MessageChannel messageChannel;

    @InjectMocks
    private PublisherService publisherService;

    @BeforeEach
    private void beforeEach() {
        publisherService = new PublisherService("foo");
    }

}