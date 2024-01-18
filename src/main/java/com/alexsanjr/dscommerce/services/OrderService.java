package com.alexsanjr.dscommerce.services;

import com.alexsanjr.dscommerce.dto.OrderDTO;
import com.alexsanjr.dscommerce.dto.OrderItemDTO;
import com.alexsanjr.dscommerce.entities.Order;
import com.alexsanjr.dscommerce.entities.OrderItem;
import com.alexsanjr.dscommerce.entities.OrderStatus;
import com.alexsanjr.dscommerce.entities.Product;
import com.alexsanjr.dscommerce.entities.User;
import com.alexsanjr.dscommerce.repositories.OrderItemRepository;
import com.alexsanjr.dscommerce.repositories.OrderRepository;
import com.alexsanjr.dscommerce.repositories.ProductRepository;
import com.alexsanjr.dscommerce.services.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id) {
        Order order = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado"));
        return modelMapper.map(order, OrderDTO.class);
    }

    @Transactional
    public OrderDTO insert(OrderDTO dto) {
        Order order = new Order();

        order.setMoment(Instant.now());
        order.setStatus(OrderStatus.WAITING_PAYMENT);

        User user = userService.authenticated();
        order.setClient(user);

        for (OrderItemDTO itemDTO : dto.getItems()) {
            Product product = productRepository.getReferenceById(itemDTO.getProductId());
            OrderItem item = new OrderItem(order, product, itemDTO.getQuantity(), product.getPrice());
            order.getItems().add(item);
        }
        order = repository.save(order);
        orderItemRepository.saveAll(order.getItems());

        return modelMapper.map(order, OrderDTO.class);
    }
}
