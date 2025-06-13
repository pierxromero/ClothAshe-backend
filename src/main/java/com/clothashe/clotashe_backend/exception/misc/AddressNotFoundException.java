package com.clothashe.clotashe_backend.exception.misc;


import com.clothashe.clotashe_backend.exception.ApiException;
import org.springframework.http.HttpStatus;

public class AddressNotFoundException extends ApiException {
    public AddressNotFoundException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public String getErrorCode() {
        return "ADDRESS_NOT_FOUND";
    }
}