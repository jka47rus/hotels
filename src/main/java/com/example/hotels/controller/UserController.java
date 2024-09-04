package com.example.hotels.controller;


import com.example.hotels.dto.filter.Filter;
import com.example.hotels.dto.kafka.UserInfo;
import com.example.hotels.dto.request.UserRequest;
import com.example.hotels.dto.response.UserResponse;
import com.example.hotels.entity.Role;
import com.example.hotels.entity.RoleType;
import com.example.hotels.entity.User;
import com.example.hotels.exception.AlreadyExistsException;
import com.example.hotels.mapper.UserMapper;
import com.example.hotels.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Value("${app.kafka.usersTopic}")
    private String topicName;
    private final KafkaTemplate<String, UserInfo> kafkaTemplate;

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll(Filter filter) {
        return ResponseEntity.ok(userMapper.userListToListResponse(userService.findAll(filter)));
    }


    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request,
                                                   @RequestParam(value = "role") RoleType role
    ) {
        if (userService.existByEmail(request.getEmail())) {
            throw new AlreadyExistsException(MessageFormat
                    .format("User with email {0} already exists!", request.getEmail()));
        }
        if (userService.existsByUsername(request.getUsername())) {
            throw new AlreadyExistsException(MessageFormat
                    .format("User with username {0} already exists!", request.getUsername()));
        }


        User user = userService.save(userMapper.fromRequestToUser(request), Role.from(role));

        kafkaTemplate.send(topicName, UserInfo.builder()
                .userId(user.getId().toString())
                .build());


        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userMapper.userToResponse(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable UUID id,
                                                   @RequestBody UserRequest request) {
        if (userService.existByEmail(request.getEmail())) {
            throw new AlreadyExistsException(MessageFormat
                    .format("User with email {0} already exists!", request.getEmail()));
        }
        if (userService.existsByUsername(request.getUsername())) {
            throw new AlreadyExistsException(MessageFormat
                    .format("User with username {0} already exists!", request.getUsername()));
        }


        User updatedUser = userService.update(id, userMapper.fromRequestToUser(request));
        return ResponseEntity.ok(userMapper.userToResponse(updatedUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(userMapper.userToResponse(userService.findById(id)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getByUsername(@RequestParam String username) {
        return ResponseEntity.ok(userMapper.userToResponse(userService.findByUsername(username)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
