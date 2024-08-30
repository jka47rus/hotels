package com.example.hotels.repository;

import com.example.hotels.dto.filter.RoomFilter;
import com.example.hotels.entity.Booking;
import com.example.hotels.entity.Hotel;
import com.example.hotels.entity.Room;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public interface RoomSpecification {

    static Specification<Room> withFilter(RoomFilter roomFilter) {
        return Specification.where(byRoomId(roomFilter.getId()))
                .and(byRoomName(roomFilter.getName()))
                .and(byRoomCostRange(roomFilter.getMinPrice(), roomFilter.getMaxPrice()))
                .and(byQuantityOfPeople(roomFilter.getQuantityOfPeople()))
                .and(isAvailableBetween(roomFilter.getCheckIn(), roomFilter.getCheckOut()))
                .and(byHotelId(roomFilter.getHotelId()));
    }

    static Specification<Room> byRoomId(UUID roomId) {
        return ((root, query, criteriaBuilder) -> {
            if (roomId == null) return null;
            return criteriaBuilder.equal(root.get(Room.Fields.id), roomId);
        });
    }

    static Specification<Room> byRoomName(String roomName) {
        return ((root, query, criteriaBuilder) -> {
            if (roomName == null) return null;
            return criteriaBuilder.equal(root.get(Room.Fields.name), roomName);
        });
    }

    static Specification<Room> byRoomCostRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return ((root, query, criteriaBuilder) -> {
            if (minPrice == null && maxPrice == null) return null;
            if (minPrice == null)
                return criteriaBuilder.lessThanOrEqualTo(root.get(Room.Fields.price), maxPrice);
            if (maxPrice == null)
                return criteriaBuilder.greaterThanOrEqualTo(root.get(Room.Fields.price), minPrice);
            return criteriaBuilder.between(root.get(Room.Fields.price), minPrice, maxPrice);
        });
    }

    static Specification<Room> byQuantityOfPeople(Integer quantityOfPeople) {
        return ((root, query, criteriaBuilder) -> {
            if (quantityOfPeople == null) return null;
            return criteriaBuilder.equal(root.get(Room.Fields.quantityOfPeople), quantityOfPeople);
        });
    }

    static Specification<Room> byHotelId(UUID hotelId) {
        return ((root, query, criteriaBuilder) -> {
            if (hotelId == null) return null;
            return criteriaBuilder.equal(root.get(Room.Fields.hotel).get(Hotel.Fields.id), hotelId);
        });
    }

    static Specification<Room> isAvailableBetween(LocalDate checkIn, LocalDate checkOut) {

        return ((root, query, criteriaBuilder) -> {
            if (checkIn == null || checkOut == null) return null;

            Subquery<Booking> bookingSubquery = query.subquery(Booking.class);
            Root<Booking> bookingRoot = bookingSubquery.from(Booking.class);

            return criteriaBuilder.not(criteriaBuilder.exists(
                    bookingSubquery.select(bookingRoot).where(
                            criteriaBuilder.equal(bookingRoot.get(Booking.Fields.room), root),
                            criteriaBuilder.lessThan(bookingRoot.get(Booking.Fields.startDate), checkOut),
                            criteriaBuilder.greaterThan(bookingRoot.get(Booking.Fields.endDate), checkIn)
                    )
            ));
        });
    }
}
