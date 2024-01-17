package com.alexsanjr.dscommerce.core;

import com.alexsanjr.dscommerce.dto.OrderItemDTO;
import com.alexsanjr.dscommerce.dto.UserDTO;
import com.alexsanjr.dscommerce.entities.OrderItem;
import com.alexsanjr.dscommerce.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.createTypeMap(OrderItem.class, OrderItemDTO.class)
                .addMapping(mapper -> mapper.getProduct().getName(),
                        OrderItemDTO::setName);

        modelMapper.createTypeMap(User.class, UserDTO.class)
                .addMapping(User::getRolesName, UserDTO::setRoles);

        return modelMapper;
    }
}
