package com.igrejaplus.service;

import com.igrejaplus.dto.ChurchRequest;
import com.igrejaplus.dto.ChurchResponse;
import com.igrejaplus.mapper.ChurchMapper;
import com.igrejaplus.model.Church;
import com.igrejaplus.repository.ChurchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ChurchService {

    private final ChurchRepository churchRepository;

    public ChurchResponse register(ChurchRequest dto) {
        Church church = ChurchMapper.toEntity(dto);
        church = churchRepository.save(church);
        return ChurchMapper.toResponse(church);
    }

    public ChurchResponse update(Long id, ChurchRequest dto) {
        Church church = churchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Igreja não encontrada."));

        ChurchMapper.updateEntity(church, dto);
        church = churchRepository.save(church);

        return ChurchMapper.toResponse(church);
    }

    public List<ChurchResponse> findAll() {
        return churchRepository.findAll()
                .stream()
                .map(ChurchMapper::toResponse)
                .collect(Collectors
                        .toList());
    }

    public ChurchResponse findById(Long id) {
        Church church = churchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Igreja não encontrada."));
        return ChurchMapper.toResponse(church);
    }

    public void delete(Long id) {
        if (!churchRepository.existsById(id)) {
            throw new RuntimeException("Igreja não encontrada.");
        }
        churchRepository.deleteById(id);
    }
}
