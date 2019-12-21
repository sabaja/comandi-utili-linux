package com.intesasanpaolo.bear.lmbe0.orchestrazione.exception;

import com.intesasanpaolo.bear.exceptions.BearDomainRuntimeException;
import org.springframework.http.HttpStatus;

public class PraticaNonTrovataException extends BearDomainRuntimeException {

    public PraticaNonTrovataException(String message, String code, HttpStatus responseStatus) {
        super(message, code, responseStatus);
    }
}
