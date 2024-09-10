package com.example.hotels.controller;

import com.example.hotels.dto.filter.Filter;
import com.example.hotels.entity.UserInfoMongo;
import com.example.hotels.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.StringWriter;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user/info")
public class UserInfoController {

    private final UserInfoService userInfoService;

    @GetMapping
    public ResponseEntity<List<UserInfoMongo>> findAll(Filter filter) {
        return ResponseEntity.ok(userInfoService.findAll(filter));

    }

    @SneakyThrows
    @GetMapping("/download")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> downloadFile() {
        StringWriter writer = new StringWriter();
        List<String> info = userInfoService.saveToFile();
        info.forEach(s -> writer.write(s + "\n"));

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=user-info.csv");
        headers.setContentType(MediaType.TEXT_PLAIN);

        return ResponseEntity.ok()
                .headers(headers)
                .body(writer.toString());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserInfoMongo> getById(@PathVariable String Id) {
        return ResponseEntity.ok(userInfoService.findById(Id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String Id) {
        userInfoService.deleteById(Id);
        return ResponseEntity.noContent().build();
    }
}
