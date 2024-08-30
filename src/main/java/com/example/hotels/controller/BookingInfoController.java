package com.example.hotels.controller;

import com.example.hotels.dto.filter.Filter;
import com.example.hotels.dto.kafka.BookingInfo;
import com.example.hotels.dto.response.BookingResponse;
import com.example.hotels.service.BookingInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/booking/info")
public class BookingInfoController {

    private final BookingInfoService bookingInfoService;

    @GetMapping
    public ResponseEntity<List<BookingInfo>> findAll(Filter filter) {
        return ResponseEntity.ok(bookingInfoService.findAll(filter));

    }

    @GetMapping("download/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename){
        if(!bookingInfoService.saveToFile()) return ResponseEntity.notFound().build();

        Resource fileResource = new ClassPathResource("files/" + filename);
        if(!fileResource.exists()) return ResponseEntity.notFound().build();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);
        headers.setContentType(MediaType.TEXT_PLAIN);

        return ResponseEntity.ok()
                .headers(headers)
                .body(fileResource);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingInfo> getByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(bookingInfoService.findById(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteByUserId(@PathVariable UUID userId) {
        bookingInfoService.deleteById(userId);
        return ResponseEntity.noContent().build();
    }



}
