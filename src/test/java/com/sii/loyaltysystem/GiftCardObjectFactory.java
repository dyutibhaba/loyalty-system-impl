package com.sii.loyaltysystem;

import com.sii.loyaltysystem.giftcard.api.model.GiftCard;
import com.sii.loyaltysystem.giftcard.api.model.GiftCardDto;
import com.sii.loyaltysystem.giftcard.api.request.GiftCardRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;

public class GiftCardObjectFactory {

    public static final String FIRST_GIFT_CARD_ID = "GC101";

    public static GiftCardRequest.GiftCardRequestBuilder aGiftCardRequest() {
        return GiftCardRequest.builder()
                .amount(BigDecimal.valueOf(50.10))
                .expiryDate(LocalDateTime.of(2020, Month.DECEMBER, 5, 12, 00))
                .description("Sample gift card");

    }

    public static GiftCard.GiftCardBuilder aGiftCard() {
        return GiftCard.builder()
                .amount(BigDecimal.valueOf(50.10))
                .expiryDate(LocalDateTime.of(2020, Month.DECEMBER, 5, 12, 00))
                .description("Sample gift card");

    }

    public static GiftCardDto.GiftCardDtoBuilder aGiftCardDto() {
        return GiftCardDto.builder()
                .giftCardId(FIRST_GIFT_CARD_ID)
                .amount(BigDecimal.valueOf(50.10))
                .expiryDate(LocalDateTime.of(2020, Month.DECEMBER, 5, 12, 00))
                .description("Sample gift card");
    }
}
