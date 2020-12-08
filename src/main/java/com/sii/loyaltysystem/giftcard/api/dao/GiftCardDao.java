package com.sii.loyaltysystem.giftcard.api.dao;

import com.sii.loyaltysystem.env.EnvironmentParameters;
import com.sii.loyaltysystem.giftcard.api.model.GiftCard;
import com.sii.loyaltysystem.giftcard.api.model.GiftCardDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class GiftCardDao {

    EnvironmentParameters environmentParameters;
    Comparator<GiftCard> comparatorById
            = Comparator.comparing(GiftCard::getGiftCardId, (giftCard1, giftCard2) -> giftCard1.compareTo(giftCard2));
    SortedSet<GiftCard> giftCardStore = new TreeSet<>(comparatorById);

    public boolean save(GiftCard giftCard) {
        return giftCardStore.add(giftCard);
    }

    public String findMaxGiftCardId() {
        if (giftCardStore.isEmpty()) {
            return environmentParameters.getGiftCardInitId();
        }
        return giftCardStore.last().getGiftCardId();
    }

    public Collection<GiftCardDto> findAll() {
        return giftCardStore.stream().map(this::buildGiftCardDto).collect(Collectors.toList());
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

    public Optional<GiftCardDto> findOne(String giftCardId) {
        return giftCardStore.stream()
                .filter(e -> e.getGiftCardId().equals(giftCardId))
                .map(this::buildGiftCardDto).findAny();
    }
}
