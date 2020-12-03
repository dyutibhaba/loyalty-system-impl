package com.sii.loyaltysystem.config;

import com.sii.loyaltysystem.core.api.response.error.ErrorResponse;
import com.sii.loyaltysystem.core.exception.NoDataFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.format.DateTimeParseException;

@RestControllerAdvice
@Slf4j
public class ErrorHandlingControllerAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse onUnexpectedException(Exception exception) throws IOException {

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .errorMessage(exception.getMessage())
                .stacktrace(sw.toString())
                .build();

        sw.close();
        pw.close();

        log.error("An unexpected error has occurred.", exception);
        return errorResponse;
    }

    @ExceptionHandler(NoDataFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse onNoDataFoundException(NoDataFoundException exception) {
        return ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .errorMessage(exception.getMessage())
                .build();
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse onDateTimeParseException(HttpMessageNotReadableException exception) {
        ErrorResponse.ErrorResponseBuilder errorResponseBuilder = ErrorResponse.builder().status(HttpStatus.BAD_REQUEST);

        if(exception.getCause().getCause() instanceof DateTimeParseException) {
            return errorResponseBuilder.errorMessage("Incorrect expiry date format! Supported format is dd.MM.yyyy HH:mm:ss")
                    .build();
        }
        return ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .errorMessage(exception.getMessage())
                .build();
    }

}
