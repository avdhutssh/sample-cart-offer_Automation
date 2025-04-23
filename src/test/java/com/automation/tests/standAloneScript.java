package com.automation.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;

public class standAloneScript {

    private final String APPLICATION_BASE_URL = "http://localhost:9001";
    private final String MOCKSERVER_BASE_URL = "http://localhost:1080";

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = APPLICATION_BASE_URL;
    }

    private void addOffer(int restaurantId, String offerType, double offerValue, String[] segments) {
        Map<String, Object> offerRequest = new HashMap<>();
        offerRequest.put("restaurant_id", restaurantId);
        offerRequest.put("offer_type", offerType);
        offerRequest.put("offer_value", offerValue);
        offerRequest.put("customer_segment", segments);

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

        return given()
                .contentType(ContentType.JSON)
                .body(cartRequest)
                .when()
                .post("/api/v1/cart/apply_offer");
    }

    // TC01: Basic FLATX offer application
    @Test
    public void testBasicFlatAmountOffer() {
        // Add offer for restaurant 1
        addOffer(1, "FLATX", 10, new String[]{"p1"});

        // Apply offer and verify
        Response response = applyOffer(1, 1, 200);
        System.out.println("------------------------");
        System.out.println(response);
        System.out.println("------------------------");
        response.then()
                .statusCode(200)
                .body("cart_value", equalTo(190));
    }

    // TC02: Basic FLATP offer application
    @Test
    public void testBasicFlatPercentageOffer() {
        // Add offer for restaurant 2
        addOffer(2, "FLATP", 10, new String[]{"p1"});

        // Apply offer and verify
        Response response = applyOffer(2, 1, 200);

        response.then()
                .statusCode(200)
                .body("cart_value", equalTo(180));
    }
}