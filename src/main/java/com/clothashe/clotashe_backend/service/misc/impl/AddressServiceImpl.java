package com.clothashe.clotashe_backend.service.misc.impl;

import com.clothashe.clotashe_backend.exception.ResourceNotFoundException;
import com.clothashe.clotashe_backend.mapper.misc.AddressMapper;
import com.clothashe.clotashe_backend.model.dto.user.create.CreateAddressRequestDTO;
import com.clothashe.clotashe_backend.model.dto.user.response.AddressResponseDTO;
import com.clothashe.clotashe_backend.model.dto.user.update.UpdateAddressRequestDTO;
import com.clothashe.clotashe_backend.model.entity.user.AddressEntity;
import com.clothashe.clotashe_backend.model.entity.user.UserEntity;
import com.clothashe.clotashe_backend.model.enums.Role;
import com.clothashe.clotashe_backend.repository.auth.UserRepository;
import com.clothashe.clotashe_backend.repository.misc.AddressRepository;
import com.clothashe.clotashe_backend.service.auth.impl.AuthService;
import com.clothashe.clotashe_backend.service.misc.AddressService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final AuthService authService;

    @Override
    public AddressResponseDTO createAddress(CreateAddressRequestDTO requestDTO) {
        UserEntity user = authService.getAuthenticatedUser();
        AddressEntity address = addressMapper.toEntity(requestDTO);
        address.setUser(user);
        AddressEntity saved = addressRepository.save(address);
        return addressMapper.toDto(saved);
    }

    @Override
    public void deleteAddress(Long addressId) {
        UserEntity user = authService.getAuthenticatedUser();
        AddressEntity address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found"));

        if (!address.getUser().getId().equals(user.getId()) && !user.getRole().equals(Role.ADMIN)) {
            throw new AccessDeniedException("You are not allowed to delete this address");
        }

        addressRepository.delete(address);
    }

    @Override
    public AddressResponseDTO updateAddress(Long addressId, UpdateAddressRequestDTO updateDTO) {
        UserEntity user = authService.getAuthenticatedUser();
        AddressEntity address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found"));

        if (!address.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You are not allowed to update this address");
        }

        addressMapper.updateEntityFromDTO(updateDTO, address);
        return addressMapper.toDto(addressRepository.save(address));
    }

    @Override
    public AddressResponseDTO getAddressById(Long addressId) {
        UserEntity user = authService.getAuthenticatedUser();
        AddressEntity address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found"));

        if (!address.getUser().getId().equals(user.getId()) && !user.getRole().equals(Role.ADMIN)) {
            throw new AccessDeniedException("You are not allowed to access this address");
        }

        return addressMapper.toDto(address);
    }

    @Override
    public List<AddressResponseDTO> getAllMyAddresses() {
        UserEntity user = authService.getAuthenticatedUser();
        List<AddressEntity> addresses = addressRepository.findByUserId(user.getId());
        return addresses.stream().map(addressMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<AddressResponseDTO> getAllAddressesByUserId(Long targetUserId) {
        UserEntity requester = authService.getAuthenticatedUser();
        if (!requester.getRole().equals(Role.ADMIN)) {
            throw new AccessDeniedException("Only admins can access other users' addresses");
        }

        List<AddressEntity> addresses = addressRepository.findByUserId(targetUserId);
        return addresses.stream().map(addressMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<AddressResponseDTO> getAllAddresses() {
        UserEntity requester = authService.getAuthenticatedUser();
        if (!requester.getRole().equals(Role.ADMIN)) {
            throw new AccessDeniedException("Only admins can access all addresses");
        }

        return addressRepository.findAll()
                .stream()
                .map(addressMapper::toDto)
                .collect(Collectors.toList());
    }
}