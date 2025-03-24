package com.android.APILogin.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Date;

//@RestControllerAdvice
public class GlobalExceptionHandler {
    //request body vi pham validation
    //request param, path variable không hợp lệ
    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException( Exception exception, WebRequest request ) {
        System.out.println("==========> handleValidationException");
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(new Date());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setPath(request.getDescription(false).replace("uri=", ""));

        //lay default message
        String message = exception.getMessage();
        if(exception instanceof MethodArgumentNotValidException) {
            int start = message.lastIndexOf("[");
            int end = message.lastIndexOf("]");
            message = message.substring(start+1, end-1);
            errorResponse.setError("Payload invalid");
        }else if(exception instanceof ConstraintViolationException) {
            message = message.substring(message.indexOf(" ") + 1);
            errorResponse.setError("Parameter Invalid");
        }
        errorResponse.setMessage(message);
        return errorResponse;
    }

    //exception khi doi so request param sai kieu du lieu
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleInternalServerErrorException( Exception exception, WebRequest request ) {
        System.out.println("==========> handleValidationException");
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(new Date());
        errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.setPath(request.getDescription(false).replace("uri=", ""));
        errorResponse.setError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());

        if(exception instanceof MethodArgumentTypeMismatchException) {
            errorResponse.setMessage("Failed to convert value of type");
        }
        return errorResponse;
    }
}
