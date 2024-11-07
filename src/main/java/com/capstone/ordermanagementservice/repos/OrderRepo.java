package com.capstone.ordermanagementservice.repos;

import com.capstone.ordermanagementservice.models.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<OrderModel, Long> {

    List<OrderModel> findByUserIdOrderByCreatedAtDesc(Long userId);

}

