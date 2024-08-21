package com.example.hotels.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class HotelFilter {
    private UUID id;
    private String name;
    private String title;
    private String city;
    private String address;
    private Integer distance;
    private Double rating;
    private Integer numberOfRating;

    private Integer pageSize;
    private Integer pageNumber;

}
