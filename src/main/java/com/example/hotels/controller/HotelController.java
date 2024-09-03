package com.example.hotels.controller;

import com.example.hotels.dto.filter.HotelFilter;
import com.example.hotels.dto.request.HotelRequest;
import com.example.hotels.dto.response.HotelResponse;
import com.example.hotels.entity.Hotel;
import com.example.hotels.exception.AlreadyExistsException;
import com.example.hotels.mapper.HotelMapper;
import com.example.hotels.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/hotel")
public class HotelController {

    private final HotelService hotelService;
    private final HotelMapper hotelMapper;

    @GetMapping
    public ResponseEntity<List<HotelResponse>> findAll(HotelFilter filter) {
        return ResponseEntity.ok(hotelMapper.fromAllHotelsToListResponse(hotelService.findAll(filter)));

    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<HotelResponse> create(@RequestBody HotelRequest request) {
        if (hotelService.existsByName(request.getName()) && hotelService.existsByCity(request.getCity())) {
            throw new AlreadyExistsException(MessageFormat
                    .format("Hotel: {0} in city: {1} already exists!", request.getName(), request.getCity()));
        }

        Hotel hotel = hotelMapper.fromRequestToHotel(request);
        return ResponseEntity.ok(hotelMapper.fromHotelToResponse(hotelService.save(hotel)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(hotelMapper.fromHotelToResponse(hotelService.findById(id)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<HotelResponse> update(@PathVariable UUID id,
                                                @RequestBody HotelRequest request) {
        Hotel hotel = hotelService.update(id, hotelMapper.fromRequestToHotel(request));

        return ResponseEntity.ok(hotelMapper.fromHotelToResponse(hotel));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        hotelService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/mark/{id}")
    public ResponseEntity<Void> addRank(@PathVariable UUID id,
                                        @RequestParam Integer mark) {
        hotelService.addMark(id, mark);

        return ResponseEntity.noContent().build();
    }
}
