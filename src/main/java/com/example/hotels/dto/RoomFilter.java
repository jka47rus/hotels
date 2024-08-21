package com.example.hotels.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
public class RoomFilter {
    private UUID id;
    private String name;
    private BigDecimal maxPrice;
    private BigDecimal minPrice;
    private Integer quantityOfPeople;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private UUID hotelId;

    private Integer pageSize;
    private Integer pageNumber;
}
