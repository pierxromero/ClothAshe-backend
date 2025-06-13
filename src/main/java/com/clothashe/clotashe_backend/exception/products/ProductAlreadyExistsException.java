package com.clothashe.clotashe_backend.exception.products;


import com.clothashe.clotashe_backend.exception.ApiException;
import org.springframework.http.HttpStatus;

public class ProductAlreadyExistsException extends ApiException {
    public ProductAlreadyExistsException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public String getErrorCode() {
        return "PRODUCT_ALREADY_EXISTS";
    }
}
