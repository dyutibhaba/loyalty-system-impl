package com.sii.loyaltysystem.giftcard.api.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sii.loyaltysystem.giftcard.api.type.CurrencyType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class GiftCardRequest {

    @NotNull
    @JsonProperty("amount")
    BigDecimal amount;

    @NotBlank
    @JsonProperty("currency")
    String currency = CurrencyType.EUR.toString();

    @NotNull
    @JsonProperty("expiration")
    //Add exception handling with appropriate validation message
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    LocalDateTime expiryDate;

    @JsonProperty("description")
    String description;

}
