package com.example.hotels.service;

import com.example.hotels.dto.filter.Filter;
import com.example.hotels.entity.Booking;

import java.util.List;
import java.util.UUID;

public interface BookingService {
    List<Booking> findAll(Filter filter);

    Booking save(Booking booking);

    Booking findById(UUID id);

    void deleteById(UUID id);


}
