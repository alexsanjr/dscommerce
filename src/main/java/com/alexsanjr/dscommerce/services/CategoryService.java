package com.alexsanjr.dscommerce.services;

import com.alexsanjr.dscommerce.dto.CategoryDTO;
import com.alexsanjr.dscommerce.entities.Category;
import com.alexsanjr.dscommerce.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        List<Category> result = repository.findAll();
        return result.stream().map(obj -> modelMapper.map(obj, CategoryDTO.class)).toList();
    }

}
