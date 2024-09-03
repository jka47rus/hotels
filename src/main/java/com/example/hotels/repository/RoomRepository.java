package com.example.hotels.repository;

import com.example.hotels.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface RoomRepository extends JpaRepository<Room, UUID>, JpaSpecificationExecutor<Room> {
//    Page<Room> findAll(Pageable pageable);

//    Optional<Room> findByNumber(Integer number);


}
