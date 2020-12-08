package com.sii.loyaltysystem.giftcard;

import com.sii.loyaltysystem.LoyaltySystemBaseIT;
import com.sii.loyaltysystem.core.api.response.Response;
import com.sii.loyaltysystem.env.EnvironmentParameters;
import com.sii.loyaltysystem.giftcard.api.GiftCardController;
import com.sii.loyaltysystem.giftcard.api.model.GiftCardDto;
import com.sii.loyaltysystem.giftcard.api.request.GiftCardRequest;
import com.sii.loyaltysystem.giftcard.api.service.GiftCardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.sii.loyaltysystem.GiftCardObjectFactory.aGiftCardDto;
import static com.sii.loyaltysystem.GiftCardObjectFactory.aGiftCardRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class GiftCardIT extends LoyaltySystemBaseIT {

    @Autowired
    private EnvironmentParameters environmentParameters;
    @Mock
    private GiftCardService giftCardService;

    private GiftCardController giftCardController;

    public static final String FIRST_GIFT_CARD_ID = "GC101";

    @BeforeEach
    void runBefore() {
        MockitoAnnotations.openMocks(this);
        giftCardController = new GiftCardController(giftCardService);
    }

    @Test
    void shouldAddAGiftCard() {
        //given
        GiftCardRequest.GiftCardRequestBuilder giftCardRequestBuilder = aGiftCardRequest();
        GiftCardRequest giftCardRequest = giftCardRequestBuilder.build();
        String path = "add";

        //when
        ResponseEntity<String> responseEntity = this.restTemplate.postForEntity(String.format(URL_API_GIFT_CARD, path), giftCardRequest, String.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        JsonContent<Object> jsonContext = jsonTester().from(responseEntity.getBody());
        assertThat(jsonContext).isNotNull();
        assertThat(jsonContext).extractingJsonPathValue("$.data").isNotNull();
        assertThat(jsonContext).extractingJsonPathStringValue("$.data.giftCardId").isNotBlank();
        assertThat(jsonContext).extractingJsonPathStringValue("$.data.giftCardId").isEqualTo(FIRST_GIFT_CARD_ID);

    }

    @Test
    void shouldGetAGiftCard() {
        //given
        GiftCardDto.GiftCardDtoBuilder giftCardDtoBuilder = aGiftCardDto();
        GiftCardDto giftCardDto = giftCardDtoBuilder.build();

        //when
        when(giftCardService.findGiftCardById(FIRST_GIFT_CARD_ID)).thenReturn(giftCardDto);
        Response<GiftCardDto> response = giftCardController.getGiftCard(FIRST_GIFT_CARD_ID);

        //then
        assertThat(response).isNotNull();
        assertThat(response.getData().getGiftCardId()).isEqualTo(FIRST_GIFT_CARD_ID);
    }


}
