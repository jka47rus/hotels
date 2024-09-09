package com.example.hotels.service;

import com.example.hotels.dto.filter.Filter;
import com.example.hotels.entity.BookingInfoMongo;
import org.apache.kafka.clients.Metadata;

import java.util.List;

public interface BookingInfoService {

    List<BookingInfoMongo> findAll(Filter filter);

    BookingInfoMongo save(BookingInfoMongo bookingInfo);

    BookingInfoMongo findById(String id);

    void deleteById(String id);

    List<String> saveToFile();

}
