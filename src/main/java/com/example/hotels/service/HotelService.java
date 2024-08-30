package com.example.hotels.service;

import com.example.hotels.dto.filter.HotelFilter;
import com.example.hotels.entity.Hotel;

import java.util.List;
import java.util.UUID;


public interface HotelService {
    List<Hotel> findAll(HotelFilter filter);

    Hotel save(Hotel hotel);

    Hotel update(UUID id, Hotel hotel);

    Hotel findByName(String name);

    Hotel findById(UUID id);

    void deleteById(UUID id);

    void addMark(UUID id, Integer mark);

    boolean existsByName(String name);

    boolean existsByCity(String city);


}
