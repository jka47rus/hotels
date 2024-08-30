package com.example.hotels.dto.kafka;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;
@Data
@Builder
public class BookingInfo {

    private UUID userId;
    private LocalDate checkIn;
    private LocalDate checkOut;
}
