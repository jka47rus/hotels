package com.example.hotels.controller;

import com.example.hotels.dto.filter.Filter;
import com.example.hotels.entity.BookingInfoMongo;
import com.example.hotels.service.BookingInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/save")
    public ResponseEntity<Void> saveToFile() {
        if (!bookingInfoService.saveToFile()) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/download/{filename}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {

        Resource fileResource = new ClassPathResource("files/" + filename);
        if (!fileResource.exists()) return ResponseEntity.notFound().build();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);
        headers.setContentType(MediaType.TEXT_PLAIN);

        return ResponseEntity.ok()
                .headers(headers)
                .body(fileResource);
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
