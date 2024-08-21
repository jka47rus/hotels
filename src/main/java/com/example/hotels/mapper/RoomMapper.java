package com.example.hotels.mapper;

import com.example.hotels.dto.request.RoomRequest;
import com.example.hotels.dto.request.RoomRequestForUpdate;
import com.example.hotels.dto.response.RoomForBookingResponse;
import com.example.hotels.dto.response.RoomResponse;
import com.example.hotels.entity.Room;
import com.example.hotels.service.HotelService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class RoomMapper {

    private final HotelService hotelService;

    public Room fromRequestToRoom(RoomRequest request) {
        if (request == null) return null;
        return Room.builder()
                .name(request.getName())
                .number(request.getNumber())
                .price(request.getPrice())
                .quantityOfPeople(request.getQuantityOfPeople())
                .description(request.getDescription())
                .hotel(hotelService.findById(request.getHotelId()))
                .busyDates(new ArrayList<>())
                .build();
    }

    public Room fromRequestToRoomUpdate(RoomRequestForUpdate request) {
        if (request == null) return null;
        return Room.builder()
                .name(request.getName())
                .number(request.getNumber())
                .price(request.getPrice())
                .quantityOfPeople(request.getQuantityOfPeople())
                .description(request.getDescription())
                .busyDates(new ArrayList<>())
                .build();
    }

    public RoomResponse fromRoomToResponse(Room room) {
        if (room == null) return null;

        return RoomResponse.builder()
                .id(room.getId())
                .name(room.getName())
                .number(room.getNumber())
                .description(room.getDescription())
                .quantityOfPeople(room.getQuantityOfPeople())
                .hotelName(room.getHotel().getName())
                .price(room.getPrice())
                .busyDates(room.getBusyDates())
                .build();
    }

    public List<RoomResponse> fromAllRoomsToListResponse(List<Room> rooms) {
        return rooms.stream().map(this::fromRoomToResponse).collect(Collectors.toList());
    }

    public RoomForBookingResponse fromRoomToBookingResponse(Room room) {
        if (room == null) return null;

        return RoomForBookingResponse.builder()
                .hotelName(room.getHotel().getName())
                .RoomNumber(room.getNumber())
                .build();
    }

}
