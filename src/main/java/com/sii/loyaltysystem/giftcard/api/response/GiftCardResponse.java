package com.sii.loyaltysystem.giftcard.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCardResponse {

    @JsonProperty("message")
    protected String message;

    @JsonProperty("giftCardId")
    private String giftCardId;

}
