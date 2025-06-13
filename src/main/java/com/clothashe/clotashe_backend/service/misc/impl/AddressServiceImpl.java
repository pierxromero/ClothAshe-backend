package com.clothashe.clotashe_backend.service.misc.impl;

import com.clothashe.clotashe_backend.exception.misc.AddressNotFoundException;
import com.clothashe.clotashe_backend.exception.users.UserAccessDeniedException;
import com.clothashe.clotashe_backend.mapper.misc.AddressMapper;
import com.clothashe.clotashe_backend.model.dto.user.create.CreateAddressRequestDTO;
import com.clothashe.clotashe_backend.model.dto.user.response.AddressResponseDTO;
import com.clothashe.clotashe_backend.model.dto.user.update.UpdateAddressRequestDTO;
import com.clothashe.clotashe_backend.model.entity.user.AddressEntity;
import com.clothashe.clotashe_backend.model.entity.user.UserEntity;
import com.clothashe.clotashe_backend.model.enums.Role;
import com.clothashe.clotashe_backend.repository.misc.AddressRepository;
import com.clothashe.clotashe_backend.service.auth.AuthService;
import com.clothashe.clotashe_backend.service.misc.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
                .orElseThrow(() -> new AddressNotFoundException("Address not found"));
        System.out.println("User id: " + user.getId());
        System.out.println("User role: " + user.getRole());
        System.out.println("Address user id: " + (address.getUser() != null ? address.getUser().getId() : "null"));
        if (!address.getUser().getId().equals(user.getId()) && !user.getRole().equals(Role.ADMIN)) {
            throw new UserAccessDeniedException("You are not allowed to delete this address");
        }

        addressRepository.delete(address);
    }

    @Override
    public AddressResponseDTO updateAddress(Long addressId, UpdateAddressRequestDTO updateDTO) {
        UserEntity user = authService.getAuthenticatedUser();
        AddressEntity address = addressRepository.findById(addressId)
                .orElseThrow(() -> new AddressNotFoundException("Address not found"));

        if (!address.getUser().getId().equals(user.getId())) {
            throw new UserAccessDeniedException("You are not allowed to update this address");
        }

        addressMapper.updateEntityFromDTO(updateDTO, address);
        return addressMapper.toDto(addressRepository.save(address));
    }

    @Override
    public AddressResponseDTO getAddressById(Long addressId) {
        UserEntity user = authService.getAuthenticatedUser();
        AddressEntity address = addressRepository.findById(addressId)
                .orElseThrow(() -> new AddressNotFoundException("Address not found"));

        if (!address.getUser().getId().equals(user.getId()) && !user.getRole().equals(Role.ADMIN)) {
            throw new UserAccessDeniedException("You are not allowed to access this address");
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
            throw new UserAccessDeniedException("Only admins can access other users' addresses");
        }

        List<AddressEntity> addresses = addressRepository.findByUserId(targetUserId);
        return addresses.stream().map(addressMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public Page<AddressResponseDTO> getAllAddresses(Pageable pageable) {
        UserEntity requester = authService.getAuthenticatedUser();
        if (!requester.getRole().equals(Role.ADMIN)) {
            throw new UserAccessDeniedException("Only admins can access all addresses");
        }

        Page<AddressEntity> page = addressRepository.findAll(pageable);
        return page.map(addressMapper::toDto);
    }
}