package com.example.hotels.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
@FieldNameConstants
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private int totalRating;
    private int numberOfRating;

    private double rating;
    @OneToOne(mappedBy = "rank")
    private Hotel hotel;

}
