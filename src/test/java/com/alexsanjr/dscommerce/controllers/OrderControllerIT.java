package com.alexsanjr.dscommerce.controllers;

import com.alexsanjr.dscommerce.dto.OrderDTO;
import com.alexsanjr.dscommerce.dto.ProductDTO;
import com.alexsanjr.dscommerce.entities.Category;
import com.alexsanjr.dscommerce.entities.Order;
import com.alexsanjr.dscommerce.entities.OrderItem;
import com.alexsanjr.dscommerce.entities.OrderStatus;
import com.alexsanjr.dscommerce.entities.Product;
import com.alexsanjr.dscommerce.entities.User;
import com.alexsanjr.dscommerce.tests.ProductFactory;
import com.alexsanjr.dscommerce.tests.TokenUtil;
import com.alexsanjr.dscommerce.tests.UserFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class OrderControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TokenUtil tokenUtil;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ModelMapper modelMapper;
    private String clientUsername, clientPassword, adminUsername, adminPassword;
    private String adminToken, clientToken, invalidToken;
    private Long existingOrderId, nonExistingOrderId;
    private Order order;
    private OrderDTO orderDTO;
    private User user;

    @BeforeEach
    void setUp() throws Exception {
        clientUsername = "maria@gmail.com";
        clientPassword = "123456";
        adminUsername = "alex@gmail.com";
        adminPassword = "123456";

        existingOrderId = 1L;
        nonExistingOrderId = 100L;

        adminToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);
        clientToken = tokenUtil.obtainAccessToken(mockMvc, clientUsername, clientPassword);
        invalidToken = adminToken + "xpto"; // Simulates invalid token

        user = UserFactory.createClientUser();
        order = new Order(null, Instant.now(), OrderStatus.WAITING_PAYMENT, user, null);

        Product product = ProductFactory.createProduct();
        OrderItem orderItem = new OrderItem(order, product, 2, 10.0);
        order.getItems().add(orderItem);
    }

    @Test
    public void findByIdShouldReturnOrderDTOWhenIdExistsAndAdminLogged() throws Exception {
        ResultActions result = mockMvc
                .perform(get("/orders/{id}", existingOrderId)
                        .header("Authorization","Bearer " + adminToken)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print());

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").value(existingOrderId));
        result.andExpect(jsonPath("$.moment").value("2022-07-25T13:00:00Z"));
        result.andExpect(jsonPath("$.status").value("PAID"));
        result.andExpect(jsonPath("$.client").exists());
        result.andExpect(jsonPath("$.payment").exists());
        result.andExpect(jsonPath("$.items").exists());
        result.andExpect(jsonPath("$.total").exists());

    }

    @Test
    public void findByIdShouldReturnOrderDTOWhenIdExistsAndClientLogged() throws Exception {
        ResultActions result = mockMvc
                .perform(get("/orders/{id}", existingOrderId)
                        .header("Authorization","Bearer " + clientToken)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").value(existingOrderId));
        result.andExpect(jsonPath("$.moment").value("2022-07-25T13:00:00Z"));
        result.andExpect(jsonPath("$.status").value("PAID"));
        result.andExpect(jsonPath("$.client").exists());
        result.andExpect(jsonPath("$.payment").exists());
        result.andExpect(jsonPath("$.items").exists());
        result.andExpect(jsonPath("$.total").exists());

    }

    @Test
    public void findByIdShouldReturnForbiddenWhenIdExistsAndClientLoggedAndOrderDoesNotBelongUser() throws Exception {
        Long otherOrderId = 2L;

        ResultActions result = mockMvc
                .perform(get("/orders/{id}", otherOrderId)
                        .header("Authorization","Bearer " + clientToken)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isForbidden());
    }

    @Test
    public void findByIdShouldReturnNotFoundWhenIdDoesNotExistsAndAdminLogged() throws Exception {
        ResultActions result = mockMvc
                .perform(get("/orders/{id}", nonExistingOrderId)
                        .header("Authorization","Bearer " + adminToken)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void findByIdShouldReturnNotFoundWhenIdDoesNotExistsAndClientLogged() throws Exception {
        ResultActions result = mockMvc
                .perform(get("/orders/{id}", nonExistingOrderId)
                        .header("Authorization","Bearer " + clientToken)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }


    @Test
    public void findByIdShouldReturnUnauthorizedWhenIdExistsAndInvalidToken() throws Exception {
        ResultActions result = mockMvc
                .perform(get("/orders/{id}", existingOrderId)
                        .header("Authorization","Bearer " + invalidToken)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isUnauthorized());
    }
}
