package com.project.shopapp.exceptions;

import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

@Setter
@Getter
public class AppException extends Exception{
    private ErrorCode errorCode;
    public AppException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
