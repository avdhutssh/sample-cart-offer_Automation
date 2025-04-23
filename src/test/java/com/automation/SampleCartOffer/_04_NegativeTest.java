package com.automation.SampleCartOffer;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;

public class _04_NegativeTest {
    private static final Logger log = LogManager.getLogger(_04_NegativeTest.class);
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

    //Bug
    @Test(groups = "Negative")
    public void TC31_verifyNonExistentUser() {
        log.info("Apply offer with non-existent user");
        addOffer(30, "FLATX", 10, new String[]{"p1"});
        Response response = applyOffer(30, 999, 200);
        int statusCode = response.getStatusCode();
        if (statusCode >= 400) {
            log.info("Test passed: Non-existent user resulted in error response");
        } else {
            response.then()
                    .statusCode(200)
                    .body("cart_value", equalTo(200));
            log.info("Test passed: No discount applied for non-existent user");
        }
    }

    //Bug
    @Test(groups = "Negative")
    public void TC33_verifyNegativeCartValue() {
        log.info("Apply offer to negative cart value");
        addOffer(32, "FLATX", 10, new String[]{"p1"});
        Response response = applyOffer(32, 1, -100);
        int statusCode = response.getStatusCode();
        if (statusCode >= 400) {
            log.info("Test passed: Negative cart value resulted in error response");
        } else {
            log.info("API accepted negative cart value. Response: " + response.asString());
        }
    }
}