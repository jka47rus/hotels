package com.example.hotels.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class HotelResponse {
    private UUID id;
    private String name;
    private String title;
    private String city;
    private String address;
    private Integer distance;
    private String rank;
}
