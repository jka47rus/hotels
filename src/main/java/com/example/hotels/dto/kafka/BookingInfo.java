package com.example.hotels.dto.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingInfo {

    private String userId;
    private LocalDate checkIn;
    private LocalDate checkOut;
}
