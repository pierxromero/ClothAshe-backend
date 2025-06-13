package com.clothashe.clotashe_backend.service.product.impl;

import com.clothashe.clotashe_backend.exception.products.ColorAlreadyExistsException;
import com.clothashe.clotashe_backend.exception.products.ColorNotFoundException;
import com.clothashe.clotashe_backend.mapper.product.ColorMapper;
import com.clothashe.clotashe_backend.model.dto.product.create.CreateColorRequestDTO;
import com.clothashe.clotashe_backend.model.dto.product.response.ColorResponseDTO;
import com.clothashe.clotashe_backend.model.dto.product.update.UpdateColorRequestDTO;
import com.clothashe.clotashe_backend.model.entity.product.ColorEntity;
import com.clothashe.clotashe_backend.repository.product.ColorRepository;
import com.clothashe.clotashe_backend.service.product.ColorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class ColorServiceImpl implements ColorService {

    private final ColorRepository colorRepository;
    private final ColorMapper colorMapper;

    @Override
    @Transactional
    public ColorResponseDTO create(CreateColorRequestDTO dto) {
        if (colorRepository.existsByNameIgnoreCase(dto.getName())) {
            throw new ColorAlreadyExistsException("A color with the name '" + dto.getName() + "' already exists: ");
        }

        ColorEntity entity = colorMapper.toEntity(dto);
        ColorEntity saved = colorRepository.save(entity);
        return colorMapper.toDto(saved);
    }

    @Override
    @Transactional
    public ColorResponseDTO update(Long id, UpdateColorRequestDTO dto) {
        ColorEntity existing = colorRepository.findById(id)
                .orElseThrow(() -> new ColorAlreadyExistsException("Color not found with id: " + id));

        colorMapper.updateEntityFromDto(dto, existing);
        ColorEntity updated = colorRepository.save(existing);

        return colorMapper.toDto(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public ColorResponseDTO findById(Long id) {
        ColorEntity entity = colorRepository.findById(id)
                .orElseThrow(() -> new ColorNotFoundException("Color not found with id: " + id));
        return colorMapper.toDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ColorResponseDTO> findAll() {
        return colorRepository.findAll()
                .stream()
                .map(colorMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!colorRepository.existsById(id)) {
            throw new ColorNotFoundException("Color not found with id: " + id);
        }
        colorRepository.deleteById(id);
    }
}