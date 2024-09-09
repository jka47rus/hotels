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
    public ResponseEntity<File> downloadFile() {

//        Resource fileResource = new ClassPathResource("files/" + filename);
//        if (!fileResource.exists()) return ResponseEntity.notFound().build();
        File file = new File("booking-info.csv");
        PrintWriter pw = new PrintWriter(file);
        List<String> info = bookingInfoService.saveToFile();
        info.forEach(pw::println);
        pw.close();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "booking-info.csv");
        headers.setContentType(MediaType.TEXT_PLAIN);

        return ResponseEntity.ok()
//                .headers(headers)
                .body(file);
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
