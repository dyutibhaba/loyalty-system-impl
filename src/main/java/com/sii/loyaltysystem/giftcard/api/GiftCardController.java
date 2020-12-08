package com.sii.loyaltysystem.giftcard.api;

import com.sii.loyaltysystem.core.api.response.Response;
import com.sii.loyaltysystem.giftcard.api.model.GiftCardDto;
import com.sii.loyaltysystem.giftcard.api.request.GiftCardRequest;
import com.sii.loyaltysystem.giftcard.api.response.GiftCardResponse;
import com.sii.loyaltysystem.giftcard.api.service.GiftCardService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.math.BigDecimal;
import java.util.Collection;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/loyalty/giftcards")
public class GiftCardController {

    private final GiftCardService giftCardService;

    @GetMapping
    public Response<Collection<GiftCardDto>> getAllGiftCards() {
        Collection<GiftCardDto> giftCards = giftCardService.findAllGiftCards();
        return Response.success(giftCards);
    }

    @PostMapping
    public Response<GiftCardResponse> addGiftCard(@Valid @RequestBody GiftCardRequest giftCard) {
        GiftCardResponse giftCardResponse = giftCardService.addGiftCard(giftCard);
        return Response.success(giftCardResponse);
    }

    @GetMapping("/{giftcard_id}")
    public Response<GiftCardDto> getGiftCard(@PathVariable("giftcard_id") String giftCardId) {
        GiftCardDto giftCard = giftCardService.findGiftCardById(giftCardId);
        return Response.success(giftCard);
    }

    @GetMapping("/")
    public Response<Collection<GiftCardDto>> getAllActiveGiftCards(@PathParam("amount") String amount, @PathParam("status") String status) {
        Collection<GiftCardDto> giftCards = giftCardService.findAllCardsByAmountAndStatus(new BigDecimal(amount), status);
        return Response.success(giftCards);
    }

}
