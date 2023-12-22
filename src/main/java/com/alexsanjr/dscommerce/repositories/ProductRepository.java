package com.alexsanjr.dscommerce.repositories;

import com.alexsanjr.dscommerce.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
