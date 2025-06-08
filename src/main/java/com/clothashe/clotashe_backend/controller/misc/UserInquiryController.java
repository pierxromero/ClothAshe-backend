package com.clothashe.clotashe_backend.controller.misc;


import com.clothashe.clotashe_backend.model.dto.user.create.AnswerInquiryRequestDTO;
import com.clothashe.clotashe_backend.model.dto.user.create.CreateUserInquiryRequestDTO;
import com.clothashe.clotashe_backend.model.dto.user.response.UserInquiryResponseDTO;
import com.clothashe.clotashe_backend.service.misc.UserInquiryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inquiries")
@RequiredArgsConstructor
@Validated
public class UserInquiryController {

    private final UserInquiryService inquiryService;


    @PostMapping
    public ResponseEntity<UserInquiryResponseDTO> createInquiry(
            @Valid @RequestBody CreateUserInquiryRequestDTO dto) {
        UserInquiryResponseDTO created = inquiryService.createInquiry(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserInquiryResponseDTO>> listAllInquiries(
            @RequestParam(required = false) Boolean answered,
            @RequestParam(required = false) @Min(1) Long userId,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) int size) {
        Page<UserInquiryResponseDTO> result = inquiryService.listAllInquiries(answered, userId, page, size);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/me")
    public ResponseEntity<List<UserInquiryResponseDTO>> listMyInquiries() {
        List<UserInquiryResponseDTO> list = inquiryService.listUserInquiries();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{inquiryId}")
    public ResponseEntity<UserInquiryResponseDTO> getInquiryById(
            @PathVariable Long inquiryId) {
        UserInquiryResponseDTO dto = inquiryService.getInquiryById(inquiryId);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/answer")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserInquiryResponseDTO> answerInquiry(
            @Valid @RequestBody AnswerInquiryRequestDTO dto) {
        UserInquiryResponseDTO answered = inquiryService.answerInquiry(dto);
        return ResponseEntity.ok(answered);
    }

    @DeleteMapping("/{inquiryId}")
    public ResponseEntity<Void> deleteInquiry(
            @PathVariable Long inquiryId) {
        inquiryService.deleteInquiry(inquiryId);
        return ResponseEntity.noContent().build();
    }
}