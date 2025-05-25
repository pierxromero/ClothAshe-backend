package com.clothashe.clotashe_backend.service.misc.impl;

import com.clothashe.clotashe_backend.exception.ResourceNotFoundException;
import com.clothashe.clotashe_backend.mapper.misc.FavoriteProductMapper;
import com.clothashe.clotashe_backend.model.dto.user.FavoriteProductDTO;
import com.clothashe.clotashe_backend.model.entity.user.FavoriteProductEntity;
import com.clothashe.clotashe_backend.repository.misc.FavoriteProductRepository;
import com.clothashe.clotashe_backend.service.misc.FavoriteProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteProductServiceImpl implements FavoriteProductService {

    private final FavoriteProductRepository favoriteProductRepository;
    private final FavoriteProductMapper favoriteProductMapper;

    public FavoriteProductServiceImpl(FavoriteProductRepository favoriteProductRepository, FavoriteProductMapper favoriteProductMapper) {
        this.favoriteProductRepository = favoriteProductRepository;
        this.favoriteProductMapper = favoriteProductMapper;
    }

    @Override
    public FavoriteProductDTO create(FavoriteProductDTO dto) {
        FavoriteProductEntity entity = favoriteProductMapper.toEntity(dto);
        return favoriteProductMapper.toDto(favoriteProductRepository.save(entity));
    }

    @Override
    public FavoriteProductDTO update(Long id, FavoriteProductDTO dto) {
        FavoriteProductEntity existing = favoriteProductRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FavoriteProduct not found with id: " + id));
        FavoriteProductEntity updated = favoriteProductMapper.toEntity(dto);
        updated.setId(id);
        return favoriteProductMapper.toDto(favoriteProductRepository.save(updated));
    }

    @Override
    public FavoriteProductDTO getById(Long id) {
        return favoriteProductRepository.findById(id)
                .map(favoriteProductMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("FavoriteProduct not found with id: " + id));
    }

    @Override
    public List<FavoriteProductDTO> getAll() {
        return favoriteProductRepository.findAll()
                .stream()
                .map(favoriteProductMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (!favoriteProductRepository.existsById(id)) {
            throw new ResourceNotFoundException("FavoriteProduct not found with id: " + id);
        }
        favoriteProductRepository.deleteById(id);
    }
}