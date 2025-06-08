package com.clothashe.clotashe_backend.exception.order;


import com.clothashe.clotashe_backend.exception.ApiException;
import org.springframework.http.HttpStatus;

public class CartNotFoundException extends ApiException {
    public CartNotFoundException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public String getErrorCode() {
        return "CART_NOT_FOUND";
    }
}