package com.example.hotels.controller;


import com.example.hotels.dto.filter.Filter;
import com.example.hotels.dto.request.UserRequest;
import com.example.hotels.dto.response.UserResponse;
import com.example.hotels.entity.Role;
import com.example.hotels.entity.RoleType;
import com.example.hotels.entity.User;
import com.example.hotels.exception.AlreadyExistsException;
import com.example.hotels.mapper.UserMapper;
import com.example.hotels.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;
//import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
//    @PreAuthorize("hasAniRoles('USER', 'ADMIN')")
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

        User newUser = userMapper.fromRequestToUser(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userMapper.userToResponse(userService.save(newUser, Role.from(role))));
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
//    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<UserResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(userMapper.userToResponse(userService.findById(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


//    @GetMapping("/name")
//    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
//    public ResponseEntity<String> userGet(@AuthenticationPrincipal UserDetails userDetails) {
//        return ResponseEntity.ok(
//                MessageFormat.format("Called by user: {0}. Role is : {1}", userDetails.getUsername(),
//                        userDetails.getAuthorities().stream()
//                                .map(GrantedAuthority::getAuthority)
//                                .collect(Collectors.joining(","))
//                )
//        );
//    }

}
