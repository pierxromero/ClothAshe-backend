package com.clothashe.clotashe_backend.controller.misc;

import com.clothashe.clotashe_backend.model.dto.review.create.CreateProductCommentRequestDTO;
import com.clothashe.clotashe_backend.model.dto.review.response.ProductCommentResponseDTO;
import com.clothashe.clotashe_backend.model.dto.review.update.UpdateProductCommentRequestDTO;
import com.clothashe.clotashe_backend.service.misc.ProductCommentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
@Validated
public class ProductCommentController {

    private final ProductCommentService commentService;

    @PostMapping
    public ResponseEntity<ProductCommentResponseDTO> createComment(
            @Valid @RequestBody CreateProductCommentRequestDTO dto) {
        ProductCommentResponseDTO created = commentService.createComment(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }


    @PutMapping("/{commentId}")
    public ResponseEntity<ProductCommentResponseDTO> updateOwnComment(
            @PathVariable @Min(1) Long commentId,
            @Valid @RequestBody UpdateProductCommentRequestDTO dto) {
        ProductCommentResponseDTO updated = commentService.updateOwnComment(commentId, dto);
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable @Min(1) Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<List<ProductCommentResponseDTO>> getMyComments() {
        List<ProductCommentResponseDTO> list = commentService.getMyComments();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ProductCommentResponseDTO>> getCommentsByUser(
            @PathVariable @Min(1) Long userId) {
        List<ProductCommentResponseDTO> list = commentService.getCommentsByUser(userId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ProductCommentResponseDTO>> getCommentsByProduct(
            @PathVariable @Min(1) Long productId) {
        List<ProductCommentResponseDTO> list = commentService.getCommentsByProduct(productId);
        return ResponseEntity.ok(list);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ProductCommentResponseDTO>> getAllComments() {
        List<ProductCommentResponseDTO> list = commentService.getAllComments();
        return ResponseEntity.ok(list);
    }


    @GetMapping("/{commentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductCommentResponseDTO> getCommentById(
            @PathVariable @Min(1) Long commentId) {
        ProductCommentResponseDTO dto = commentService.getCommentById(commentId);
        return ResponseEntity.ok(dto);
    }
}