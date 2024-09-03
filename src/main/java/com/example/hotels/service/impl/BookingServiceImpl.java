package com.example.hotels.service.impl;

import com.example.hotels.dto.filter.Filter;
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

        room.getBookings().forEach(book -> {
            boolean notBusy = booking.getStartDate().isBefore(book.getStartDate())
                    && booking.getEndDate().isBefore(book.getStartDate()) ||
                    booking.getStartDate().isAfter(book.getEndDate())
                            && booking.getEndDate().isAfter(book.getEndDate());
            if (!notBusy) throw new AlreadyExistsException("These dates have already booked!");
        });

        room.addBooking(booking);
        bookingRepository.save(booking);
        roomService.justSave(room);
        return booking;
    }

    @Override
    public Booking findById(UUID id) {
        return bookingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                MessageFormat.format("Booking with id {0} not found!", id)));
    }

    @Override
    public void deleteById(UUID id) {
        Booking booking = bookingRepository.findById(id).get();
        Room room = booking.getRoom();
        room.deleteBooking(booking);
        roomService.justSave(room);
        bookingRepository.deleteById(id);
    }

}
