package com.alexsanjr.dscommerce.repositories;

import com.alexsanjr.dscommerce.entities.OrderItem;
import com.alexsanjr.dscommerce.entities.OrderItemPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {

}
