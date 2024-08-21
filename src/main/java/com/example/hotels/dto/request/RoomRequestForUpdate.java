package com.example.hotels.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RoomRequestForUpdate {
    private String name;
    private String description;
    private Integer number;
    private BigDecimal price;
    private Integer quantityOfPeople;
}
