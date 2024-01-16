package com.alexsanjr.dscommerce.dto;

import com.alexsanjr.dscommerce.entities.Role;
import com.alexsanjr.dscommerce.entities.User;

import java.time.LocalDate;
import java.util.List;

public record UserDTO(Long id, String name, String email, String phone, LocalDate birthDate, List<String> roles) {

    public UserDTO(User user) {
        this(user.getId(), user.getName(), user.getEmail(), user.getPhone(), user.getBirthDate(),
                user.getRoles().stream().map(Role::getAuthority).toList());
    }
}
