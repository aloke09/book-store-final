package com.bridgelabz.order_microservice1.repository;

import com.bridgelabz.order_microservice1.external.User;
import com.bridgelabz.order_microservice1.model.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderModel,Long>
{
    List<OrderModel> findAllByUserId(Long userId);

    List<OrderModel> findByUserId(Long id);
}
