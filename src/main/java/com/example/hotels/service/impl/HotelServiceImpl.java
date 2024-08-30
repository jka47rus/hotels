package com.example.hotels.service.impl;

import com.example.hotels.dto.filter.HotelFilter;
import com.example.hotels.entity.Hotel;
import com.example.hotels.entity.Rating;
import com.example.hotels.exception.EntityNotFoundException;
import com.example.hotels.repository.HotelRepository;
import com.example.hotels.repository.HotelSpecification;
import com.example.hotels.repository.RatingRepository;
import com.example.hotels.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final RatingRepository ratingRepository;

    @Override
    public List<Hotel> findAll(HotelFilter filter) {
        return hotelRepository.findAll(HotelSpecification.withFilter(filter),
                PageRequest.of(
                        filter.getPageNumber(), filter.getPageSize()
                )).getContent();
    }

    @Override
    public Hotel save(Hotel hotel) {
        Rating rating = new Rating();
        rating.setNumberOfRating(0);
        rating.setTotalRating(0);
        rating.setRating(0.0);
        hotel.setRank(rating);
        ratingRepository.save(rating);
        return hotelRepository.save(hotel);
    }

    @Override
    public Hotel update(UUID id, Hotel hotel) {
        Hotel existedHotel = findById(id);
        hotel.setId(existedHotel.getId());
        BeanUtils.copyProperties(hotel, existedHotel);
        return hotelRepository.save(hotel);
    }

    @Override
    public Hotel findByName(String name) {
        return hotelRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException(MessageFormat
                .format("Hotel with name: {0} not found!", name)));
    }

    @Override
    public Hotel findById(UUID id) {
        return hotelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(MessageFormat
                .format("Hotel with id: {0} not found!", id)));
    }

    @Override
    public void deleteById(UUID id) {
        hotelRepository.deleteById(id);
    }

    @Override
    public void addMark(UUID id, Integer mark) {
        Hotel hotel = findById(id);

        Rating existedRating = hotel.getRank();
        Rating rating = new Rating();
        rating.setTotalRating(existedRating.getTotalRating() + mark);
        rating.setNumberOfRating(existedRating.getNumberOfRating() + 1);
        rating.setRating((double) rating.getTotalRating() / rating.getNumberOfRating());

        hotel.setRank(rating);
        ratingRepository.save(rating);
        hotelRepository.save(hotel);

    }

    @Override
    public boolean existsByName(String name) {
        return hotelRepository.findByName(name).isPresent();
    }

    @Override
    public boolean existsByCity(String city) {
        return hotelRepository.findByCity(city).isPresent();
    }
}
