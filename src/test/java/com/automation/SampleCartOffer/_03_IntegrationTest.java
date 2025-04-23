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

public class _03_IntegrationTest {
    private static final Logger log = LogManager.getLogger(_03_IntegrationTest.class);
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

    @Test(groups = "Integration")
    public void TC15_verifyStandardP1Segment() {
        log.info("Apply offer to user in p1 segment");
        addOffer(15, "FLATX", 20, new String[]{"p1"});
        Response response = applyOffer(15, 1, 200);
        response.then()
                .statusCode(200)
                .body("cart_value", equalTo(180));
        log.info("Test passed: Offer correctly applied to user in p1 segment");
    }

    // TO:DO Add DataProvider using excel for this 3 test cases
    @Test(groups = "Integration")
    public void TC16_verifyStandardP2Segment() {
        log.info("Apply offer to user in p2 segment");
        addOffer(16, "FLATX", 20, new String[]{"p2"});
        Response response = applyOffer(16, 2, 200);
        response.then()
                .statusCode(200)
                .body("cart_value", equalTo(180));
        log.info("Test passed: Offer correctly applied to user in p2 segment");
    }

    @Test(groups = "Integration")
    public void TC17_verifyStandardP3Segment() {
        log.info("Apply offer to user in p3 segment");
        addOffer(17, "FLATX", 20, new String[]{"p3"});
        Response response = applyOffer(17, 3, 200);
        response.then()
                .statusCode(200)
                .body("cart_value", equalTo(180));
        log.info("Test passed: Offer correctly applied to user in p3 segment");
    }

    @Test(groups = "Integration")
    public void TC18_verifyMultiSegmentOffer() {
        log.info("Apply offer to a user in one of multiple segments");
        addOffer(18, "FLATX", 20, new String[]{"p1", "p2"});
        Response response = applyOffer(18, 1, 200);
        response.then()
                .statusCode(200)
                .body("cart_value", equalTo(180));
        log.info("Test passed: Offer correctly applied to user in one of multiple target segments");
    }

    @Test(groups = "Integration")
    public void TC19_verifyUserNotInOfferedSegment() {
        log.info("No offer for user not in target segment");
        addOffer(19, "FLATX", 20, new String[]{"p2"});
        Response response = applyOffer(19, 1, 200);
        response.then()
                .statusCode(200)
                .body("cart_value", equalTo(200));
        log.info("Test passed: No offer applied to user not in target segment");
    }

}