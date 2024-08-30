package com.example.hotels.service;

import com.example.hotels.dto.filter.Filter;
import com.example.hotels.dto.kafka.BookingInfo;
import com.example.hotels.entity.Booking;

import java.util.List;
import java.util.UUID;

public interface BookingInfoService {

    List<BookingInfo> findAll(Filter filter);

    BookingInfo save(BookingInfo bookingInfo);

    BookingInfo findById(UUID id);

    void deleteById(UUID id);

    boolean saveToFile();

}
