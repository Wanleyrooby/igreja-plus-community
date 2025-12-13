package com.igrejaplus.controller;

import com.igrejaplus.dto.ChurchRequest;
import com.igrejaplus.dto.ChurchResponse;
import com.igrejaplus.service.ChurchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/churches")
@RequiredArgsConstructor
public class ChurchController {

    private final ChurchService churchService;

    @PostMapping
    public ResponseEntity<ChurchResponse> register(@RequestBody @Valid ChurchRequest dto) {
        ChurchResponse response = churchService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChurchResponse> update(@PathVariable Long id, @RequestBody ChurchRequest dto) {
        ChurchResponse response = churchService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ChurchResponse>> findAll() {
        List<ChurchResponse> churchList = churchService.findAll();
        return ResponseEntity.ok(churchList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChurchResponse> findById(@PathVariable Long id) {
        ChurchResponse response = churchService.findById(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        churchService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
