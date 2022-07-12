package com.samples.spanner.repo;

import com.samples.spanner.model.OrderItem;
import com.samples.spanner.model.pk.OrderItemId;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends PagingAndSortingRepository<OrderItem, OrderItemId> {
}