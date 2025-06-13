package com.clothashe.clotashe_backend.service.auth.impl;

import com.clothashe.clotashe_backend.exception.users.EmailAlreadyExistsException;
import com.clothashe.clotashe_backend.exception.users.*;
import com.clothashe.clotashe_backend.model.dto.auth.UserWithAuthInfoDTO;
import com.clothashe.clotashe_backend.model.dto.user.response.UserDTO;
import com.clothashe.clotashe_backend.model.dto.user.update.ChangePasswordDTO;
import com.clothashe.clotashe_backend.model.dto.user.update.LockUnlockDTO;
import com.clothashe.clotashe_backend.model.dto.user.update.RoleChangeDTO;
import com.clothashe.clotashe_backend.model.dto.user.update.UpdateUserDTO;
import com.clothashe.clotashe_backend.model.entity.auth.AuthInfoEntity;
import com.clothashe.clotashe_backend.model.enums.Role;
import com.clothashe.clotashe_backend.repository.auth.AuthInfoRepository;
import com.clothashe.clotashe_backend.repository.auth.UserRepository;
import com.clothashe.clotashe_backend.service.auth.AuthService;
import com.clothashe.clotashe_backend.service.auth.UserService;
import com.clothashe.clotashe_backend.mapper.auth.UserMapper;
import com.clothashe.clotashe_backend.model.entity.user.UserEntity;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final AuthInfoRepository authInfoRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;

    @Override
    @Transactional(readOnly = true)
    public UserDTO getById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return userMapper.toUserDTO(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserDTO> getAll(Pageable pageable) {
        Page<UserEntity> page = userRepository.findAll(pageable);
        return page.map(userMapper::toUserDTO);
    }


    @Override
    @Transactional(readOnly = true)
    public UserDTO getByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
        return userMapper.toUserDTO(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> getLockedUsers() {
        List<AuthInfoEntity> lockedInfos = authInfoRepository.findByIsLockedTrue();
        List<UserEntity> lockedUsers = lockedInfos.stream()
                .map(AuthInfoEntity::getUser)
                .collect(Collectors.toList());
        return userMapper.toDTOs(lockedUsers);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getMe() {
        UserEntity me = authService.getAuthenticatedUser();
        return userMapper.toUserDTO(me);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        UserEntity targetUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if (targetUser.getRole() == Role.OWNER) {
            throw new UserAccessDeniedException("Cannot delete a user with OWNER role.");
        }

        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public UserDTO changeUserRole(Long id, RoleChangeDTO dto) {
        UserEntity currentUser = authService.getAuthenticatedUser();
        if (currentUser.getRole() != Role.OWNER) {
            throw new UserAccessDeniedException("Only OWNER can change user roles.");
        }
        UserEntity targetUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if (currentUser.getId().equals(targetUser.getId()) && dto.getNewRole() != Role.OWNER) {
            throw new UserAccessDeniedException("You cannot change your own role from OWNER to another role.");
        }

        if (targetUser.getRole() == Role.OWNER && !targetUser.getId().equals(currentUser.getId())) {
            throw new UserAccessDeniedException("Cannot change the role of another OWNER.");
        }
        targetUser.setRole(dto.getNewRole());
        targetUser = userRepository.save(targetUser);

        return userMapper.toUserDTO(targetUser);
    }

    @Override
    @Transactional
    public UserWithAuthInfoDTO lockUnlockUser(Long id, LockUnlockDTO dto) {
        AuthInfoEntity auth = authInfoRepository.findByUserId(id)
                .orElseThrow(() -> new AuthInfoNotFoundException(id));

        auth.setIsLocked(dto.getLock());
        authInfoRepository.save(auth);

        UserDTO userDto = userMapper.toUserDTO(auth.getUser());

        return UserWithAuthInfoDTO.builder()
                .locked(auth.getIsLocked())
                .user(userDto)
                .build();
    }

    @Override
    @Transactional
    public UserDTO updateProfile(UpdateUserDTO dto) {
        UserEntity user = authService.getAuthenticatedUser();
        if (userRepository.existsByEmailAndIdNot(dto.getEmail(), user.getId())) {
            throw new EmailAlreadyExistsException(dto.getEmail());
        }
        if (userRepository.existsByNumberPhoneAndIdNot(dto.getNumberPhone(), user.getId())) {
            throw new PhoneAlreadyExistsException(dto.getNumberPhone());
        }
        userMapper.updateFromDto(dto, user);
        user = userRepository.save(user);
        return userMapper.toUserDTO(user);
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordDTO dto) {
        UserEntity user = authService.getAuthenticatedUser();
        AuthInfoEntity auth = authInfoRepository
                .findByUserId(user.getId())
                .orElseThrow(() -> new AuthInfoNotFoundException(user.getId()));

        if (dto.getNewPassword().equals(dto.getCurrentPassword())) {
            throw new PasswordValidationException("The new password cannot be the same as the current password");
        }
        if (!passwordEncoder.matches(dto.getCurrentPassword(), auth.getPasswordHash())) {
            throw new PasswordValidationException("Current password is incorrect");
        }
        auth.setPasswordHash(passwordEncoder.encode(dto.getNewPassword()));
        authInfoRepository.save(auth);
    }

    @Override
    @Transactional
    public void deleteOwnAccount() {
        UserEntity user = authService.getAuthenticatedUser();
        userRepository.delete(user);
    }
}