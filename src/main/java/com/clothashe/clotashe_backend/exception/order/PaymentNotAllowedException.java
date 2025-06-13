package com.clothashe.clotashe_backend.exception.order;
import com.clothashe.clotashe_backend.exception.ApiException;
import org.springframework.http.HttpStatus;


public class PaymentNotAllowedException extends ApiException {
    public PaymentNotAllowedException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public String getErrorCode() {
        return "PAYMENT_NOT_ALLOWED";
    }
}