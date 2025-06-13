package com.clothashe.clotashe_backend.exception.order;

import com.clothashe.clotashe_backend.exception.ApiException;
import org.springframework.http.HttpStatus;

public class OrderStatusUpdateNotAllowedException extends ApiException {
    public OrderStatusUpdateNotAllowedException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public String getErrorCode() {
        return "ORDER_STATUS_UPDATE_NOT_ALLOWED";
    }
}