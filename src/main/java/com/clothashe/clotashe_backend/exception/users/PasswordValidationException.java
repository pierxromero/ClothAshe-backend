package com.clothashe.clotashe_backend.exception.users;
import com.clothashe.clotashe_backend.exception.ApiException;
import org.springframework.http.HttpStatus;

public class PasswordValidationException extends ApiException {
    public PasswordValidationException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public String getErrorCode() {
        return "PASSWORD_VALIDATION_ERROR";
    }
}