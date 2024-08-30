package com.example.hotels.repository_mongo;

import com.example.hotels.dto.kafka.BookingInfo;
import com.example.hotels.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookingInfoRepository extends JpaRepository<BookingInfo, UUID> {
    Page<BookingInfo> findAll(Pageable pageable);
}
