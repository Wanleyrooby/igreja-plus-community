package com.igrejaplus.controller;

import com.igrejaplus.dto.appconfig.AppConfigRequest;
import com.igrejaplus.dto.appconfig.AppConfigResponse;
import com.igrejaplus.service.AppConfigService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/config")
@RequiredArgsConstructor
public class AppConfigController {

    private final AppConfigService appConfigService;

    // público (todos podem ver)
    @GetMapping
    public AppConfigResponse get() {
        return appConfigService.get();
    }

    // somente ADMIN
    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public AppConfigResponse update(@RequestBody @Valid AppConfigRequest dto) {
        return appConfigService.update(dto);
    }
}
