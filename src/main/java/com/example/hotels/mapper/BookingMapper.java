package com.example.hotels.mapper;

import com.example.hotels.dto.request.BookingRequest;
import com.example.hotels.dto.response.BookingResponse;
import com.example.hotels.entity.Booking;
import com.example.hotels.service.RoomService;
import com.example.hotels.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class BookingMapper {
    private final RoomService roomService;
    private final UserService userService;
    private final RoomMapper roomMapper;

    public Booking fromRequestToBooking(BookingRequest request) {
        if (request == null) return null;
        return Booking.builder()
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .user(userService.findById(request.getUserId()))
                .room(roomService.findById(request.getRoomID()))
                .build();
    }

    public BookingResponse fromBookingToResponse(Booking booking) {
        if (booking == null) return null;

        return BookingResponse.builder()
                .id(booking.getId())
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .userName(booking.getUser().getUsername())
                .room(roomMapper.fromRoomToBookingResponse(booking.getRoom()))
                .build();
    }

    public List<BookingResponse> fromAllBookingsToListResponse(List<Booking> bookings) {
        return bookings.stream().map(this::fromBookingToResponse).collect(Collectors.toList());
    }
}
