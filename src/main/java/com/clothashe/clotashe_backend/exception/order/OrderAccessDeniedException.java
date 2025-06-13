package com.clothashe.clotashe_backend.exception.order;

import com.clothashe.clotashe_backend.exception.ApiException;
import org.springframework.http.HttpStatus;

public class OrderAccessDeniedException extends ApiException {
    public OrderAccessDeniedException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.FORBIDDEN;
    }

    @Override
    public String getErrorCode() {
        return "ORDER_ACCESS_DENIED";
    }
}