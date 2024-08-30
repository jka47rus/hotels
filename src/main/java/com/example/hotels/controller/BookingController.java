package com.example.hotels.controller;

import com.example.hotels.dto.filter.Filter;
//import com.example.hotels.dto.kafka.BookingInfo;
import com.example.hotels.dto.kafka.BookingInfo;
import com.example.hotels.dto.request.BookingRequest;
import com.example.hotels.dto.response.BookingResponse;
import com.example.hotels.entity.Booking;
import com.example.hotels.mapper.BookingMapper;
import com.example.hotels.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/booking")
public class BookingController {
    @Value("${app.kafka.bookingTopic}")
    private String topicName;
    private final KafkaTemplate<String, BookingInfo> kafkaTemplate;

    private final BookingService bookingService;
    private final BookingMapper bookingMapper;



    @GetMapping
    public ResponseEntity<List<BookingResponse>> findAll(Filter filter) {
        return ResponseEntity.ok(bookingMapper.fromAllBookingsToListResponse(bookingService.findAll(filter)));

    }

    @PostMapping
    public ResponseEntity<BookingResponse> create(@RequestBody BookingRequest request) {

        Booking booking = bookingMapper.fromRequestToBooking(request);
        bookingService.save(booking);

        kafkaTemplate.send(topicName, BookingInfo.builder()
                .userId(request.getUserId())
                .checkIn(request.getStartDate())
                .checkOut(request.getEndDate()).build());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookingMapper.fromBookingToResponse(booking));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(bookingMapper.fromBookingToResponse(bookingService.findById(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        bookingService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    //    @PutMapping("/{id}")
//    public ResponseEntity<BookingResponse> update(@PathVariable UUID id,
//                                                  @RequestBody BookingRequest request) {
////        if (roomService.existsByHotelId(request.getHotelId())) {
////            if (roomService.existsByNumber(request.getNumber())) {
////                throw new AlreadyExistsException(MessageFormat
////                        .format("Room: {0} in hotel id: {1} already exists!", request.getNumber(), request.getHotelId()));
////            }
////        }
//        Booking booking = bookingService.update(id, bookingMapper.fromRequestToBooking(request));
//
//        return ResponseEntity.ok(bookingMapper.fromBookingToResponse(booking));
//    }


}
