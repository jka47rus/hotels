package com.example.hotels.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class BookingResponse {

    private UUID id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String userName;
    private RoomForBookingResponse room;

}
