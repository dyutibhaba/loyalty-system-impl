package com.sii.loyaltysystem.env;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("environmentParameters")
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EnvironmentParameters {

    String giftCardInitId;

    public EnvironmentParameters(@Value("${giftcard.baseId}") String giftCardInitId) {
        this.giftCardInitId = giftCardInitId;
    }
}
