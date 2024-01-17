package com.alexsanjr.dscommerce.core;

import com.alexsanjr.dscommerce.dto.OrderItemDTO;
import com.alexsanjr.dscommerce.entities.OrderItem;
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
        return modelMapper;
    }
}
