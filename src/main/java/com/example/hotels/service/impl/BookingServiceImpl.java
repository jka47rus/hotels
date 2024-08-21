package com.example.hotels.service.impl;

import com.example.hotels.dto.Filter;
import com.example.hotels.entity.Booking;
import com.example.hotels.entity.Room;
import com.example.hotels.exception.AlreadyExistsException;
import com.example.hotels.exception.EntityNotFoundException;
import com.example.hotels.repository.BookingRepository;
import com.example.hotels.service.BookingService;
import com.example.hotels.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final RoomService roomService;

    @Override
    public List<Booking> findAll(Filter filter) {
        return bookingRepository.findAll(PageRequest.of(filter.getPageNumber(), filter.getPageSize())).getContent();
    }

    @Override
    public Booking save(Booking booking) {
        Room room = roomService.findById(booking.getRoom().getId());
        if (!room.addBusyDates(booking.getStartDate(), booking.getEndDate()))
            throw new AlreadyExistsException(MessageFormat
                    .format("These dates {0} - {1} are not acceptable for booking",
                            booking.getStartDate(), booking.getEndDate()));
        roomService.justSave(room);
        return bookingRepository.save(booking);
    }

//    @Override
//    public Booking update(UUID id, Booking booking) {
//
//        Booking existingBooking = findById(id);
//        booking.setId(existingBooking.getId());
//        BeanUtils.copyProperties(booking, existingBooking);
//        return bookingRepository.save(booking);
//    }

    @Override
    public Booking findById(UUID id) {
        return bookingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                MessageFormat.format("Booking with id {0} not found!", id)));
    }

    @Override
    public void deleteById(UUID id) {
        Booking booking = bookingRepository.findById(id).get();
        Room room = booking.getRoom();
        room.deleteDates(booking.getStartDate(), booking.getEndDate());
        roomService.justSave(room);
        bookingRepository.deleteById(id);
    }

//    @Override
//    public Booking findByRoomId(UUID id) {
//        return null;
////        return bookingRepository.findByRoomId(id);
//    }
}
