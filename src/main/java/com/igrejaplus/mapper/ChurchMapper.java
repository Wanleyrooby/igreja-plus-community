package com.igrejaplus.mapper;

import com.igrejaplus.dto.ChurchRequest;
import com.igrejaplus.dto.ChurchResponse;
import com.igrejaplus.model.Church;

import java.time.Instant;

public class ChurchMapper {

    public static Church toEntity(ChurchRequest dto) {
        return Church.builder()
                .name(dto.name())
                .slug(dto.slug())
                .plan(dto.plan())
                .createdAt(Instant.now())
                .build();
    }

    public static void updateEntity(Church church, ChurchRequest dto) {
        church.setName(dto.name());
        church.setSlug(dto.slug());
        church.setPlan(dto.plan());
    }

    public static ChurchResponse toResponse(Church church) {
        return new ChurchResponse(
                church.getId(),
                church.getName(),
                church.getSlug(),
                church.getPlan(),
                church.getCreatedAt()
        );
    }
}
