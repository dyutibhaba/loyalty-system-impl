package com.sii.loyaltysystem;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.BasicJsonTester;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoyaltySystemBaseIT {

    protected final static String URL_API_GIFT_CARD = "/api/loyalty/giftcards/%s";

    @Autowired
    protected TestRestTemplate restTemplate;

    protected BasicJsonTester jsonTester = new BasicJsonTester(LoyaltySystemBaseIT.class);

    protected BasicJsonTester jsonTester() {
        return jsonTester;
    }

}
