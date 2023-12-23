package com.alexsanjr.dscommerce.services;

import com.alexsanjr.dscommerce.dto.ProductDTO;
import com.alexsanjr.dscommerce.entities.Product;
import com.alexsanjr.dscommerce.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Product product = repository.findById(id).get();
        return modelMapper.map(product, ProductDTO.class);
    }
}
