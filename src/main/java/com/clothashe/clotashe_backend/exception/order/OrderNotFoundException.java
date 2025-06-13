package com.clothashe.clotashe_backend.exception.order;


import com.clothashe.clotashe_backend.exception.ApiException;
import org.springframework.http.HttpStatus;

public class OrderNotFoundException extends ApiException {
    public OrderNotFoundException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public String getErrorCode() {
        return "ORDER_NOT_FOUND";
    }
}