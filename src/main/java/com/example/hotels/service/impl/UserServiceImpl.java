package com.example.hotels.service.impl;

import com.example.hotels.dto.Filter;
import com.example.hotels.entity.Role;
import com.example.hotels.entity.User;
import com.example.hotels.exception.EntityNotFoundException;
import com.example.hotels.repository.UserRepository;
import com.example.hotels.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;


    @Override
    public List<User> findAll(Filter filter) {
        return userRepository.findAll(PageRequest.of(filter.getPageNumber(), filter.getPageSize())).getContent();

    }

    @Override
    public User save(User user, Role role) {
        user.setRoles(Collections.singletonList(role));
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        role.setUser(user);

        return userRepository.save(user);
    }

    @Override
    public User update(UUID id, User user) {
        User existedUser = findById(user.getId());
        user.setId(existedUser.getId());
        BeanUtils.copyProperties(user, existedUser);
        return userRepository.save(user);
    }

    @Override
    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(MessageFormat
                .format("User with Id {0} not found", id)));
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    @Override
    public boolean existByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException(
                        MessageFormat.format("User with username {0} not found!", username)
                )
        );
    }


}
