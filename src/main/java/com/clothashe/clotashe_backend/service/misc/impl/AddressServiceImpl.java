package com.clothashe.clotashe_backend.service.misc.impl;

import com.clothashe.clotashe_backend.exception.ResourceNotFoundException;
import com.clothashe.clotashe_backend.mapper.misc.AddressMapper;
import com.clothashe.clotashe_backend.model.dto.user.AddressDTO;
import com.clothashe.clotashe_backend.model.entity.user.AddressEntity;
import com.clothashe.clotashe_backend.repository.misc.AddressRepository;
import com.clothashe.clotashe_backend.service.misc.AddressService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public AddressServiceImpl(AddressRepository addressRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    @Override
    public AddressDTO create(AddressDTO dto) {
        AddressEntity entity = addressMapper.toEntity(dto);
        return addressMapper.toDto(addressRepository.save(entity));
    }

    @Override
    public AddressDTO update(Long id, AddressDTO dto) {
        AddressEntity existing = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + id));
        AddressEntity updated = addressMapper.toEntity(dto);
        updated.setId(id);
        return addressMapper.toDto(addressRepository.save(updated));
    }

    @Override
    public AddressDTO getById(Long id) {
        return addressRepository.findById(id)
                .map(addressMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + id));
    }

    @Override
    public List<AddressDTO> getAll() {
        return addressRepository.findAll()
                .stream()
                .map(addressMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (!addressRepository.existsById(id)) {
            throw new ResourceNotFoundException("Address not found with id: " + id);
        }
        addressRepository.deleteById(id);
    }
}