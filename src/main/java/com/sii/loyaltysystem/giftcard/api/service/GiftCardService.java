package com.sii.loyaltysystem.giftcard.api.service;

import com.sii.loyaltysystem.core.exception.NoDataFoundException;
import com.sii.loyaltysystem.core.util.GiftCardUtil;
import com.sii.loyaltysystem.giftcard.api.dao.GiftCardDao;
import com.sii.loyaltysystem.giftcard.api.model.GiftCard;
import com.sii.loyaltysystem.giftcard.api.model.GiftCardDto;
import com.sii.loyaltysystem.giftcard.api.request.GiftCardRequest;
import com.sii.loyaltysystem.giftcard.api.response.GiftCardResponse;
import com.sii.loyaltysystem.giftcard.api.type.CurrencyType;
import com.sii.loyaltysystem.giftcard.api.type.StatusType;
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
        giftCardDao.save(giftCard);
        return buildGiftCardResponse(giftCard);
    }

    private GiftCardResponse buildGiftCardResponse(GiftCard giftCard) {
        return GiftCardResponse.builder()
                .message("Gift card successfully added!")
                .giftCardId(giftCard.getGiftCardId())
                .build();
    }

    private GiftCard buildGiftCard(GiftCardRequest giftCardRequest) {
        return GiftCard.builder()
                .giftCardId(giftCardUtil.generateNewGiftCardId())
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
        return giftCardDao.findOne(giftCardId);
    }

    public Collection<GiftCardDto> findAllCardsByAmountAndStatus(BigDecimal amount, String status) {
        Collection<GiftCardDto> giftCards = giftCardDao.findAll();
        return giftCards.stream()
                .filter(compareByAmount(amount))
                .filter(compareByExpiryStatus(getStatusType(status)))
                .collect(Collectors.toList());
    }

    private StatusType getStatusType(String status) {
        Optional<StatusType> statusType = StatusType.getStatusType(status);
        return statusType.orElseThrow(() -> new NoDataFoundException(String.format("Invalid status %s provided", status)));
    }

    private Predicate<GiftCardDto> compareByAmount(BigDecimal amount) {
        return (e -> e.getAmount().compareTo(amount) > 0);
    }

    private Predicate<GiftCardDto> compareByExpiryStatus(StatusType status) {
        if (StatusType.ACTIVE.equals(status)) {
            return (e -> e.getExpiryDate().compareTo(LocalDateTime.now()) > 0);
        }
        return (e -> e.getExpiryDate().compareTo(LocalDateTime.now()) < 0);
    }
}
