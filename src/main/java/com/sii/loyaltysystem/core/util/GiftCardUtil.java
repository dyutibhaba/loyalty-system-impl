package com.sii.loyaltysystem.core.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GiftCardUtil {

    public String generateNewGiftCardId() {
        return UUID.randomUUID().toString();
    }
}
