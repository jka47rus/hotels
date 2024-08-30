package com.example.hotels.service;


import com.example.hotels.dto.filter.Filter;
import com.example.hotels.entity.Role;
import com.example.hotels.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    List<User> findAll(Filter filter);

    User save(User user, Role role);

    User update(UUID id, User user);

    void deleteById(UUID id);

    User findById(UUID id);

    boolean existsByUsername(String username);

    boolean existByEmail(String email);

    User findByUsername(String username);

}
