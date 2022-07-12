package com.samples.spanner.service;

import com.samples.spanner.model.Order;
import com.samples.spanner.model.pk.OrderId;
import com.samples.spanner.model.pk.OrderItemId;
import com.samples.spanner.repo.OrderItemRepository;
import com.samples.spanner.repo.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;

    OrderService(OrderRepository orderRepository,
                 OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public Order createOrder(Order order) {
        // Use UUID String representation for the ID
        OrderId orderId = new OrderId();

        order.setOrderId(orderId);
        // Set the creation time
        //order.setCreationTimestamp(LocalDateTime.now());

        // Set the parent Order ID and children ID for each item.
        if (order.getItems() != null) {
            order.getItems().stream().forEach(orderItem -> {
                OrderItemId oii = new OrderItemId();
                oii.setOrderId(orderId.getId());
                oii.setOrderItemId(UUID.randomUUID().toString());
                orderItem.setOrderItemId(oii);
            });
        }

        // Children are saved in cascade.
        try {
            return orderRepository.save(order);
        } catch (Exception e) {
            log.error(" ", e);
        }

        return null;
    }


    public Order updateOrder(Order order) {
        // Children are saved in cascade.
        try {
            return orderRepository.save(order);
        } catch (Exception e) {
            log.error(" ", e);
        }

        return null;
    }

    public List<Order> findOrders() {
        return (List<Order>) orderRepository.findAll();
    }

    public Order findOrderById(String id) {
        OrderId oId = new OrderId();
        oId.setId(id);
        return orderRepository.findById(oId)
                .orElseThrow(() ->
                        new RuntimeException("Order Not Found"));
    }
}