package com.samples.spanner.model.pk;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderId implements Serializable {

    @Column(name = "order_id")
    String id;

    public OrderId() {
        this.id = UUID.randomUUID().toString();
    }
}