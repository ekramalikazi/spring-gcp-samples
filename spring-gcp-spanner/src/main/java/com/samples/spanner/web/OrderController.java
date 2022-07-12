package com.samples.spanner.web;

import com.samples.spanner.model.Order;
import com.samples.spanner.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/v1/orders")
    public ResponseEntity<Order> createTutorial(@RequestBody Order order) {
        try {
            Order order1 = orderService.createOrder(order);
            return new ResponseEntity<>(order1, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Failed to create tutorial.", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PatchMapping(value = "/v1/orders/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<Order> updateOrder(@PathVariable String id, @RequestBody JsonPatch patch) {
        try {
            Order order = orderService.findOrderById(id);
            Order orderPatched = applyPatchToOrder(patch, order);
            orderPatched = orderService.updateOrder(orderPatched);
            return new ResponseEntity<>(orderPatched, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Failed to create tutorial.", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Order applyPatchToOrder(
            JsonPatch patch, Order targetOrder) throws JsonPatchException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        JsonNode patched = patch.apply(objectMapper.convertValue(targetOrder, JsonNode.class));
        return objectMapper.treeToValue(patched, Order.class);
    }

    @GetMapping("/v1/orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        try {
            List<Order> orders = orderService.findOrders();

            if (orders.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Failed to get all orders.", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
