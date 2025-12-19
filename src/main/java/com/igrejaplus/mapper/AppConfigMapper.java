package com.igrejaplus.mapper;


import com.igrejaplus.dto.appconfig.AppConfigRequest;
import com.igrejaplus.dto.appconfig.AppConfigResponse;
import com.igrejaplus.model.AppConfig;

public class AppConfigMapper {

    public static AppConfigResponse toResponse(AppConfig config) {
        return new AppConfigResponse(
                config.getChurchName(),
                config.getAddress(),
                config.getPastorName()
        );
    }

    public static void updateEntity(
            AppConfig entity,
            AppConfigRequest request
    ) {
        entity.setChurchName(request.churchName());
        entity.setAddress(request.address());
        entity.setPastorName(request.pastorName());
    }
}
