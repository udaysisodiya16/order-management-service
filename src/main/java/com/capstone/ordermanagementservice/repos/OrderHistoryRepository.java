package com.capstone.ordermanagementservice.repos;

import com.capstone.ordermanagementservice.models.OrderHistoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderHistoryRepository extends JpaRepository<OrderHistoryModel, Long> {
}
