package com.example.hotels.mapper;


import com.example.hotels.dto.request.UserRequest;
import com.example.hotels.dto.response.UserResponse;
import com.example.hotels.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {

    public UserResponse userToResponse(User user) {
        if (user == null) return null;

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }


    public User fromRequestToUser(UserRequest request) {
        if (request == null) return null;

        return User.builder()
                .id(request.getId())
                .username(request.getUsername())
                .password(request.getPassword())
                .email(request.getEmail())
                .build();
    }


    public List<UserResponse> userListToListResponse(List<User> users) {
        return users.stream().map(this::userToResponse).collect(Collectors.toList());
    }

}

