package com.sii.loyaltysystem.core.util;

import org.springframework.stereotype.Component;

@Component
public class GiftCardUtil {

    private static final String BASE_STRING = "GC";

    public String generateNewGiftCardId(String currentId) {
        String[] idSubStrings = currentId.split(BASE_STRING);
        int nextNumber = getNextNumber(Integer.valueOf(idSubStrings[1]));
        return BASE_STRING.concat(String.valueOf(nextNumber));
    }

    private int getNextNumber(int numberText) {
        return (numberText + 1);
    }
}
