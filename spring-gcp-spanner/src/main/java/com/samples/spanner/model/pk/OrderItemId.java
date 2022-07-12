package com.samples.spanner.model.pk;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItemId implements Serializable {
    @Column(name = "order_id")
    String orderId;

    @Column(name = "order_item_id")
    String orderItemId;

}