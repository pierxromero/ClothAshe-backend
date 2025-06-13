package com.clothashe.clotashe_backend.exception.users;


import com.clothashe.clotashe_backend.exception.ApiException;
import org.springframework.http.HttpStatus;

public class UserAccessDeniedException extends ApiException {

    public UserAccessDeniedException(String actionDescription) {
        super(actionDescription);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.FORBIDDEN;
    }

    @Override
    public String getErrorCode() {
        return "ACCESS_DENIED";
    }
}