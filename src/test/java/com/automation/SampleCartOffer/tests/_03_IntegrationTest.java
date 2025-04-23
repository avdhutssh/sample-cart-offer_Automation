package com.automation.SampleCartOffer.tests;

import com.automation.SampleCartOffer.specs.ResponseSpecificationBuilder;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

public class _03_IntegrationTest extends _00_BaseTest {
    private static final Logger log = LogManager.getLogger(_03_IntegrationTest.class);

    @Test(groups = "Integration")
    public void TC15_verifyStandardP1Segment() {
        log.info("Apply offer to user in p1 segment");
        addOffer(15, "FLATX", 20, new String[]{"p1"});
        Response response = applyOffer(15, 1, 200);
        response.then()
                .spec(ResponseSpecificationBuilder.getSuccessResponseSpec())
                .body("cart_value", equalTo(180));
        log.info("Test passed: Offer correctly applied to user in p1 segment");
    }

    @Test(groups = "Integration")
    public void TC16_verifyStandardP2Segment() {
        log.info("Apply offer to user in p2 segment");
        addOffer(16, "FLATX", 20, new String[]{"p2"});
        Response response = applyOffer(16, 2, 200);
        response.then()
                .spec(ResponseSpecificationBuilder.getSuccessResponseSpec())
                .body("cart_value", equalTo(180));
        log.info("Test passed: Offer correctly applied to user in p2 segment");
    }

    @Test(groups = "Integration")
    public void TC17_verifyStandardP3Segment() {
        log.info("Apply offer to user in p3 segment");
        addOffer(17, "FLATX", 20, new String[]{"p3"});
        Response response = applyOffer(17, 3, 200);
        response.then()
                .spec(ResponseSpecificationBuilder.getSuccessResponseSpec())
                .body("cart_value", equalTo(180));
        log.info("Test passed: Offer correctly applied to user in p3 segment");
    }

    @Test(groups = "Integration")
    public void TC18_verifyMultiSegmentOffer() {
        log.info("Apply offer to a user in one of multiple segments");
        addOffer(18, "FLATX", 20, new String[]{"p1", "p2"});
        Response response = applyOffer(18, 1, 200);
        response.then()
                .spec(ResponseSpecificationBuilder.getSuccessResponseSpec())
                .body("cart_value", equalTo(180));
        log.info("Test passed: Offer correctly applied to user in one of multiple target segments");
    }

    @Test(groups = "Integration")
    public void TC19_verifyUserNotInOfferedSegment() {
        log.info("No offer for user not in target segment");
        addOffer(19, "FLATX", 20, new String[]{"p2"});
        Response response = applyOffer(19, 1, 200);
        response.then()
                .spec(ResponseSpecificationBuilder.getSuccessResponseSpec())
                .body("cart_value", equalTo(200));
        log.info("Test passed: No offer applied to user not in target segment");
    }
}