package com.example.hotels.dto.request;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class BookingRequest {

    private LocalDate startDate;
    private LocalDate endDate;
    private UUID roomID;
    private UUID userId;
}
