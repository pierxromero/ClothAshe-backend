package com.clothashe.clotashe_backend.service.product.impl;

import com.clothashe.clotashe_backend.exception.ResourceNotFoundException;
import com.clothashe.clotashe_backend.mapper.product.ColorMapper;
import com.clothashe.clotashe_backend.model.dto.product.ColorDTO;
import com.clothashe.clotashe_backend.model.entity.product.ColorEntity;
import com.clothashe.clotashe_backend.repository.product.ColorRepository;
import com.clothashe.clotashe_backend.service.product.ColorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ColorServiceImpl implements ColorService {

    private final ColorRepository colorRepository;
    private final ColorMapper colorMapper;

    public ColorServiceImpl(ColorRepository colorRepository, ColorMapper colorMapper) {
        this.colorRepository = colorRepository;
        this.colorMapper = colorMapper;
    }

    @Override
    public ColorDTO create(ColorDTO dto) {
        ColorEntity entity = colorMapper.toEntity(dto);
        return colorMapper.toDto(colorRepository.save(entity));
    }

    @Override
    public ColorDTO update(Long id, ColorDTO dto) {
        ColorEntity existing = colorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Color not found with id: " + id));
        ColorEntity updated = colorMapper.toEntity(dto);
        updated.setId(id);
        return colorMapper.toDto(colorRepository.save(updated));
    }

    @Override
    public ColorDTO getById(Long id) {
        return colorRepository.findById(id)
                .map(colorMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Color not found with id: " + id));
    }

    @Override
    public List<ColorDTO> getAll() {
        return colorRepository.findAll()
                .stream()
                .map(colorMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (!colorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Color not found with id: " + id);
        }
        colorRepository.deleteById(id);
    }
}