package com.example.hotels.repository_mongo;

import com.example.hotels.entity.UserInfoMongo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends MongoRepository<UserInfoMongo, String> {
    Page<UserInfoMongo> findAll(Pageable pageable);
}
