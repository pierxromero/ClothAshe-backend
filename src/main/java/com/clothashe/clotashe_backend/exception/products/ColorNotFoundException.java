package com.clothashe.clotashe_backend.exception.products;

import com.clothashe.clotashe_backend.exception.ApiException;
import org.springframework.http.HttpStatus;

public class ColorNotFoundException extends ApiException {
    public ColorNotFoundException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public String getErrorCode() {
        return "COLOR_NOT_FOUND";
    }
}