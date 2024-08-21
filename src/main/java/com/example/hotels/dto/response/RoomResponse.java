package com.example.hotels.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class RoomResponse {
    private UUID id;
    private String name;
    private String description;
    private Integer number;
    private BigDecimal price;
    private Integer quantityOfPeople;
    private String hotelName;
    private List<LocalDate> busyDates = new ArrayList<>();
}
