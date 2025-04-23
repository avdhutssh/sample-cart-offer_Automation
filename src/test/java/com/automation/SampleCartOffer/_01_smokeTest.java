package com.automation.SampleCartOffer;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;

public class _01_smokeTest {
    private static final Logger log = LogManager.getLogger(_01_smokeTest.class);
    private final String APPLICATION_BASE_URL = "http://localhost:9001";
    private final String MOCKSERVER_BASE_URL = "http://localhost:1080";

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = APPLICATION_BASE_URL;
        log.info("Test setup complete. Base URI set to: " + APPLICATION_BASE_URL);
    }

    private void addOffer(int restaurantId, String offerType, double offerValue, String[] segments) {
        Map<String, Object> offerRequest = new HashMap<>();
        offerRequest.put("restaurant_id", restaurantId);
        offerRequest.put("offer_type", offerType);
        offerRequest.put("offer_value", offerValue);
        offerRequest.put("customer_segment", segments);
        log.info("Adding offer: restaurant_id={}, offer_type={}, offer_value={}, segments={}",
                restaurantId, offerType, offerValue, String.join(",", segments));
        given()
                .contentType(ContentType.JSON)
                .body(offerRequest)
                .when()
                .post("/api/v1/offer")
                .then()
                .statusCode(200)
                .body("response_msg", equalTo("success"));
    }

    private Response applyOffer(int restaurantId, int userId, double cartValue) {
        Map<String, Object> cartRequest = new HashMap<>();
        cartRequest.put("restaurant_id", restaurantId);
        cartRequest.put("user_id", userId);
        cartRequest.put("cart_value", cartValue);
        log.info("Applying offer: restaurant_id={}, user_id={}, cart_value={}",
                restaurantId, userId, cartValue);
        return given()
                .contentType(ContentType.JSON)
                .body(cartRequest)
                .when()
                .post("/api/v1/cart/apply_offer");
    }

    @Test(groups = "Smoke")
    public void TC01_verifyBasicFlatAmountOffer() {
        log.info("Verify FLATX offer is applied correctly for a user in the correct segment");
        addOffer(1, "FLATX", 10, new String[]{"p1"});
        Response response = applyOffer(1, 1, 200);
        response.then()
                .statusCode(200)
                .body("cart_value", equalTo(190));
        log.info("Test passed: FLATX offer of 10 correctly applied to cart of 200, resulting in 190");
    }

    @Test(groups = "Smoke")
    public void TC02_verifyBasicFlatPercentageOffer() {
        log.info("Verify FLATP offer is applied correctly for a user in the correct segment");
        addOffer(2, "FLATP", 10, new String[]{"p1"});
        Response response = applyOffer(2, 1, 200);
        response.then()
                .statusCode(200)
                .body("cart_value", equalTo(180));
        log.info("Test passed: FLATP offer of 10% correctly applied to cart of 200, resulting in 180");
    }

    @Test(groups = "Smoke")
    public void TC03_verifyNoMatchingSegmentNoOffer() {
        log.info("Verify no offer is applied when user is not in the segment");
        addOffer(3, "FLATX", 10, new String[]{"p2"});
        Response response = applyOffer(3, 1, 200);
        response.then()
                .statusCode(200)
                .body("cart_value", equalTo(200));
        log.info("Test passed: No offer applied when user is not in the matching segment");
    }

    @Test(groups = "Smoke")
    public void TC04_verifyNoOfferForRestaurant() {
        log.info("Verify no offer is applied when restaurant has no offers");
        Response response = applyOffer(4, 1, 200);
        response.then()
                .statusCode(200)
                .body("cart_value", equalTo(200));
        log.info("Test passed: No offer applied when restaurant has no offers");
    }

}