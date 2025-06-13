package com.clothashe.clotashe_backend.controller.misc;

import com.clothashe.clotashe_backend.exception.error.ApiError;
import com.clothashe.clotashe_backend.model.dto.review.create.CreateProductCommentRequestDTO;
import com.clothashe.clotashe_backend.model.dto.review.response.ProductCommentResponseDTO;
import com.clothashe.clotashe_backend.model.dto.review.update.UpdateProductCommentRequestDTO;
import com.clothashe.clotashe_backend.service.misc.ProductCommentService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
@Validated
@Tag(name = "Product Comments", description = "API for managing product comments (reviews)")
public class ProductCommentController {

    private final ProductCommentService commentService;

    @Operation(
            summary = "Create a new product comment",
            description = "Allows an authenticated user to submit a comment (review) for a specific product. " +
                    "User cannot comment more than once per product."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Comment created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductCommentResponseDTO.class))
            ),
            @ApiResponse(responseCode = "400", description = "Validation failed (blank comment, invalid rating, etc.)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Rating must be between 1 and 5",
                      "status": 400,
                      "errorCode": "BAD_REQUEST",
                      "path": "/api/comments",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Product not found with ID 123",
                      "status": 404,
                      "errorCode": "NOT_FOUND",
                      "path": "/api/comments",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "409", description = "User has already commented on this product",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "You have already submitted a review for product 123",
                      "status": 409,
                      "errorCode": "CONFLICT",
                      "path": "/api/comments",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Authentication required",
                      "status": 401,
                      "errorCode": "UNAUTHORIZED",
                      "path": "/api/comments",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            )
    })
    @PostMapping
    public ResponseEntity<ProductCommentResponseDTO> createComment(
            @Valid @RequestBody CreateProductCommentRequestDTO dto) {
        ProductCommentResponseDTO created = commentService.createComment(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(
            summary = "Update own comment",
            description = "Allows the comment owner to update the text and/or rating of their comment."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Comment updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductCommentResponseDTO.class))
            ),
            @ApiResponse(responseCode = "400", description = "Validation failed",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Comment text must not be blank",
                      "status": 400,
                      "errorCode": "BAD_REQUEST",
                      "path": "/api/comments/5",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "403", description = "Forbidden – not the owner",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "You do not have permission to modify this comment",
                      "status": 403,
                      "errorCode": "FORBIDDEN",
                      "path": "/api/comments/5",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Comment or product not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Comment with ID 5 not found",
                      "status": 404,
                      "errorCode": "NOT_FOUND",
                      "path": "/api/comments/5",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Authentication required",
                      "status": 401,
                      "errorCode": "UNAUTHORIZED",
                      "path": "/api/comments/5",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            )
    })
    @PutMapping("/{commentId}")
    public ResponseEntity<ProductCommentResponseDTO> updateOwnComment(
            @PathVariable @Min(1) @Parameter(description = "ID of the comment to update", required = true) Long commentId,
            @Valid @RequestBody UpdateProductCommentRequestDTO dto) {
        ProductCommentResponseDTO updated = commentService.updateOwnComment(commentId, dto);
        return ResponseEntity.ok(updated);
    }

    @Operation(
            summary = "Delete a comment",
            description = "Deletes a comment. Only the comment owner can delete their own comment."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Comment deleted successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden – not the owner",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "You do not have permission to delete this comment",
                      "status": 403,
                      "errorCode": "FORBIDDEN",
                      "path": "/api/comments/5",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Comment not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Comment with ID 5 not found",
                      "status": 404,
                      "errorCode": "NOT_FOUND",
                      "path": "/api/comments/5",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Authentication required",
                      "status": 401,
                      "errorCode": "UNAUTHORIZED",
                      "path": "/api/comments/5",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            )
    })
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable @Min(1) @Parameter(description = "ID of the comment to delete", required = true) Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "List my comments",
            description = "Retrieves all comments made by the authenticated user."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Comments retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ProductCommentResponseDTO.class)))
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Authentication required",
                      "status": 401,
                      "errorCode": "UNAUTHORIZED",
                      "path": "/api/comments/me",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            )
    })
    @GetMapping("/me")
    public ResponseEntity<List<ProductCommentResponseDTO>> getMyComments() {
        List<ProductCommentResponseDTO> list = commentService.getMyComments();
        return ResponseEntity.ok(list);
    }

    @Operation(
            summary = "List comments by user (admin only)",
            description = "Allows administrators to fetch all comments submitted by a specific user."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Comments retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ProductCommentResponseDTO.class)))
            ),
            @ApiResponse(responseCode = "403", description = "Forbidden – requires ADMIN role",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Access denied",
                      "status": 403,
                      "errorCode": "FORBIDDEN",
                      "path": "/api/comments/user/10",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Authentication required",
                      "status": 401,
                      "errorCode": "UNAUTHORIZED",
                      "path": "/api/comments/user/10",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            )
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProductCommentResponseDTO>> getCommentsByUser(
            @PathVariable @Min(1) @Parameter(description = "ID of the user", required = true) Long userId) {
        List<ProductCommentResponseDTO> list = commentService.getCommentsByUser(userId);
        return ResponseEntity.ok(list);
    }

    @Operation(
            summary = "List comments by product",
            description = "Fetches paginated comments made for a given product."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Comments retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductCommentResponseDTO.class))
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                        {
                          "message": "Authentication required",
                          "status": 401,
                          "errorCode": "UNAUTHORIZED",
                          "path": "/api/comments/product/20",
                          "timestamp": "2025-06-08T16:00:00"
                        }
                        """
                            )
                    )
            )
    })
    @GetMapping("/product/{productId}")
    public ResponseEntity<Page<ProductCommentResponseDTO>> getCommentsByProduct(
            @PathVariable @Min(1) @Parameter(description = "ID of the product", required = true) Long productId,
            @ParameterObject Pageable pageable) {
        Page<ProductCommentResponseDTO> page = commentService.getCommentsByProduct(productId, pageable);
        return ResponseEntity.ok(page);
    }

    @Operation(
            summary = "List all comments (admin only)",
            description = "Allows administrators to retrieve paginated comments in the system."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "All comments retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductCommentResponseDTO.class))
            ),
            @ApiResponse(responseCode = "403", description = "Forbidden – requires ADMIN role",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                        {
                          "message": "Access denied",
                          "status": 403,
                          "errorCode": "FORBIDDEN",
                          "path": "/api/comments",
                          "timestamp": "2025-06-08T16:00:00"
                        }
                        """
                            )
                    )
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                        {
                          "message": "Authentication required",
                          "status": 401,
                          "errorCode": "UNAUTHORIZED",
                          "path": "/api/comments",
                          "timestamp": "2025-06-08T16:00:00"
                        }
                        """
                            )
                    )
            )
    })
    @GetMapping
    public ResponseEntity<Page<ProductCommentResponseDTO>> getAllComments(
            @ParameterObject Pageable pageable) {
        Page<ProductCommentResponseDTO> page = commentService.getAllComments(pageable);
        return ResponseEntity.ok(page);
    }

    @Operation(
            summary = "Get a comment by ID (admin only)",
            description = "Allows an administrator to fetch any comment by its ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Comment retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductCommentResponseDTO.class))
            ),
            @ApiResponse(responseCode = "404", description = "Comment not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Comment with ID 7 not found",
                      "status": 404,
                      "errorCode": "NOT_FOUND",
                      "path": "/api/comments/7",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Authentication required",
                      "status": 401,
                      "errorCode": "UNAUTHORIZED",
                      "path": "/api/comments/7",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "403", description = "Forbidden – requires ADMIN role",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Access denied",
                      "status": 403,
                      "errorCode": "FORBIDDEN",
                      "path": "/api/comments/7",
                      "timestamp": "2025-06-08T16:00:00"
                    }
                    """
                            )
                    )
            )
    })
    @GetMapping("/{commentId}")
    public ResponseEntity<ProductCommentResponseDTO> getCommentById(
            @PathVariable @Min(1) @Parameter(description = "ID of the comment", required = true) Long commentId) {
        ProductCommentResponseDTO dto = commentService.getCommentById(commentId);
        return ResponseEntity.ok(dto);
    }

}