package com.alexsanjr.dscommerce.services;

import com.alexsanjr.dscommerce.dto.OrderDTO;
import com.alexsanjr.dscommerce.entities.Order;
import com.alexsanjr.dscommerce.entities.User;
import com.alexsanjr.dscommerce.repositories.OrderRepository;
import com.alexsanjr.dscommerce.services.exceptions.ForbiddenException;
import com.alexsanjr.dscommerce.services.exceptions.ResourceNotFoundException;
import com.alexsanjr.dscommerce.tests.OrderFactory;
import com.alexsanjr.dscommerce.tests.UserFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
public class OrderServiceTests {

    @InjectMocks
    private OrderService service;

    @Mock
    private OrderRepository repository;

    @Mock
    private AuthService authService;

    @Spy
    private ModelMapper modelMapper;

    private Long existingOrderId, nonExistingOrderId;
    private Order order;
    private OrderDTO orderDTO;
    private User admin, client;


    @BeforeEach
    void setUp() throws Exception {
        existingOrderId = 1L;
        nonExistingOrderId = 2L;

        admin = UserFactory.createCustomAdminUser(1L, "Jef");
        client = UserFactory.createCustomClientUser(2L, "Bob");

        order = OrderFactory.createOrder(client);
        orderDTO = modelMapper.map(order, OrderDTO.class);

        Mockito.when(repository.findById(existingOrderId)).thenReturn(Optional.of(order));
        Mockito.when(repository.findById(nonExistingOrderId)).thenReturn(Optional.empty());;

    }

    @Test
    public void findByIdShouldReturnOrderDTOWhenIdExistsAndAdminLogged() {
         Mockito.doNothing().when(authService).validateSelfOrAdmin(any());

         OrderDTO result = service.findById(existingOrderId);

         Assertions.assertNotNull(result);
         Assertions.assertEquals(result.getId(), existingOrderId);
    }

    @Test
    public void findByIdShouldReturnOrderDTOWhenIdExistsAndSelfClientLogged() {
        Mockito.doNothing().when(authService).validateSelfOrAdmin(any());

        OrderDTO result = service.findById(existingOrderId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getId(), existingOrderId);
    }

    @Test
    public void findByIdShouldThrowsForbiddenExceptionWhenIdExistsAndOtherClientLogged() {
        Mockito.doThrow(ForbiddenException.class).when(authService).validateSelfOrAdmin(any());

        Assertions.assertThrows(ForbiddenException.class, () -> {
            service.findById(existingOrderId);
        });
    }

    @Test
    public void findByIdShouldThrowsResourceNotFoundExceptionWhenIdDoesNotExists() {
        Mockito.doNothing().when(authService).validateSelfOrAdmin(any());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.findById(nonExistingOrderId);
        });
    }
}
