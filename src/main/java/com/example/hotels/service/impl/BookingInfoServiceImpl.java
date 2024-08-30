package com.example.hotels.service.impl;

import com.example.hotels.dto.filter.Filter;
import com.example.hotels.dto.kafka.BookingInfo;
import com.example.hotels.repository_mongo.BookingInfoRepository;
import com.example.hotels.service.BookingInfoService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BookingInfoServiceImpl implements BookingInfoService {
    private final BookingInfoRepository bookingInfoRepository;

    @Value("${app.file-path}")
    private String pathForSave;
    @Override
    public List<BookingInfo> findAll(Filter filter) {

        return bookingInfoRepository.findAll(PageRequest.of(filter.getPageNumber(), filter.getPageSize())).getContent();
    }

    @Override
    public BookingInfo save(BookingInfo bookingInfo) {
        return bookingInfoRepository.save(bookingInfo);
    }

    @Override
    public BookingInfo findById(UUID id) {
        return bookingInfoRepository.findById(id).get();
    }

    @Override
    public void deleteById(UUID id) {
        bookingInfoRepository.deleteById(id);
    }

    @Override
    @SneakyThrows
    public boolean saveToFile() {
        List<BookingInfo> info = bookingInfoRepository.findAll();
        List<String> infoToString = new ArrayList<>();

        info.forEach(inform->{
            String builder = "User Id: " + inform.getUserId() + "; " +
                    "check-in/out: " + inform.getCheckIn() + "-" + inform.getCheckOut();
            infoToString.add(builder);
        });

        if(infoToString.isEmpty()) return false;
        Files.write(Paths.get(pathForSave), infoToString);
        return true;
    }

}
