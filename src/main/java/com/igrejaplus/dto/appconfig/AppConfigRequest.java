package com.igrejaplus.dto.appconfig;

public record AppConfigRequest(
        String churchName,
        String address,
        String pastorName
) {}
