package com.clothashe.clotashe_backend.controller.misc;


import com.clothashe.clotashe_backend.exception.error.ApiError;
import com.clothashe.clotashe_backend.model.dto.user.create.AnswerInquiryRequestDTO;
import com.clothashe.clotashe_backend.model.dto.user.create.CreateUserInquiryRequestDTO;
import com.clothashe.clotashe_backend.model.dto.user.response.UserInquiryResponseDTO;
import com.clothashe.clotashe_backend.service.misc.UserInquiryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/inquiries")
@RequiredArgsConstructor
@Validated
@Tag(name = "User Inquiries", description = "Operations related to user inquiries management")
public class UserInquiryController {

    private final UserInquiryService inquiryService;

    @Operation(summary = "Create a new user inquiry",
            description = "Allows an authenticated user to create a new inquiry",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Inquiry created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserInquiryResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input data",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            })
    @PostMapping
    public ResponseEntity<UserInquiryResponseDTO> createInquiry(
            @Valid @RequestBody CreateUserInquiryRequestDTO dto) {
        UserInquiryResponseDTO created = inquiryService.createInquiry(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "List all inquiries (admin only)",
            description = "Allows an admin to list all inquiries, with optional filters",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of inquiries retrieved",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Page.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid parameters",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Access denied",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiError.class)))
            })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<Page<UserInquiryResponseDTO>> listAllInquiries(
            @Parameter(description = "Filter by answered status")
            @RequestParam(required = false) Boolean answered,
            @Parameter(description = "Filter by user ID (long)")
            @RequestParam(required = false) @Min(1) Long userId,
            @Parameter(description = "Page number (zero-based)")
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @Parameter(description = "Page size")
            @RequestParam(defaultValue = "10") @Min(1) int size) {
        Page<UserInquiryResponseDTO> result = inquiryService.listAllInquiries(answered, userId, page, size);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "List inquiries of the authenticated user",
            description = "Returns all inquiries submitted by the authenticated user",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "User's inquiries retrieved",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = UserInquiryResponseDTO.class)))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            })
    @GetMapping("/me")
    public ResponseEntity<List<UserInquiryResponseDTO>> listMyInquiries() {
        List<UserInquiryResponseDTO> list = inquiryService.listUserInquiries();
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Get inquiry by ID",
            description = "Retrieves a specific inquiry by its ID if the authenticated user is owner or admin",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Inquiry retrieved",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserInquiryResponseDTO.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Access denied",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(responseCode = "404", description = "Inquiry not found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiError.class)))
            })
    @GetMapping("/{inquiryId}")
    public ResponseEntity<UserInquiryResponseDTO> getInquiryById(
            @Parameter(description = "ID of the inquiry to retrieve", required = true)
            @PathVariable Long inquiryId) {
        UserInquiryResponseDTO dto = inquiryService.getInquiryById(inquiryId);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Answer an inquiry (admin only)",
            description = "Allows an admin to answer a user inquiry",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Inquiry answered successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserInquiryResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input or inquiry already answered",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Access denied",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(responseCode = "404", description = "Inquiry not found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiError.class)))
            })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/answer")
    public ResponseEntity<UserInquiryResponseDTO> answerInquiry(
            @Valid @RequestBody AnswerInquiryRequestDTO dto) {
        UserInquiryResponseDTO answered = inquiryService.answerInquiry(dto);
        return ResponseEntity.ok(answered);
    }

    @Operation(summary = "Delete an inquiry",
            description = "Allows the inquiry owner or an admin to delete an inquiry",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Inquiry deleted successfully"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Access denied",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(responseCode = "404", description = "Inquiry not found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiError.class)))
            })
    @DeleteMapping("/{inquiryId}")
    public ResponseEntity<Void> deleteInquiry(
            @Parameter(description = "ID of the inquiry to delete", required = true)
            @PathVariable Long inquiryId) {
        inquiryService.deleteInquiry(inquiryId);
        return ResponseEntity.noContent().build();
    }
}