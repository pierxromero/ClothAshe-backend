package com.clothashe.clotashe_backend.service.auth;


import com.clothashe.clotashe_backend.model.dto.auth.UserWithAuthInfoDTO;
import com.clothashe.clotashe_backend.model.dto.user.response.UserDTO;
import com.clothashe.clotashe_backend.model.dto.user.update.ChangePasswordDTO;
import com.clothashe.clotashe_backend.model.dto.user.update.LockUnlockDTO;
import com.clothashe.clotashe_backend.model.dto.user.update.RoleChangeDTO;
import com.clothashe.clotashe_backend.model.dto.user.update.UpdateUserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface UserService {
    UserDTO getById(Long id);
    Page<UserDTO> getAll(Pageable pageable);
    UserDTO getByEmail(String email);
    List<UserDTO> getLockedUsers();
    UserDTO getMe();

    void deleteById(Long id);
    UserDTO changeUserRole(Long id, RoleChangeDTO dto);
    UserWithAuthInfoDTO lockUnlockUser(Long id, LockUnlockDTO dto);
    UserDTO updateProfile(UpdateUserDTO dto);
    void changePassword(ChangePasswordDTO dto);
    void deleteOwnAccount();
}
