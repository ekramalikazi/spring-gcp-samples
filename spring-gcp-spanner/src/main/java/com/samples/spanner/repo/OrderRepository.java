package com.samples.spanner.repo;

import com.samples.spanner.model.Order;
import com.samples.spanner.model.pk.OrderId;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order, OrderId> {
}