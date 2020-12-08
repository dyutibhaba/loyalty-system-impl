package com.sii.loyaltysystem.giftcard.api.service;

import com.sii.loyaltysystem.core.exception.NoDataFoundException;
import com.sii.loyaltysystem.core.util.GiftCardUtil;
import com.sii.loyaltysystem.giftcard.api.dao.GiftCardDao;
import com.sii.loyaltysystem.giftcard.api.model.GiftCard;
import com.sii.loyaltysystem.giftcard.api.model.GiftCardDto;
import com.sii.loyaltysystem.giftcard.api.request.GiftCardRequest;
import com.sii.loyaltysystem.giftcard.api.response.GiftCardResponse;
import com.sii.loyaltysystem.giftcard.api.type.CurrencyType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@Slf4j
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class GiftCardService {

    GiftCardDao giftCardDao;
    GiftCardUtil giftCardUtil;

    public GiftCardResponse addGiftCard(GiftCardRequest giftCardRequest) {
        GiftCard giftCard = buildGiftCard(giftCardRequest);
        if (!giftCardDao.save(giftCard)) {
            throw new UnsupportedOperationException("Could not add gift card to the system!");
        }
        return buildGiftCardResponse(giftCard);
    }

    private GiftCardResponse buildGiftCardResponse(GiftCard giftCard) {
        return GiftCardResponse.builder()
                .message("Successfully added!")
                .giftCardId(giftCard.getGiftCardId())
                .build();
    }

    private GiftCard buildGiftCard(GiftCardRequest giftCardRequest) {
        String maxGiftCardId = giftCardDao.findMaxGiftCardId();
        return GiftCard.builder()
                .giftCardId(giftCardUtil.generateNewGiftCardId(maxGiftCardId))
                .amount(giftCardRequest.getAmount())
                .currency(CurrencyType.valueOf(giftCardRequest.getCurrency()))
                .expiryDate(giftCardRequest.getExpiryDate())
                .description(giftCardRequest.getDescription())
                .build();
    }

    public Collection<GiftCardDto> findAllGiftCards() {
        Collection<GiftCardDto> giftCards = giftCardDao.findAll();
        if (giftCards.isEmpty()) {
            throw new NoDataFoundException("The system is empty currently!");
        }
        return giftCards;
    }

    public GiftCardDto findGiftCardById(String giftCardId) {
        Optional<GiftCardDto> giftCardOptional = giftCardDao.findOne(giftCardId);
        return giftCardOptional.orElseThrow(() ->
                new NoDataFoundException(String.format("No gift card found in the system with id - %s", giftCardId)));
    }

    public Collection<GiftCardDto> findAllActiveCards(BigDecimal amount) {
        Collection<GiftCardDto> giftCards = giftCardDao.findAll();
        return giftCards.stream()
                .filter(compareAmountPredicate(amount))
                .collect(Collectors.toList());
    }

    private Predicate<GiftCardDto> compareAmountPredicate(BigDecimal amount) {
        return e -> (isAmountGreaterThanGiven(e, amount) && isExpiryDateGreaterThanNow(e));
    }

    private boolean isExpiryDateGreaterThanNow(GiftCardDto e) {
        return (e.getExpiryDate().compareTo(LocalDateTime.now()) > 0);
    }

    private boolean isAmountGreaterThanGiven(GiftCardDto giftCardDto, BigDecimal amount) {
        return (giftCardDto.getAmount().compareTo(amount) > 0);
    }
}
