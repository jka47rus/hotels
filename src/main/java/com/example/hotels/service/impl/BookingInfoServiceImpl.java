package com.example.hotels.service.impl;

import com.example.hotels.dto.filter.Filter;
import com.example.hotels.entity.BookingInfoMongo;
import com.example.hotels.repository_mongo.BookingInfoRepository;
import com.example.hotels.service.BookingInfoService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingInfoServiceImpl implements BookingInfoService {
    @Autowired
    private BookingInfoRepository bookingInfoRepository;

    @Value("${app.file-path-booking}")
    private String pathForSave;

    @Override
    public List<BookingInfoMongo> findAll(Filter filter) {

        return bookingInfoRepository.findAll(PageRequest.of(filter.getPageNumber(), filter.getPageSize())).getContent();
    }

    @Override
    public BookingInfoMongo save(BookingInfoMongo bookingInfo) {

        return bookingInfoRepository.save(bookingInfo);
    }

    @Override
    public BookingInfoMongo findById(String id) {
        return bookingInfoRepository.findById(id).get();
    }

    @Override
    public void deleteById(String id) {
        bookingInfoRepository.deleteById(id);
    }

    @Override
    @SneakyThrows
    public List<String> saveToFile() {
        List<BookingInfoMongo> info = bookingInfoRepository.findAll();
        List<String> infoToString = new ArrayList<>();

        info.forEach(inform -> {
            String builder = "User Id: " + inform.getUserId() + " " +
                    "check-in: " + inform.getCheckIn() + " " + "check-out: " + inform.getCheckOut() + ";";
            infoToString.add(builder);
        });

//        if (infoToString.isEmpty()) return false;
//        Files.write(Paths.get(pathForSave), infoToString);
        return infoToString;
    }

}
