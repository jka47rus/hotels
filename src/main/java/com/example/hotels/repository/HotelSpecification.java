package com.example.hotels.repository;

import com.example.hotels.dto.HotelFilter;
import com.example.hotels.entity.Hotel;
import com.example.hotels.entity.Rating;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public interface HotelSpecification {

    static Specification<Hotel> withFilter(HotelFilter hotelFilter) {
        return Specification.where(byHotelId(hotelFilter.getId()))
                .and(byHotelName(hotelFilter.getName()))
                .and(byHotelTitle(hotelFilter.getTitle()))
                .and(byHotelCity(hotelFilter.getCity()))
                .and(byHotelAddress(hotelFilter.getAddress()))
                .and(byHotelDistance(hotelFilter.getDistance()))
                .and(byHotelRank(hotelFilter.getRating()))
                .and(hotelNumberOfRating(hotelFilter.getNumberOfRating()));
    }

    static Specification<Hotel> byHotelId(UUID hotelId) {
        return ((root, query, criteriaBuilder) -> {
            if (hotelId == null) return null;
            return criteriaBuilder.equal(root.get(Hotel.Fields.id), hotelId);

        });
    }

    static Specification<Hotel> byHotelName(String hotelName) {
        return ((root, query, criteriaBuilder) -> {
            if (hotelName == null) return null;
            return criteriaBuilder.equal(root.get(Hotel.Fields.name), hotelName);
        });
    }

    static Specification<Hotel> byHotelTitle(String hotelTitle) {
        return ((root, query, criteriaBuilder) -> {
            if (hotelTitle == null) return null;
            return criteriaBuilder.equal(root.get(Hotel.Fields.title), hotelTitle);
        });
    }

    static Specification<Hotel> byHotelCity(String hotelCity) {
        return ((root, query, criteriaBuilder) -> {
            if (hotelCity == null) return null;
            return criteriaBuilder.equal(root.get(Hotel.Fields.city), hotelCity);
        });
    }

    static Specification<Hotel> byHotelAddress(String hotelAddress) {
        return ((root, query, criteriaBuilder) -> {
            if (hotelAddress == null) return null;
            return criteriaBuilder.equal(root.get(Hotel.Fields.address), hotelAddress);
        });
    }

    static Specification<Hotel> byHotelDistance(Integer hotelDistance) {
        return ((root, query, criteriaBuilder) -> {
            if (hotelDistance == null) return null;
            return criteriaBuilder.lessThanOrEqualTo(root.get(Hotel.Fields.distance), hotelDistance);
        });
    }

    static Specification<Hotel> byHotelRank(Double hotelRating) {
        return ((root, query, criteriaBuilder) -> {
            if (hotelRating == null) return null;
            return criteriaBuilder.greaterThanOrEqualTo(
                    root.get(Hotel.Fields.rank).get(Rating.Fields.rating), hotelRating);
        });
    }

    static Specification<Hotel> hotelNumberOfRating(Integer hotelNumberOfRating) {
        return ((root, query, criteriaBuilder) -> {
            if (hotelNumberOfRating == null) return null;
            return criteriaBuilder.greaterThanOrEqualTo(
                    root.get(Hotel.Fields.rank).get(Rating.Fields.numberOfRating), hotelNumberOfRating);

        });
    }

}
