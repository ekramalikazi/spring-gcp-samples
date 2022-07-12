package com.samples.spanner;

import com.samples.spanner.model.Order;
import com.samples.spanner.model.OrderItem;
import com.samples.spanner.service.OrderService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "GCP Spanner Sample Service API", version = "0.0.1", termsOfService = "",
        description = "GCP Spanner Sample Service."))
@Slf4j
public class SpannerApplication {

    @Autowired
    OrderService orderService;

    public static void main(String[] args) {
        SpringApplication.run(SpannerApplication.class, args);
    }

    @Bean
    @Profile("local")
    ApplicationRunner applicationRunner() {
        return (args) -> {
            setUpData();
        };
    }

    private void setUpData() {
        Order order = new Order();
        order.setDescription("hello");

        order.setItems(new ArrayList<>());

        OrderItem i1 = new OrderItem();
        i1.setQuantity(10L);
        i1.setDescription("pen");

        OrderItem i2 = new OrderItem();
        i2.setQuantity(20L);
        i2.setDescription("pencil");

        order.getItems().add(i1);
        order.getItems().add(i2);

        orderService.createOrder(order);

        List<Order> orders = orderService.findOrders();
        orders.forEach(o -> log.info(o.getDescription()));
    }
}
