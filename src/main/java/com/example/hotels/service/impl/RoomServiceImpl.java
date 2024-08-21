package com.example.hotels.service.impl;

import com.example.hotels.dto.Filter;
import com.example.hotels.dto.RoomFilter;
import com.example.hotels.entity.Hotel;
import com.example.hotels.entity.Room;
import com.example.hotels.exception.EntityNotFoundException;
import com.example.hotels.repository.RoomRepository;
import com.example.hotels.repository.RoomSpecification;
import com.example.hotels.service.HotelService;
import com.example.hotels.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final HotelService hotelService;


    @Override
    public List<Room> findAll(RoomFilter filter) {
        return roomRepository.findAll(RoomSpecification.withFilter(filter),
                PageRequest.of(filter.getPageNumber(), filter.getPageSize())).getContent();
    }

    @Override
    public Room save(Room room) {

        Hotel hotel = room.getHotel();
        hotel.addRoom(room);
        hotelService.save(hotel);

        return roomRepository.save(room);
    }

    @Override
    public Room justSave(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public Room update(UUID id, Room room) {
        Room existedRoom = findById(id);
        Hotel hotel = existedRoom.getHotel();
        room.setId(existedRoom.getId());
        room.setHotel(hotel);
        if (!existedRoom.getBusyDates().isEmpty()) room.setBusyDates(existedRoom.getBusyDates());
        if (room.getNumber() != existedRoom.getNumber()) {
            hotel.deleteRoom(existedRoom);
            hotel.addRoom(room);
            hotelService.save(hotel);
        }

        BeanUtils.copyProperties(room, existedRoom);

        return roomRepository.save(room);
    }

//    @Override
//    public Room findByNumber(Integer number) {
//        return roomRepository.findByNumber(number).orElseThrow(() -> new EntityNotFoundException(
//                MessageFormat.format("Room number: {0} not found!", number)));
//    }

    @Override
    public Room findById(UUID id) {
        return roomRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                MessageFormat.format("Room with id: {0} not found!", id)));
    }

    @Override
    public void deleteById(UUID id) {
        Room room = roomRepository.findById(id).get();
        Hotel hotel = room.getHotel();
        hotel.deleteRoom(room);
        hotelService.save(hotel);
        roomRepository.deleteById(id);
    }

//    @Override
//    public void addDates(UUID roomId, LocalDate startDate, LocalDate endDate) {
//        Room room = findById(roomId);
//        room.addBusyDates(startDate, endDate);
//        roomRepository.save(room);
//    }

    @Override
    public boolean existsByNumber(UUID hotelId, Integer roomNumber) {
        Hotel hotel = hotelService.findById(hotelId);

        return hotel.getRooms().stream().anyMatch(s -> s.getNumber() == roomNumber);
    }

    @Override
    public boolean existsByHotelId(UUID id) {
        if (hotelService.findById(id) == null) return false;
        return true;
    }
}
