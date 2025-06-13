package com.clothashe.clotashe_backend.exception.products;


import com.clothashe.clotashe_backend.exception.ApiException;
import org.springframework.http.HttpStatus;

public class SizeAlreadyExistsException extends ApiException {
    public SizeAlreadyExistsException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public String getErrorCode() {
        return "SIZE_ALREADY_EXISTS";
    }
}