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

import static com.sii.loyaltysystem.GiftCardObjectFactory.aGiftCardRequest;
import static com.sii.loyaltysystem.GiftCardObjectFactory.aGiftCard;
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
}
