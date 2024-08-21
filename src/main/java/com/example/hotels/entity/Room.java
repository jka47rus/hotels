package com.example.hotels.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldNameConstants
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String description;
    private Integer number;
    private BigDecimal price;
    private Integer quantityOfPeople;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @Builder.Default
    @ToString.Exclude
    private List<LocalDate> busyDates = new ArrayList<>();

    public boolean addBusyDates(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> newDates = startDate.datesUntil(endDate.plusDays(1)).toList();

        if (busyDates.stream().anyMatch(newDates::contains)) return false;
        busyDates.addAll(newDates);
        return true;
    }

    public void deleteDates(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> newDates = startDate.datesUntil(endDate).toList();
        if (busyDates.stream().noneMatch(newDates::contains)) return;
        busyDates.removeAll(newDates);
    }


}
