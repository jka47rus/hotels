package com.example.hotels.controller;

import com.example.hotels.dto.filter.RoomFilter;
import com.example.hotels.dto.request.RoomRequest;
import com.example.hotels.dto.request.RoomRequestForUpdate;
import com.example.hotels.dto.response.RoomResponse;
import com.example.hotels.entity.Room;
import com.example.hotels.exception.AlreadyExistsException;
import com.example.hotels.exception.EntityNotFoundException;
import com.example.hotels.mapper.RoomMapper;
import com.example.hotels.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/room")
public class RoomController {
    private final RoomService roomService;
    private final RoomMapper roomMapper;

    @GetMapping
    public ResponseEntity<List<RoomResponse>> findAll(RoomFilter filter) {
        return ResponseEntity.ok(roomMapper.fromAllRoomsToListResponse(roomService.findAll(filter)));

    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN'")
    public ResponseEntity<RoomResponse> create(@RequestBody RoomRequest request) {
        if (!roomService.existsByHotelId(request.getHotelId())) {
            throw new EntityNotFoundException(MessageFormat
                    .format("Hotel id: {1} does not exists!", request.getHotelId()));
        }
        if (roomService.existsByNumber(request.getHotelId(), request.getNumber())) {
            throw new AlreadyExistsException(MessageFormat
                    .format("Room: {0} in hotel id: {1} already exists!", request.getNumber(), request.getHotelId()));

        }

        Room room = roomMapper.fromRequestToRoom(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(roomMapper.fromRoomToResponse(roomService.saveRoomHotel(room)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(roomMapper.fromRoomToResponse(roomService.findById(id)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN'")
    public ResponseEntity<RoomResponse> update(@PathVariable UUID id,
                                               @RequestBody RoomRequestForUpdate request) {

        Room room = roomService.update(id, roomMapper.fromRequestToRoomUpdate(request));

        return ResponseEntity.ok(roomMapper.fromRoomToResponse(room));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN'")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        roomService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
