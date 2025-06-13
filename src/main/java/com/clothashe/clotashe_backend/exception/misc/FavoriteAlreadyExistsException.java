package com.clothashe.clotashe_backend.exception.misc;
import com.clothashe.clotashe_backend.exception.ApiException;
import org.springframework.http.HttpStatus;

public class FavoriteAlreadyExistsException extends ApiException {
    public FavoriteAlreadyExistsException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public String getErrorCode() {
        return "FAVORITE_ALREADY_EXISTS";
    }
}