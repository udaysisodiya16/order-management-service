package com.capstone.ordermanagementservice.repos;

import com.capstone.ordermanagementservice.models.OrderHistoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderHistoryRepo extends JpaRepository<OrderHistoryModel, Long> {

    List<OrderHistoryModel> findAllByOrderId(Long orderId);

}
