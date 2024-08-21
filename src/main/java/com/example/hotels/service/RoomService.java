package com.example.hotels.service;

import com.example.hotels.dto.Filter;
import com.example.hotels.dto.RoomFilter;
import com.example.hotels.entity.Room;

import java.util.List;
import java.util.UUID;

public interface RoomService {
    List<Room> findAll(RoomFilter filter);

    Room save(Room room);

    Room justSave(Room room);

    Room update(UUID id, Room room);

//    Room findByNumber(Integer number);

    Room findById(UUID id);

    void deleteById(UUID id);

//    void addDates(UUID roomId, LocalDate startDate, LocalDate endDate);

    boolean existsByNumber(UUID hotelId, Integer roomNumber);

    boolean existsByHotelId(UUID id);

}
