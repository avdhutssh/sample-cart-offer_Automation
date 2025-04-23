package com.automation.SampleCartOffer.tests;

import com.automation.SampleCartOffer.specs.ResponseSpecificationBuilder;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

public class _01_smokeTest extends _00_BaseTest {
    private static final Logger log = LogManager.getLogger(_01_smokeTest.class);

    @Test(groups = "Smoke")
    public void TC01_verifyBasicFlatAmountOffer() {
        log.info("Verify FLATX offer is applied correctly for a user in the correct segment");
        addOffer(1, "FLATX", 10, new String[]{"p1"});
        Response response = applyOffer(1, 1, 200);
        response.then()
                .spec(ResponseSpecificationBuilder.getSuccessResponseSpec())
                .body("cart_value", equalTo(190));
        log.info("Test passed: FLATX offer of 10 correctly applied to cart of 200, resulting in 190");
    }

    @Test(groups = "Smoke")
    public void TC02_verifyBasicFlatPercentageOffer() {
        log.info("Verify FLATP offer is applied correctly for a user in the correct segment");
        addOffer(2, "FLATP", 10, new String[]{"p1"});
        Response response = applyOffer(2, 1, 200);
        response.then()
                .spec(ResponseSpecificationBuilder.getSuccessResponseSpec())
                .body("cart_value", equalTo(180));
        log.info("Test passed: FLATP offer of 10% correctly applied to cart of 200, resulting in 180");
    }

    @Test(groups = "Smoke")
    public void TC03_verifyNoMatchingSegmentNoOffer() {
        log.info("Verify no offer is applied when user is not in the segment");
        addOffer(3, "FLATX", 10, new String[]{"p2"});
        Response response = applyOffer(3, 1, 200);
        response.then()
                .spec(ResponseSpecificationBuilder.getSuccessResponseSpec())
                .body("cart_value", equalTo(200));
        log.info("Test passed: No offer applied when user is not in the matching segment");
    }

    @Test(groups = "Smoke")
    public void TC04_verifyNoOfferForRestaurant() {
        log.info("Verify no offer is applied when restaurant has no offers");
        Response response = applyOffer(4, 1, 200);
        response.then()
                .spec(ResponseSpecificationBuilder.getSuccessResponseSpec())
                .body("cart_value", equalTo(200));
        log.info("Test passed: No offer applied when restaurant has no offers");
    }
}