package com.clothashe.clotashe_backend.exception.order;

import com.clothashe.clotashe_backend.exception.ApiException;
import org.springframework.http.HttpStatus;

public class CartItemNotFoundException extends ApiException {
    public CartItemNotFoundException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public String getErrorCode() {
        return "CART_ITEM_NOT_FOUND";
    }
}