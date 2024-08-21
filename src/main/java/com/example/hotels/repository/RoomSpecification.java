package com.example.hotels.repository;

import com.example.hotels.dto.RoomFilter;
import com.example.hotels.entity.Hotel;
import com.example.hotels.entity.Room;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface RoomSpecification {

    static Specification<Room> withFilter(RoomFilter roomFilter){
        return Specification.where(byRoomId(roomFilter.getId()))
                .and(byRoomName(roomFilter.getName()))
                .and(byRoomCostRange(roomFilter.getMinPrice(),roomFilter.getMaxPrice()))
                .and(byQuantityOfPeople(roomFilter.getQuantityOfPeople()))
                .and(byDateRange(roomFilter.getCheckIn(), roomFilter.getCheckOut()))
                .and(byHotelId(roomFilter.getHotelId()));
    }

    static Specification<Room> byRoomId(UUID roomId){
        return ((root, query, criteriaBuilder) -> {
            if(roomId == null) return null;
            return criteriaBuilder.equal(root.get(Room.Fields.id), roomId);
        });
    }

    static Specification<Room> byRoomName(String roomName){
        return ((root, query, criteriaBuilder) -> {
            if(roomName == null) return null;
            return criteriaBuilder.equal(root.get(Room.Fields.name), roomName);
        });
    }

    static Specification<Room> byRoomCostRange(BigDecimal minPrice, BigDecimal maxPrice){
        return ((root, query, criteriaBuilder) -> {
            if(minPrice == null && maxPrice == null) return null;
            if(minPrice == null)
                return criteriaBuilder.lessThanOrEqualTo(root.get(Room.Fields.price), maxPrice);
            if(maxPrice == null)
                return criteriaBuilder.greaterThanOrEqualTo(root.get(Room.Fields.price), minPrice);
            return criteriaBuilder.between(root.get(Room.Fields.price), minPrice, maxPrice);
        });
    }

    static Specification<Room> byQuantityOfPeople(Integer quantityOfPeople){
        return ((root, query, criteriaBuilder) -> {
            if(quantityOfPeople == null) return null;
            return criteriaBuilder.equal(root.get(Room.Fields.quantityOfPeople), quantityOfPeople);
        });
    }

    static Specification<Room> byHotelId(UUID hotelId){
        return ((root, query, criteriaBuilder) -> {
            if(hotelId == null) return null;
            return criteriaBuilder.equal(root.get(Room.Fields.hotel).get(Hotel.Fields.id), hotelId);
        });
    }

    static Specification<Room> byDateRange(LocalDate checkIn, LocalDate checkOut){

        return ((root, query, criteriaBuilder) -> {
            if(checkIn == null && checkOut == null) return null;
            List<LocalDate> searchDates = checkIn.datesUntil(checkOut.plusDays(1)).toList();
            return criteriaBuilder.notEqual(root.get(Room.Fields.busyDates), searchDates);
        });
    }
}
