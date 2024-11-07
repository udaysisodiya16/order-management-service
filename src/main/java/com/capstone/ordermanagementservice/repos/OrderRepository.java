package com.capstone.ordermanagementservice.repos;

import com.capstone.ordermanagementservice.models.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderModel, Long> {
}

