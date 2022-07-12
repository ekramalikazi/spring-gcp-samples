package com.samples.spanner.model;

import com.samples.spanner.model.pk.OrderId;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@EntityListeners(AuditingEntityListener.class)
@Data
@DynamicUpdate
@EqualsAndHashCode(exclude = "items")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {
    @EmbeddedId
    OrderId orderId;

    String description;

    @Column(name = "creation_timestamp")
    @CreatedDate
    LocalDateTime creationTimestamp;

    @Column(name = "created_by")
    @CreatedBy
    String createdBy;

    @Column(name = "lastmodified_timestamp")
    @LastModifiedDate
    LocalDateTime lastmodifiedTimestamp;

    @Column(name = "lastmodified_by")
    @LastModifiedBy
    String lastmodifiedBy;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<OrderItem> items;

}