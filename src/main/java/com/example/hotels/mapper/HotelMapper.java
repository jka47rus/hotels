package com.example.hotels.mapper;

import com.example.hotels.dto.request.HotelRequest;
import com.example.hotels.dto.response.HotelResponse;
import com.example.hotels.entity.Hotel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class HotelMapper {

    public Hotel fromRequestToHotel(HotelRequest request) {
        if (request == null) return null;
        return Hotel.builder()
                .name(request.getName())
                .title(request.getTitle())
                .address(request.getAddress())
                .distance(request.getDistance())
                .city(request.getCity())
                .build();
    }

    public HotelResponse fromHotelToResponse(Hotel hotel) {
        if (hotel == null) return null;
        return HotelResponse.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .title(hotel.getTitle())
                .address(hotel.getAddress())
                .city(hotel.getCity())
                .distance(hotel.getDistance())
                .rank(String.format("%.1f", hotel.getRank().getRating()))
                .build();
    }

    public List<HotelResponse> fromAllHotelsToListResponse(List<Hotel> hotels) {
        return hotels.stream().map(this::fromHotelToResponse).collect(Collectors.toList());
    }
}
