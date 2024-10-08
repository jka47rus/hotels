package com.example.hotels.repository_mongo;

import com.example.hotels.entity.BookingInfoMongo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingInfoRepository extends MongoRepository<BookingInfoMongo, String> {
    Page<BookingInfoMongo> findAll(Pageable pageable);
}
