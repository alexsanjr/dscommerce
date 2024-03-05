package com.alexsanjr.dscommerce.services;

import com.alexsanjr.dscommerce.entities.User;
import com.alexsanjr.dscommerce.projections.UserDetailsProjection;
import com.alexsanjr.dscommerce.repositories.UserRepository;
import com.alexsanjr.dscommerce.tests.UserDetailsFactory;
import com.alexsanjr.dscommerce.tests.UserFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
public class UserServiceTests {

    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository repository;

    private String existingUsername, nonExistingUsername;
    private User user;
    private List<UserDetailsProjection> userDetails;

    @BeforeEach
    void setUp() throws Exception {
        existingUsername = "maria@gmail.com";
        nonExistingUsername = "user@gmail.com";

        user = UserFactory.createCustomClientUser(1L, existingUsername);
        userDetails = UserDetailsFactory.createCustomClientUser(existingUsername);
    }
}
