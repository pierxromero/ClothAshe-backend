package com.clothashe.clotashe_backend.exception.users;

import com.clothashe.clotashe_backend.exception.ApiException;
import org.springframework.http.HttpStatus;

public class PhoneAlreadyExistsException extends ApiException {
    public PhoneAlreadyExistsException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public String getErrorCode() {
        return "PHONE_ALREADY_EXISTS";
    }
}