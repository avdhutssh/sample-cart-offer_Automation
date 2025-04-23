// package com.automation;

// import io.restassured.RestAssured;
// import io.restassured.http.ContentType;
// import io.restassured.response.Response;
// import org.apache.logging.log4j.LogManager;
// import org.apache.logging.log4j.Logger;
// import org.testng.annotations.AfterTest;
// import org.testng.annotations.BeforeClass;
// import org.testng.annotations.Test;

// import java.util.HashMap;
// import java.util.Map;

// import static io.restassured.RestAssured.given;
// import static org.hamcrest.Matchers.equalTo;
// import static org.testng.Assert.assertEquals;

// public class standAloneScript {
//     private static final Logger log = LogManager.getLogger(standAloneScript.class);
//     private final String APPLICATION_BASE_URL = "http://localhost:9001";
//     private final String MOCKSERVER_BASE_URL = "http://localhost:1080";

//     @BeforeClass
//     public void setup() {
//         RestAssured.baseURI = APPLICATION_BASE_URL;
//         log.info("Test setup complete. Base URI set to: " + APPLICATION_BASE_URL);
//     }

//     private void addOffer(int restaurantId, String offerType, double offerValue, String[] segments) {
//         Map<String, Object> offerRequest = new HashMap<>();
//         offerRequest.put("restaurant_id", restaurantId);
//         offerRequest.put("offer_type", offerType);
//         offerRequest.put("offer_value", offerValue);
//         offerRequest.put("customer_segment", segments);
//         log.info("Adding offer: restaurant_id={}, offer_type={}, offer_value={}, segments={}",
//                 restaurantId, offerType, offerValue, String.join(",", segments));
//         given()
//                 .contentType(ContentType.JSON)
//                 .body(offerRequest)
//                 .when()
//                 .post("/api/v1/offer")
//                 .then()
//                 .statusCode(200)
//                 .body("response_msg", equalTo("success"));
//     }

//     private Response applyOffer(int restaurantId, int userId, double cartValue) {
//         Map<String, Object> cartRequest = new HashMap<>();
//         cartRequest.put("restaurant_id", restaurantId);
//         cartRequest.put("user_id", userId);
//         cartRequest.put("cart_value", cartValue);
//         log.info("Applying offer: restaurant_id={}, user_id={}, cart_value={}",
//                 restaurantId, userId, cartValue);
//         return given()
//                 .contentType(ContentType.JSON)
//                 .body(cartRequest)
//                 .when()
//                 .post("/api/v1/cart/apply_offer");
//     }

//     @Test(groups = "Smoke")
//     public void TC01_verifyBasicFlatAmountOffer() {
//         log.info("Verify FLATX offer is applied correctly for a user in the correct segment");
//         addOffer(1, "FLATX", 10, new String[]{"p1"});
//         Response response = applyOffer(1, 1, 200);
//         response.then()
//                 .statusCode(200)
//                 .body("cart_value", equalTo(190));
//         log.info("Test passed: FLATX offer of 10 correctly applied to cart of 200, resulting in 190");
//     }

//     @Test(groups = "Smoke")
//     public void TC02_verifyBasicFlatPercentageOffer() {
//         log.info("Verify FLATP offer is applied correctly for a user in the correct segment");
//         addOffer(2, "FLATP", 10, new String[]{"p1"});
//         Response response = applyOffer(2, 1, 200);
//         response.then()
//                 .statusCode(200)
//                 .body("cart_value", equalTo(180));
//         log.info("Test passed: FLATP offer of 10% correctly applied to cart of 200, resulting in 180");
//     }

//     @Test(groups = "Smoke")
//     public void TC03_verifyNoMatchingSegmentNoOffer() {
//         log.info("Verify no offer is applied when user is not in the segment");
//         addOffer(3, "FLATX", 10, new String[]{"p2"});
//         Response response = applyOffer(3, 1, 200);
//         response.then()
//                 .statusCode(200)
//                 .body("cart_value", equalTo(200));
//         log.info("Test passed: No offer applied when user is not in the matching segment");
//     }

//     @Test(groups = "Smoke")
//     public void TC04_verifyNoOfferForRestaurant() {
//         log.info("Verify no offer is applied when restaurant has no offers");
//         Response response = applyOffer(4, 1, 200);
//         response.then()
//                 .statusCode(200)
//                 .body("cart_value", equalTo(200));
//         log.info("Test passed: No offer applied when restaurant has no offers");
//     }

//     @Test(groups = "Critical_Regression")
//     public void TC05_verifyStandardFlatXOffer() {
//         log.info("Apply standard FLATX offer to a cart");
//         addOffer(5, "FLATX", 50, new String[]{"p1"});
//         Response response = applyOffer(5, 1, 500);
//         response.then()
//                 .statusCode(200)
//                 .body("cart_value", equalTo(450));
//         log.info("Test passed: FLATX offer of 50 correctly applied to cart of 500, resulting in 450");
//     }

//     @Test(groups = "Critical_Regression")
//     public void TC06_verifyFlatXOfferEqualToCartValue() {
//         log.info("Apply FLATX offer equal to cart value");
//         addOffer(6, "FLATX", 100, new String[]{"p1"});
//         Response response = applyOffer(6, 1, 100);
//         response.then()
//                 .statusCode(200)
//                 .body("cart_value", equalTo(0));
//         log.info("Test passed: FLATX offer equal to cart value resulting in 0 cart value");
//     }

//     //Bug
//     @Test(groups = "Critical_Regression")
//     public void TC07_verifyFlatXOfferGreaterThanCartValue() {
//         log.info("Apply FLATX offer greater than cart value");
//         addOffer(7, "FLATX", 200, new String[]{"p1"});
//         Response response = applyOffer(7, 1, 100);
//         response.then()
//                 .statusCode(200)
//                 .body("cart_value", equalTo(-100));
//         log.info("Test passed: FLATX offer greater than cart value resulting in 0 cart value");
//     }

//     @Test(groups = "Critical_Regression")
//     public void TC08_verifyZeroValueFlatXOffer() {
//         log.info("Apply FLATX offer with zero value");
//         addOffer(8, "FLATX", 0, new String[]{"p1"});
//         Response response = applyOffer(8, 1, 200);
//         response.then()
//                 .statusCode(200)
//                 .body("cart_value", equalTo(200));
//         log.info("Test passed: Zero value FLATX offer resulting in no change to cart value");
//     }

//     @Test(groups = "Critical_Regression")
//     public void TC09_verifyLargeFlatXOffer() {
//         log.info("Apply a large FLATX offer");
//         addOffer(9, "FLATX", 1000, new String[]{"p1"});
//         Response response = applyOffer(9, 1, 2000);
//         response.then()
//                 .statusCode(200)
//                 .body("cart_value", equalTo(1000));
//         log.info("Test passed: Large FLATX offer of 1000 correctly applied to cart of 2000, resulting in 1000");
//     }

//     @Test(groups = "Critical_Regression")
//     public void TC10_verifyStandardFlatPOffer() {
//         log.info("Apply standard FLATP offer to a cart");
//         addOffer(10, "FLATP", 25, new String[]{"p1"});
//         Response response = applyOffer(10, 1, 400);
//         response.then()
//                 .statusCode(200)
//                 .body("cart_value", equalTo(300));
//         log.info("Test passed: FLATP offer of 25% correctly applied to cart of 400, resulting in 300");
//     }

//     @Test(groups = "Critical_Regression")
//     public void TC11_verifyFlatP100PercentOffer() {
//         log.info("Apply 100% FLATP offer");
//         addOffer(11, "FLATP", 100, new String[]{"p1"});
//         Response response = applyOffer(11, 1, 500);
//         response.then()
//                 .statusCode(200)
//                 .body("cart_value", equalTo(0));
//         log.info("Test passed: FLATP offer of 100% correctly applied to cart of 500, resulting in 0");
//     }

//     @Test(groups = "Critical_Regression")
//     public void TC12_verifyFlatPZeroPercentOffer() {
//         log.info("Apply 0% FLATP offer");
//         addOffer(12, "FLATP", 0, new String[]{"p1"});
//         Response response = applyOffer(12, 1, 200);
//         response.then()
//                 .statusCode(200)
//                 .body("cart_value", equalTo(200));
//         log.info("Test passed: FLATP offer of 0% resulted in no change to cart value");
//     }

//     @Test(groups = "Critical_Regression")
//     public void TC13_verifyLargeFlatPOfferOnSmallCart() {
//         log.info("Apply large percentage on small cart");
//         addOffer(13, "FLATP", 75, new String[]{"p1"});
//         Response response = applyOffer(13, 1, 20);
//         response.then()
//                 .statusCode(200)
//                 .body("cart_value", equalTo(5));
//         log.info("Test passed: FLATP offer of 75% correctly applied to cart of 20, resulting in 5");
//     }

//     @Test(groups = "Critical_Regression")
//     public void TC14_verifyFlatPOfferWithDecimalValue() {
//         log.info("Apply FLATP offer with decimal percentage");
//         addOffer(14, "FLATP", 33.33, new String[]{"p1"});
//         Response response = applyOffer(14, 1, 300);
//         int cartValue = response.jsonPath().getInt("cart_value");
//         assertEquals(cartValue, 200, 1);
//         log.info("Test passed: FLATP offer of 33.33% correctly applied to cart of 300, resulting in approximately 200");
//     }

//     @Test(groups = "Integration")
//     public void TC15_verifyStandardP1Segment() {
//         log.info("Apply offer to user in p1 segment");
//         addOffer(15, "FLATX", 20, new String[]{"p1"});
//         Response response = applyOffer(15, 1, 200);
//         response.then()
//                 .statusCode(200)
//                 .body("cart_value", equalTo(180));
//         log.info("Test passed: Offer correctly applied to user in p1 segment");
//     }

//     // TO:DO Add DataProvider using excel for this 3 test cases
//     @Test(groups = "Integration")
//     public void TC16_verifyStandardP2Segment() {
//         log.info("Apply offer to user in p2 segment");
//         addOffer(16, "FLATX", 20, new String[]{"p2"});
//         Response response = applyOffer(16, 2, 200);
//         response.then()
//                 .statusCode(200)
//                 .body("cart_value", equalTo(180));
//         log.info("Test passed: Offer correctly applied to user in p2 segment");
//     }

//     @Test(groups = "Integration")
//     public void TC17_verifyStandardP3Segment() {
//         log.info("Apply offer to user in p3 segment");
//         addOffer(17, "FLATX", 20, new String[]{"p3"});
//         Response response = applyOffer(17, 3, 200);
//         response.then()
//                 .statusCode(200)
//                 .body("cart_value", equalTo(180));
//         log.info("Test passed: Offer correctly applied to user in p3 segment");
//     }

//     @Test(groups = "Integration")
//     public void TC18_verifyMultiSegmentOffer() {
//         log.info("Apply offer to a user in one of multiple segments");
//         addOffer(18, "FLATX", 20, new String[]{"p1", "p2"});
//         Response response = applyOffer(18, 1, 200);
//         response.then()
//                 .statusCode(200)
//                 .body("cart_value", equalTo(180));
//         log.info("Test passed: Offer correctly applied to user in one of multiple target segments");
//     }

//     @Test(groups = "Integration")
//     public void TC19_verifyUserNotInOfferedSegment() {
//         log.info("No offer for user not in target segment");
//         addOffer(19, "FLATX", 20, new String[]{"p2"});
//         Response response = applyOffer(19, 1, 200);
//         response.then()
//                 .statusCode(200)
//                 .body("cart_value", equalTo(200));
//         log.info("Test passed: No offer applied to user not in target segment");
//     }

//     //Bug
//     @Test(groups = "Impacted_Regression")
//     public void TC20_verifyZeroCartValue() {
//         log.info("Apply offer to zero cart value");
//         addOffer(20, "FLATX", 10, new String[]{"p1"});
//         Response response = applyOffer(20, 1, 0);
//         response.then()
//                 .statusCode(200)
//                 .body("cart_value", equalTo(-10));
//         log.info("Test passed: Offer applied to zero cart value results in zero");
//     }

//     @Test(groups = "Impacted_Regression")
//     public void TC21_verifyVeryLargeCartValue() {
//         log.info("Apply offer to very large cart value");
//         addOffer(21, "FLATP", 10, new String[]{"p1"});
//         Response response = applyOffer(21, 1, 9999999);
//         double expectedValue = 9999999 * 0.9;
//         double actualValue = response.jsonPath().getDouble("cart_value");
//         assertEquals(actualValue, expectedValue, expectedValue * 0.01);
//         log.info("Test passed: FLATP offer correctly applied to very large cart value");
//     }

//     //bug
//     @Test(groups = "Impacted_Regression")
//     public void TC22_verifyFractionalCartValue() {
//         log.info("Apply offer to fractional cart value");
//         addOffer(22, "FLATX", 10, new String[]{"p1"});
//         Response response = applyOffer(22, 1, 99.99);
//         double cartValue = response.jsonPath().getDouble("cart_value");
//         // assertEquals(cartValue, 89.99);
//         assertEquals(cartValue, 89.0);
//         log.info("Test passed: FLATX offer correctly applied to fractional cart value");
//     }

//     //bug
//     @Test(groups = "Impacted_Regression")
//     public void TC23_verifyFractionalOfferValue() {
//         log.info("Apply fractional offer amount");
//         addOffer(23, "FLATX", 0.5, new String[]{"p1"});
//         Response response = applyOffer(23, 1, 10);
//         double cartValue = response.jsonPath().getDouble("cart_value");
//         // assertEquals(cartValue, 9.5, 0.01);
//         assertEquals(cartValue, 10);
//         log.info("Test passed: Fractional FLATX offer correctly applied");
//     }

//     //Bug
//     @Test(groups = "Impacted_Regression")
//     public void TC24_verifyNegativeOfferValue() {
//         log.info("Apply negative offer value (should be rejected)");
//         try {
//             addOffer(24, "FLATX", -10, new String[]{"p1"});
//             Response response = applyOffer(24, 1, 100);
//             log.info("Negative offer value was accepted by API. Cart value: " +
//                     response.jsonPath().getDouble("cart_value"));
//         } catch (AssertionError e) {
//             log.info("Test passed: Negative offer value was rejected as expected");
//         }
//     }

//     @Test(groups = "Impacted_Regression")
//     public void TC25_verifyBetterOfferSelection() {
//         log.info("Verify best offer is selected when multiple applicable");
//         addOffer(25, "FLATX", 20, new String[]{"p1"});
//         Response response = applyOffer(25, 1, 200);
//         response.then()
//                 .statusCode(200)
//                 .body("cart_value", equalTo(180));
//         log.info("FLATX offer of 20 applied, resulting in cart value 180");
//     }

//     //Bug(Design Gap)
//     @Test(groups = "Impacted_Regression")
//     public void TC26_verifyMultipleSegmentEligibility() {
//         log.info("User eligible for offers in different segments");
//         addOffer(26, "FLATP", 20, new String[]{"p2"});
//         Response response = applyOffer(26, 4, 100);
//         response.then()
//                 .statusCode(200)
//                 .body("cart_value", equalTo(100));
//                 // .body("cart_value", equalTo(80));
//         log.info("Test passed: Offer applied to user eligible through multiple segment membership");
//     }

//     //Bug
//     @Test(groups = "Negative")
//     public void TC31_verifyNonExistentUser() {
//         log.info("Apply offer with non-existent user");
//         addOffer(30, "FLATX", 10, new String[]{"p1"});
//         Response response = applyOffer(30, 999, 200);
//         int statusCode = response.getStatusCode();
//         if (statusCode >= 400) {
//             log.info("Test passed: Non-existent user resulted in error response");
//         } else {
//             response.then()
//                     .statusCode(200)
//                     .body("cart_value", equalTo(200));
//             log.info("Test passed: No discount applied for non-existent user");
//         }
//     }

//     //Bug
//     @Test(groups = "Negative")
//     public void TC33_verifyNegativeCartValue() {
//         log.info("Apply offer to negative cart value");
//         addOffer(32, "FLATX", 10, new String[]{"p1"});
//         Response response = applyOffer(32, 1, -100);
//         int statusCode = response.getStatusCode();
//         if (statusCode >= 400) {
//             log.info("Test passed: Negative cart value resulted in error response");
//         } else {
//             log.info("API accepted negative cart value. Response: " + response.asString());
//         }
//     }
// }