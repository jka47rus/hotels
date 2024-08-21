package com.example.hotels.dto.request;

import lombok.Data;

import java.util.UUID;

@Data
public class UserRequest {
    private UUID id;
    private String username;
    private String password;
    private String email;

}
