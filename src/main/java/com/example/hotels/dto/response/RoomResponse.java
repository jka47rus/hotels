package com.example.hotels.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
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
    @Builder.Default
    private List<String> busyDates = new ArrayList<>();
}
