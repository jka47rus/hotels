package com.example.hotels.service;

import com.example.hotels.dto.filter.RoomFilter;
import com.example.hotels.entity.Room;

import java.util.List;
import java.util.UUID;

public interface RoomService {
    List<Room> findAll(RoomFilter filter);

    Room saveRoomHotel(Room room);

    Room justSave(Room room);

    Room update(UUID id, Room room);

    Room findById(UUID id);

    void deleteById(UUID id);

    boolean existsByNumber(UUID hotelId, Integer roomNumber);

    boolean existsByHotelId(UUID id);

}
