package com.clothashe.clotashe_backend.exception.misc;
import com.clothashe.clotashe_backend.exception.ApiException;
import org.springframework.http.HttpStatus;


public class InquiryAlreadyAnsweredException extends ApiException {
    public InquiryAlreadyAnsweredException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public String getErrorCode() {
        return "INQUIRY_ALREADY_ANSWERED";
    }
}