package com.samples.spanner;

import com.samples.spanner.model.Order;
import com.samples.spanner.model.OrderItem;
import com.samples.spanner.repo.OrderRepository;
import com.samples.spanner.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class OrderControllerTest extends AbstractIntegrationTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {

    }

    @Test
    void should_be_able_to_save_one_order() throws Exception {
        // Given
        Order order = setupOrder();

        // When & Then
        mockMvc.perform(post("/api/v1/orders")
                .content(new ObjectMapper().writeValueAsString(order))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description").value(order.getDescription()))
                .andExpect(jsonPath("$.items", hasSize(2))
                );
    }

    private Order setupOrder() {
        Order order = new Order();
        order.setDescription("order-final");

        order.setItems(new ArrayList<>());

        OrderItem i1 = new OrderItem();
        i1.setQuantity(10L);
        i1.setDescription("pen");

        OrderItem i2 = new OrderItem();
        i2.setQuantity(20L);
        i2.setDescription("pencil");

        order.getItems().add(i1);
        order.getItems().add(i2);

        return order;
    }

}
