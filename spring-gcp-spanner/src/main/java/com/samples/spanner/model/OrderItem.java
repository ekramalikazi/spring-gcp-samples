package com.samples.spanner.model;

import com.samples.spanner.model.pk.OrderItemId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "order_items")
@Data
@DynamicUpdate
@EqualsAndHashCode(exclude = "order")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItem {
    @EmbeddedId
    OrderItemId orderItemId;

    String description;
    Long quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    @JsonIgnore
    Order order;
}
