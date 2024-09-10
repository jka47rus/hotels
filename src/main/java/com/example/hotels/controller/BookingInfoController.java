package com.example.hotels.controller;

import com.example.hotels.dto.filter.Filter;
import com.example.hotels.entity.BookingInfoMongo;
import com.example.hotels.service.BookingInfoService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/booking/info")
public class BookingInfoController {

    private final BookingInfoService bookingInfoService;

    @GetMapping
    public ResponseEntity<List<BookingInfoMongo>> findAll(Filter filter) {
        return ResponseEntity.ok(bookingInfoService.findAll(filter));

    }


    @SneakyThrows
    @GetMapping("/download")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> downloadFile() {

        StringWriter writer = new StringWriter();

        List<String> info = bookingInfoService.saveToFile();
        info.forEach(s-> writer.write(s + "\n"));

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=booking-info.csv");
        headers.setContentType(MediaType.TEXT_PLAIN);

        return ResponseEntity.ok()
                .headers(headers)
                .body(writer.toString());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingInfoMongo> getById(@PathVariable String Id) {
        return ResponseEntity.ok(bookingInfoService.findById(Id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String Id) {
        bookingInfoService.deleteById(Id);
        return ResponseEntity.noContent().build();
    }


}
