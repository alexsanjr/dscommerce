package com.alexsanjr.dscommerce.repositories;

import com.alexsanjr.dscommerce.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
