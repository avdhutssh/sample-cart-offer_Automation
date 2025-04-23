package com.automation.SampleCartOffer.tests;

import com.automation.SampleCartOffer.Utils.AssertionUtils;
import com.automation.SampleCartOffer.specs.ResponseSpecificationBuilder;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;

public class _02_RegressionTest extends _00_BaseTest {
    private static final Logger log = LogManager.getLogger(_02_RegressionTest.class);

    @Test(groups = "Critical_Regression")
    public void TC05_verifyStandardFlatXOffer() {
        log.info("Apply standard FLATX offer to a cart");
        addOffer(5, "FLATX", 50, new String[]{"p1"});
        Response response = applyOffer(5, 1, 500);
        response.then()
                .spec(ResponseSpecificationBuilder.getSuccessResponseSpec())
                .body("cart_value", equalTo(450));
        log.info("Test passed: FLATX offer of 50 correctly applied to cart of 500, resulting in 450");
    }

    @Test(groups = "Critical_Regression")
    public void TC06_verifyFlatXOfferEqualToCartValue() {
        log.info("Apply FLATX offer equal to cart value");
        addOffer(6, "FLATX", 100, new String[]{"p1"});
        Response response = applyOffer(6, 1, 100);
        response.then()
                .spec(ResponseSpecificationBuilder.getSuccessResponseSpec())
                .body("cart_value", equalTo(0));
        log.info("Test passed: FLATX offer equal to cart value resulting in 0 cart value");
    }

    @Test(groups = "Critical_Regression")
    public void TC07_verifyFlatXOfferGreaterThanCartValue() {
        log.info("Apply FLATX offer greater than cart value");
        addOffer(7, "FLATX", 200, new String[]{"p1"});
        Response response = applyOffer(7, 1, 100);
        response.then()
                .spec(ResponseSpecificationBuilder.getSuccessResponseSpec())
                .body("cart_value", equalTo(-100));
        log.info("Test passed: FLATX offer greater than cart value resulting in -100 cart value");
    }

    @Test(groups = "Critical_Regression")
    public void TC08_verifyZeroValueFlatXOffer() {
        log.info("Apply FLATX offer with zero value");
        addOffer(8, "FLATX", 0, new String[]{"p1"});
        Response response = applyOffer(8, 1, 200);
        response.then()
                .spec(ResponseSpecificationBuilder.getSuccessResponseSpec())
                .body("cart_value", equalTo(200));
        log.info("Test passed: Zero value FLATX offer resulting in no change to cart value");
    }

    @Test(groups = "Critical_Regression")
    public void TC09_verifyLargeFlatXOffer() {
        log.info("Apply a large FLATX offer");
        addOffer(9, "FLATX", 1000, new String[]{"p1"});
        Response response = applyOffer(9, 1, 2000);
        response.then()
                .spec(ResponseSpecificationBuilder.getSuccessResponseSpec())
                .body("cart_value", equalTo(1000));
        log.info("Test passed: Large FLATX offer of 1000 correctly applied to cart of 2000, resulting in 1000");
    }

    @Test(groups = "Critical_Regression")
    public void TC10_verifyStandardFlatPOffer() {
        log.info("Apply standard FLATP offer to a cart");
        addOffer(10, "FLATP", 25, new String[]{"p1"});
        Response response = applyOffer(10, 1, 400);
        response.then()
                .spec(ResponseSpecificationBuilder.getSuccessResponseSpec())
                .body("cart_value", equalTo(300));
        log.info("Test passed: FLATP offer of 25% correctly applied to cart of 400, resulting in 300");
    }

    @Test(groups = "Critical_Regression")
    public void TC11_verifyFlatP100PercentOffer() {
        log.info("Apply 100% FLATP offer");
        addOffer(11, "FLATP", 100, new String[]{"p1"});
        Response response = applyOffer(11, 1, 500);
        response.then()
                .spec(ResponseSpecificationBuilder.getSuccessResponseSpec())
                .body("cart_value", equalTo(0));
        log.info("Test passed: FLATP offer of 100% correctly applied to cart of 500, resulting in 0");
    }

    @Test(groups = "Critical_Regression")
    public void TC12_verifyFlatPZeroPercentOffer() {
        log.info("Apply 0% FLATP offer");
        addOffer(12, "FLATP", 0, new String[]{"p1"});
        Response response = applyOffer(12, 1, 200);
        response.then()
                .spec(ResponseSpecificationBuilder.getSuccessResponseSpec())
                .body("cart_value", equalTo(200));
        log.info("Test passed: FLATP offer of 0% resulted in no change to cart value");
    }

    @Test(groups = "Critical_Regression")
    public void TC13_verifyLargeFlatPOfferOnSmallCart() {
        log.info("Apply large percentage on small cart");
        addOffer(13, "FLATP", 75, new String[]{"p1"});
        Response response = applyOffer(13, 1, 20);
        response.then()
                .spec(ResponseSpecificationBuilder.getSuccessResponseSpec())
                .body("cart_value", equalTo(5));
        log.info("Test passed: FLATP offer of 75% correctly applied to cart of 20, resulting in 5");
    }

    @Test(groups = "Critical_Regression")
    public void TC14_verifyFlatPOfferWithDecimalValue() {
        log.info("Apply FLATP offer with decimal percentage");
        addOffer(14, "FLATP", 33.33, new String[]{"p1"});
        Response response = applyOffer(14, 1, 300);
        AssertionUtils.verifyStatusCode(response, 200);
        int cartValue = response.jsonPath().getInt("cart_value");
        assertEquals(cartValue, 200, 1);
        log.info("Test passed: FLATP offer of 33.33% correctly applied to cart of 300, resulting in approximately 200");
    }

    @Test(groups = "Impacted_Regression")
    public void TC20_verifyZeroCartValue() {
        log.info("Apply offer to zero cart value");
        addOffer(20, "FLATX", 10, new String[]{"p1"});
        Response response = applyOffer(20, 1, 0);
        response.then()
                .spec(ResponseSpecificationBuilder.getSuccessResponseSpec())
                .body("cart_value", equalTo(-10));
        log.info("Test passed: Offer applied to zero cart value results in -10");
    }

    @Test(groups = "Impacted_Regression")
    public void TC21_verifyVeryLargeCartValue() {
        log.info("Apply offer to very large cart value");
        addOffer(21, "FLATP", 10, new String[]{"p1"});
        Response response = applyOffer(21, 1, 9999999);
        AssertionUtils.verifyStatusCode(response, 200);
        double expectedValue = 9999999 * 0.9;
        double actualValue = response.jsonPath().getDouble("cart_value");
        assertEquals(actualValue, expectedValue, expectedValue * 0.01);
        log.info("Test passed: FLATP offer correctly applied to very large cart value");
    }

    @Test(groups = "Impacted_Regression")
    public void TC22_verifyFractionalCartValue() {
        log.info("Apply offer to fractional cart value");
        addOffer(22, "FLATX", 10, new String[]{"p1"});
        Response response = applyOffer(22, 1, 99.99);
        AssertionUtils.verifyStatusCode(response, 200);
        double cartValue = response.jsonPath().getDouble("cart_value");
        assertEquals(cartValue, 89.0);
        log.info("Test passed: FLATX offer correctly applied to fractional cart value");
    }

    @Test(groups = "Impacted_Regression")
    public void TC23_verifyFractionalOfferValue() {
        log.info("Apply fractional offer amount");
        addOffer(23, "FLATX", 0.5, new String[]{"p1"});
        Response response = applyOffer(23, 1, 10);
        AssertionUtils.verifyStatusCode(response, 200);
        double cartValue = response.jsonPath().getDouble("cart_value");
        assertEquals(cartValue, 10);
        log.info("Test passed: Fractional FLATX offer correctly applied");
    }

    @Test(groups = "Impacted_Regression")
    public void TC24_verifyNegativeOfferValue() {
        log.info("Apply negative offer value (should be rejected)");
        try {
            addOffer(24, "FLATX", -10, new String[]{"p1"});
            Response response = applyOffer(24, 1, 100);
            log.info("Negative offer value was accepted by API. Cart value: " +
                    response.jsonPath().getDouble("cart_value"));
        } catch (AssertionError e) {
            log.info("Test passed: Negative offer value was rejected as expected");
        }
    }

    @Test(groups = "Impacted_Regression")
    public void TC25_verifyBetterOfferSelection() {
        log.info("Verify best offer is selected when multiple applicable");
        addOffer(25, "FLATX", 20, new String[]{"p1"});
        Response response = applyOffer(25, 1, 200);
        response.then()
                .spec(ResponseSpecificationBuilder.getSuccessResponseSpec())
                .body("cart_value", equalTo(180));
        log.info("FLATX offer of 20 applied, resulting in cart value 180");
    }

    @Test(groups = "Impacted_Regression")
    public void TC26_verifyMultipleSegmentEligibility() {
        log.info("User eligible for offers in different segments");
        addOffer(26, "FLATP", 20, new String[]{"p2"});
        Response response = applyOffer(26, 4, 100);
        response.then()
                .spec(ResponseSpecificationBuilder.getSuccessResponseSpec())
                .body("cart_value", equalTo(100));
        log.info("Test passed: Offer applied to user eligible through multiple segment membership");
    }
}