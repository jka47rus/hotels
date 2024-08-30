package com.example.hotels.entity;

import com.example.hotels.exception.AlreadyExistsException;
import com.example.hotels.exception.EntityNotFoundException;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String title;
    private String city;
    private String address;
    private Integer distance;
    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "rating_id")
    private Rating rank;
    @OneToMany(mappedBy = "hotel")
    @Builder.Default
    @ToString.Exclude
    private List<Room> rooms = new ArrayList<>();


    public void addRoom(Room room) {
        if (rooms.contains(room)) throw new AlreadyExistsException(MessageFormat
                .format("In this hotel such room number: {0} already existed!", room.getNumber()));
        rooms.add(room);

    }

    public void deleteRoom(Room room) {
        if (!rooms.contains(room)) throw new EntityNotFoundException(MessageFormat
                .format("In this hotel no such room number: {0}!", room.getNumber()));
        rooms.remove(room);
    }

}
