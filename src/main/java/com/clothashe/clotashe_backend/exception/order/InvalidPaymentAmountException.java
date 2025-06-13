package com.clothashe.clotashe_backend.exception.order;
import com.clothashe.clotashe_backend.exception.ApiException;
import org.springframework.http.HttpStatus;



public class InvalidPaymentAmountException extends ApiException {
    public InvalidPaymentAmountException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public String getErrorCode() {
        return "INVALID_PAYMENT_AMOUNT";
    }
}