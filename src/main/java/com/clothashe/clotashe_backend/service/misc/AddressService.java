package com.clothashe.clotashe_backend.service.misc;

import com.clothashe.clotashe_backend.model.dto.user.create.CreateAddressRequestDTO;
import com.clothashe.clotashe_backend.model.dto.user.response.AddressResponseDTO;
import com.clothashe.clotashe_backend.model.dto.user.update.UpdateAddressRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
public interface AddressService {

    AddressResponseDTO createAddress(CreateAddressRequestDTO dto);

    void deleteAddress(Long addressId);

    AddressResponseDTO updateAddress(Long addressId, UpdateAddressRequestDTO dto);

    AddressResponseDTO getAddressById(Long addressId);

    List<AddressResponseDTO> getAllMyAddresses();

    List<AddressResponseDTO> getAllAddressesByUserId(Long userId);

    Page<AddressResponseDTO> getAllAddresses(Pageable pageable);

}