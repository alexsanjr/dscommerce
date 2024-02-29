package com.alexsanjr.dscommerce.services;

import com.alexsanjr.dscommerce.dto.CategoryDTO;
import com.alexsanjr.dscommerce.entities.Category;
import com.alexsanjr.dscommerce.repositories.CategoryRepository;
import com.alexsanjr.dscommerce.tests.CategoryFactory;
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

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class CategoryServiceTests {

    @InjectMocks
    private CategoryService service;

    @Mock
    private CategoryRepository repository;

    private Category category;
    private List<Category> list;

    @Spy
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() throws Exception {
        category = CategoryFactory.createCategory();
        list = new ArrayList<>();
        list.add(category);

        Mockito.when(repository.findAll()).thenReturn(list);

    }

    @Test
    public void findAllShouldReturnListCategoryDTO() {
        List<CategoryDTO> result = service.findAll();

        Assertions.assertEquals(result.size(), 1);
        Assertions.assertEquals(result.get(0).getId(), category.getId());

    }
}
