package com.sii.loyaltysystem.giftcard.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sii.loyaltysystem.giftcard.api.type.CurrencyType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GiftCardDto {

    @JsonProperty("id")
    String giftCardId;

    @JsonProperty("amount")
    BigDecimal amount;

    @JsonProperty("currency")
    CurrencyType currency;

    @JsonProperty("expiration")
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    LocalDateTime expiryDate;

    @JsonProperty("description")
    String description;

}
