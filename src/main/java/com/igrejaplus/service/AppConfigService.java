package com.igrejaplus.service;

import com.igrejaplus.dto.appconfig.AppConfigRequest;
import com.igrejaplus.dto.appconfig.AppConfigResponse;
import com.igrejaplus.mapper.AppConfigMapper;
import com.igrejaplus.model.AppConfig;
import com.igrejaplus.repository.AppConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppConfigService {

    private static final Long CONFIG_ID = 1L;

    private final AppConfigRepository appConfigRepository;

    public AppConfigResponse get() {
        AppConfig config = appConfigRepository.findById(CONFIG_ID)
                .orElseGet(() -> {
                    AppConfig newConfig = new AppConfig();
                    newConfig.setId(CONFIG_ID);
                    newConfig.setChurchName("AD Vila Bela");
                    return appConfigRepository.save(newConfig);
                });

        return AppConfigMapper.toResponse(config);
    }

    public AppConfigResponse update(AppConfigRequest dto) {
        AppConfig config = appConfigRepository.findById(CONFIG_ID)
                        .orElseThrow(() -> new RuntimeException("Configuração não encontrada."));

        AppConfigMapper.updateEntity(config, dto);
        appConfigRepository.save(config);

        return AppConfigMapper.toResponse(config);
    }
}
