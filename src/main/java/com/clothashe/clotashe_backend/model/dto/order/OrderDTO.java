package com.clothashe.clotashe_backend.model.dto.order;


import com.clothashe.clotashe_backend.model.dto.user.AddressDTO;
import com.clothashe.clotashe_backend.model.enums.OrderStatus;
import com.clothashe.clotashe_backend.model.enums.PaymentMethod;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OrderDTO {

    private Long id;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Order date is required")
    @PastOrPresent(message = "Order date cannot be in the future")
    private LocalDateTime orderDate;

    @NotNull(message = "Total amount is required")
    @DecimalMin(value = "0.01", inclusive = true, message = "Total amount must be greater than 0")
    private BigDecimal totalAmount;

    @NotNull(message = "Order status is required")
    private OrderStatus status;

    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;

    @NotNull(message = "Shipping address is required")
    @Valid
    private AddressDTO shippingAddressId;

    @NotEmpty(message = "Items list must contain at least one item")
    private List<@Valid OrderItemDTO> items;

    @NotNull(message = "Payment information must be provided")
    @Valid
    private PaymentDTO paymentId;;
}