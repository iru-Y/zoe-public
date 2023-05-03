package com.zoe.repositories;

import com.zoe.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    Order findByTracker(String tracker);

    void deleteByTracker(String tracker);
}
