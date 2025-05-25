package com.clothashe.clotashe_backend.service.misc;

import com.clothashe.clotashe_backend.model.dto.user.AddressDTO;

import java.util.List;

public interface AddressService
{
    AddressDTO create(AddressDTO dto);

    AddressDTO update(Long id, AddressDTO dto);

    AddressDTO getById(Long id);

    List<AddressDTO> getAll();

    void delete(Long id);
}