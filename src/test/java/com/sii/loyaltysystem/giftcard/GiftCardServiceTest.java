package com.sii.loyaltysystem.giftcard;

import com.sii.loyaltysystem.core.util.GiftCardUtil;
import com.sii.loyaltysystem.giftcard.api.dao.GiftCardDao;
import com.sii.loyaltysystem.giftcard.api.model.GiftCard;
import com.sii.loyaltysystem.giftcard.api.model.GiftCardDto;
import com.sii.loyaltysystem.giftcard.api.request.GiftCardRequest;
import com.sii.loyaltysystem.giftcard.api.response.GiftCardResponse;
import com.sii.loyaltysystem.giftcard.api.service.GiftCardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;

import static com.sii.loyaltysystem.GiftCardObjectFactory.aGiftCard;
import static com.sii.loyaltysystem.GiftCardObjectFactory.aGiftCardRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class GiftCardServiceTest {

    public static final String FIRST_GIFT_CARD_ID = "GC101";
    @Autowired
    private GiftCardDao giftCardDao;
    @Autowired
    private GiftCardUtil giftCardUtil;

    private GiftCardService giftCardService;

    @BeforeEach
    public void runBefore() {
        MockitoAnnotations.openMocks(this);
        giftCardService = new GiftCardService(giftCardDao, giftCardUtil);
    }


    @Test
    void testAddGiftCard() {
        //given
        GiftCard.GiftCardBuilder giftCardBuilder = aGiftCard();
        GiftCard giftCard = giftCardBuilder.build();

        GiftCardRequest.GiftCardRequestBuilder giftCardRequestBuilder = aGiftCardRequest();
        GiftCardRequest giftCardRequest = giftCardRequestBuilder.build();
        GiftCardDao giftCardDaoMock = mock(GiftCardDao.class);

        //when
        when(giftCardDaoMock.save(giftCard)).thenReturn(true);
        GiftCardResponse giftCardResponse = giftCardService.addGiftCard(giftCardRequest);

        //then
        assertThat(giftCardResponse).isNotNull();
        assertThat(giftCardResponse.getGiftCardId()).isEqualTo(FIRST_GIFT_CARD_ID);
    }

    @Test
    void shouldFindGiftCardById() {
        //given
        GiftCardRequest.GiftCardRequestBuilder giftCardRequestBuilder = aGiftCardRequest();
        GiftCardRequest giftCardRequest = giftCardRequestBuilder.build();
        giftCardService.addGiftCard(giftCardRequest);

        //when
        GiftCardDto giftCardDto = giftCardService.findGiftCardById(FIRST_GIFT_CARD_ID);

        //then
        assertThat(giftCardDto).isNotNull();
        assertThat(giftCardDto.getAmount()).isNotNull();
        assertThat(giftCardDto.getCurrency()).isNotNull();
        assertThat(giftCardDto.getExpiryDate()).isNotNull();
        assertThat(giftCardDto.getGiftCardId()).isEqualTo(FIRST_GIFT_CARD_ID);
    }

    @Test
    void shouldReturnActiveGiftCardAndAmountGreaterThanGiven() {
        //given
        GiftCardRequest.GiftCardRequestBuilder giftCardRequestBuilder1 = aGiftCardRequest();
        GiftCardRequest.GiftCardRequestBuilder giftCardRequestBuilder2 = aGiftCardRequest();
        giftCardRequestBuilder2.expiryDate(LocalDateTime.of(2020, Month.NOVEMBER, 30, 11, 00));
        giftCardService.addGiftCard(giftCardRequestBuilder1.build());
        giftCardService.addGiftCard(giftCardRequestBuilder2.build());
        BigDecimal amount = BigDecimal.valueOf(30.10);

        //when
        Collection<GiftCardDto> allActiveGiftCards = giftCardService.findAllActiveCards(amount);

        //then
        assertThat(allActiveGiftCards).isNotNull();
        assertThat(allActiveGiftCards.size()).isGreaterThanOrEqualTo(1);

        allActiveGiftCards.stream().findFirst().ifPresent(e -> assertThat(e.getExpiryDate()).isAfter(LocalDateTime.now()));
    }
}
