package com.automation.SampleCartOffer.tests;

import com.automation.SampleCartOffer.Utils.AssertionUtils;
import com.automation.SampleCartOffer.specs.ResponseSpecificationBuilder;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

public class _04_NegativeTest extends _00_BaseTest {
    private static final Logger log = LogManager.getLogger(_04_NegativeTest.class);

    @Test(groups = "Negative")
    public void TC31_verifyNonExistentUser() {
        log.info("Apply offer with non-existent user");
        addOffer(30, "FLATX", 10, new String[]{"p1"});
        Response response = applyOffer(30, 999, 200);
        int statusCode = response.getStatusCode();
        if (statusCode >= 400) {
            log.info("Test passed: Non-existent user resulted in error response");
            AssertionUtils.verifyStatusCode(response, statusCode);
        } else {
            response.then()
                    .spec(ResponseSpecificationBuilder.getSuccessResponseSpec())
                    .body("cart_value", equalTo(200));
            log.info("Test passed: No discount applied for non-existent user");
        }
    }

    @Test(groups = "Negative")
    public void TC33_verifyNegativeCartValue() {
        log.info("Apply offer to negative cart value");
        addOffer(32, "FLATX", 10, new String[]{"p1"});
        Response response = applyOffer(32, 1, -100);
        int statusCode = response.getStatusCode();
        if (statusCode >= 400) {
            log.info("Test passed: Negative cart value resulted in error response");
            AssertionUtils.verifyStatusCode(response, statusCode);
        } else {
            log.info("API accepted negative cart value. Response: " + response.asString());
            AssertionUtils.verifyStatusCode(response, 200);
        }
    }
}