package com.samples.spanner;

import com.samples.spanner.model.Order;
import com.samples.spanner.model.pk.OrderId;
import com.samples.spanner.repo.OrderRepository;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import static org.hamcrest.MatcherAssert.assertThat;


@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class AuditTest extends AbstractIntegrationTest {

    @Autowired
    private OrderRepository orderRepository;

    private Order order;

    @BeforeEach
    public void insert() {
        order = new Order();
        order.setOrderId(new OrderId());
        order.setDescription("fake");
        order = orderRepository.save(order);

        assertThat(order.getCreatedBy(), is(notNullValue()));

        assertThat(order.getCreatedBy(), is("Mr. Auditor"));

    }

    @Test
    public void update() {
        LocalDateTime created = order.getCreationTimestamp();
        LocalDateTime modified = order.getLastmodifiedTimestamp();

        order.setDescription("modified");
        orderRepository.save(order);

        orderRepository.findById(order.getOrderId())
                .ifPresent(updatedOrder -> {
                    assertThat(updatedOrder.getDescription(), is("modified"));

                    Assert.assertTrue(updatedOrder.getCreationTimestamp().isEqual(created));

                    Assert.assertTrue(updatedOrder.getLastmodifiedTimestamp().isAfter(modified));
                });
    }
}
