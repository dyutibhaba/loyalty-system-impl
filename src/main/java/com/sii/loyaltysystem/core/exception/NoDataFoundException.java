package com.sii.loyaltysystem.core.exception;

public class NoDataFoundException extends RuntimeException {

    private static final String NO_FOUND_MESSAGE_PATTERN = "Object not found for id: %s";

    public NoDataFoundException(String message) {
        super(message);
    }

    public NoDataFoundException(Class<?> objectClaz, Long givenId) {
        super(String.format(NO_FOUND_MESSAGE_PATTERN, objectClaz.getSimpleName(), Long.toString(givenId)));
    }
}
