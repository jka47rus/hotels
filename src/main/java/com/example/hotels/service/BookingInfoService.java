package com.example.hotels.service;

import com.example.hotels.dto.filter.Filter;
import com.example.hotels.entity.BookingInfoMongo;

import java.util.List;

public interface BookingInfoService {

    List<BookingInfoMongo> findAll(Filter filter);

    BookingInfoMongo save(BookingInfoMongo bookingInfo);

    BookingInfoMongo findById(String id);

    void deleteById(String id);

    boolean saveToFile();

}
