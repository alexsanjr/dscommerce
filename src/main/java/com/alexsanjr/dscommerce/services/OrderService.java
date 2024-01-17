package com.alexsanjr.dscommerce.services;

import com.alexsanjr.dscommerce.dto.OrderDTO;
import com.alexsanjr.dscommerce.entities.Order;
import com.alexsanjr.dscommerce.repositories.OrderRepository;
import com.alexsanjr.dscommerce.services.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id) {
        Order order = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado"));
        return modelMapper.map(order, OrderDTO.class);
    }
}
