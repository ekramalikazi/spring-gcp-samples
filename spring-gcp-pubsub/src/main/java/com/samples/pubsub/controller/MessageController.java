package com.samples.pubsub.controller;

import com.samples.pubsub.service.PublisherService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
public class MessageController {
 
    private final PublisherService.PubsubOutboundGateway messagingGateway;
 
    @PostMapping("/postMessage")
    public ResponseEntity<?> publishMessage(@RequestParam("message") String message) {
        log.info("Sending Message to PubSub {}", message);
        messagingGateway.sendToPubsub(message);
        return new ResponseEntity<>(HttpStatus.OK);
    }
 
}