package com.sii.loyaltysystem.giftcard.api.type;

import java.util.Arrays;
import java.util.Optional;

public enum StatusType {

    ACTIVE("active"),
    EXPIRED("expired");

    public final String status;

    StatusType(String status) {
        this.status = status;
    }

    public static Optional<StatusType> getStatusType(String status) {
        return Arrays.stream(values())
                .filter(e -> e.status.equalsIgnoreCase(status))
                .findFirst();
    }
}
