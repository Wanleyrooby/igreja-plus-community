package com.igrejaplus.controller;

import com.igrejaplus.dto.ChurchRequest;
import com.igrejaplus.dto.ChurchResponse;
import com.igrejaplus.service.ChurchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/churches")
@RequiredArgsConstructor
public class ChurchController {

    private final ChurchService churchService;

    @PostMapping
    public ResponseEntity<ChurchResponse> register(@RequestBody @Valid ChurchRequest dto) {
        return ResponseEntity.ok(churchService.register(dto));
    }

}
