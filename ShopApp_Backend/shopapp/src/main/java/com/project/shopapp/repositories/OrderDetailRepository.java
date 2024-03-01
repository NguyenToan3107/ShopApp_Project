package com.project.shopapp.repositories;

import com.project.shopapp.models.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    @Query("SELECT od FROM OrderDetail od WHERE od.order.id = ?1")
    List<OrderDetail> findByOrderId(Long orderId);
}
