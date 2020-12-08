package com.sii.loyaltysystem.giftcard.api.dao;

import com.sii.loyaltysystem.core.exception.NoDataFoundException;
import com.sii.loyaltysystem.env.EnvironmentParameters;
import com.sii.loyaltysystem.giftcard.api.model.GiftCard;
import com.sii.loyaltysystem.giftcard.api.model.GiftCardDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class GiftCardDao {

    private final EnvironmentParameters environmentParameters;
    private final Map<String, GiftCard> giftCardStore = new HashMap<>();

    public void save(GiftCard giftCard) {
        giftCardStore.put(giftCard.getGiftCardId(), giftCard);
    }

    public Collection<GiftCardDto> findAll() {
        return giftCardStore.entrySet()
                .stream()
                .map(e -> buildGiftCardDto(e.getValue()))
                .collect(Collectors.toList());
    }

    private GiftCardDto buildGiftCardDto(GiftCard giftCard) {
        return GiftCardDto.builder()
                .giftCardId(giftCard.getGiftCardId())
                .amount(giftCard.getAmount())
                .currency(giftCard.getCurrency())
                .expiryDate(giftCard.getExpiryDate())
                .description(giftCard.getDescription())
                .build();
    }

    public GiftCardDto findOne(String giftCardId) {
        GiftCard giftCard = giftCardStore.get(giftCardId);
        if(null == giftCard) {
            throw new NoDataFoundException(String.format("No gift card found in the system with id - %s", giftCardId));
        }
        return buildGiftCardDto(giftCard);
    }
}
