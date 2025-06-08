package com.clothashe.clotashe_backend.exception.users;


import com.clothashe.clotashe_backend.exception.ApiException;
import org.springframework.http.HttpStatus;

public class AuthInfoNotFoundException extends ApiException {
    public AuthInfoNotFoundException(Long message) {
        super("AuthInfo not found for user id " + message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public String getErrorCode() {
        return "AUTHINFO_NOT_FOUND";
    }
}