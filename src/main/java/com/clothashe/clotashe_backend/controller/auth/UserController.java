package com.clothashe.clotashe_backend.controller.auth;

import com.clothashe.clotashe_backend.model.dto.auth.UserWithAuthInfoDTO;
import com.clothashe.clotashe_backend.model.dto.user.response.UserDTO;
import com.clothashe.clotashe_backend.model.dto.user.update.ChangePasswordDTO;
import com.clothashe.clotashe_backend.model.dto.user.update.LockUnlockDTO;
import com.clothashe.clotashe_backend.model.dto.user.update.RoleChangeDTO;
import com.clothashe.clotashe_backend.model.dto.user.update.UpdateUserDTO;
import com.clothashe.clotashe_backend.service.auth.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/search")
    public ResponseEntity<UserDTO> getByEmail(@RequestParam String email) {
        return ResponseEntity.ok(userService.getByEmail(email));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/locked")
    public ResponseEntity<List<UserDTO>> getLockedUsers() {
        return ResponseEntity.ok(userService.getLockedUsers());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('OWNER')")
    @PatchMapping("/{id}/role")
    public ResponseEntity<UserDTO> changeUserRole(
            @PathVariable Long id,
            @Valid @RequestBody RoleChangeDTO dto
    ) {
        return ResponseEntity.ok(userService.changeUserRole(id, dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/lock")
    public ResponseEntity<UserWithAuthInfoDTO> lockUnlockUser(
            @PathVariable Long id,
            @Valid @RequestBody LockUnlockDTO dto
    ) {
        return ResponseEntity.ok(userService.lockUnlockUser(id, dto));
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    public ResponseEntity<UserDTO> getMe() {
        return ResponseEntity.ok(userService.getMe());
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/me")
    public ResponseEntity<UserDTO> updateProfile(
            @Valid @RequestBody UpdateUserDTO dto
    ) {
        return ResponseEntity.ok(userService.updateProfile(dto));
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/me/password")
    public ResponseEntity<Void> changePassword(
            @Valid @RequestBody ChangePasswordDTO dto
    ) {
        userService.changePassword(dto);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteOwnAccount() {
        userService.deleteOwnAccount();
        return ResponseEntity.noContent().build();
    }
}