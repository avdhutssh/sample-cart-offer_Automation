package com.automation.SampleCartOffer.tests;

import com.automation.SampleCartOffer.Utils.AllureReportUtils;
import com.automation.SampleCartOffer.Utils.AssertionUtils;
import com.automation.SampleCartOffer.specs.ResponseSpecificationBuilder;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

@Feature("Integration Tests")
public class _03_IntegrationTest extends _00_BaseTest {
    private static final Logger log = LogManager.getLogger(_03_IntegrationTest.class);

    @Test(groups = "Integration")
    @Story("Segment P1")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify offer application for users in P1 segment")
    public void TC15_verifyStandardP1Segment() {
        log.info("Apply offer to user in p1 segment");
        AllureReportUtils.logStep("Starting test to verify offer for P1 segment user");

        addOffer(15, "FLATX", 20, new String[]{"p1"});
        Response response = applyOffer(15, 1, 200);

        response.then()
                .spec(ResponseSpecificationBuilder.getSuccessResponseSpec())
                .body("cart_value", equalTo(180));

        AssertionUtils.verifyCartValue(response, 180);

        log.info("Test passed: Offer correctly applied to user in p1 segment");
        AllureReportUtils.logVerification("Offer correctly applied to user in p1 segment");
    }

    @Test(groups = "Integration")
    @Story("Segment P2")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify offer application for users in P2 segment")
    public void TC16_verifyStandardP2Segment() {
        log.info("Apply offer to user in p2 segment");
        AllureReportUtils.logStep("Starting test to verify offer for P2 segment user");

        addOffer(16, "FLATX", 20, new String[]{"p2"});
        Response response = applyOffer(16, 2, 200);

        response.then()
                .spec(ResponseSpecificationBuilder.getSuccessResponseSpec())
                .body("cart_value", equalTo(180));

        AssertionUtils.verifyCartValue(response, 180);

        log.info("Test passed: Offer correctly applied to user in p2 segment");
        AllureReportUtils.logVerification("Offer correctly applied to user in p2 segment");
    }

    @Test(groups = "Integration")
    @Story("Segment P3")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify offer application for users in P3 segment")
    public void TC17_verifyStandardP3Segment() {
        log.info("Apply offer to user in p3 segment");
        AllureReportUtils.logStep("Starting test to verify offer for P3 segment user");

        addOffer(17, "FLATX", 20, new String[]{"p3"});
        Response response = applyOffer(17, 3, 200);

        response.then()
                .spec(ResponseSpecificationBuilder.getSuccessResponseSpec())
                .body("cart_value", equalTo(180));

        AssertionUtils.verifyCartValue(response, 180);

        log.info("Test passed: Offer correctly applied to user in p3 segment");
        AllureReportUtils.logVerification("Offer correctly applied to user in p3 segment");
    }

    @Test(groups = "Integration")
    @Story("Multiple Segments")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify offer application for users when offer targets multiple segments")
    public void TC18_verifyMultiSegmentOffer() {
        log.info("Apply offer to a user in one of multiple segments");
        AllureReportUtils.logStep("Starting test to verify multi-segment offer");

        addOffer(18, "FLATX", 20, new String[]{"p1", "p2"});
        Response response = applyOffer(18, 1, 200);

        response.then()
                .spec(ResponseSpecificationBuilder.getSuccessResponseSpec())
                .body("cart_value", equalTo(180));

        AssertionUtils.verifyCartValue(response, 180);

        log.info("Test passed: Offer correctly applied to user in one of multiple target segments");
        AllureReportUtils.logVerification("Offer correctly applied to user in one of multiple target segments");
    }

    @Test(groups = "Integration")
    @Story("Segment Exclusion")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify no offer is applied when user is not in any of the targeted segments")
    public void TC19_verifyUserNotInOfferedSegment() {
        log.info("No offer for user not in target segment");
        AllureReportUtils.logStep("Starting test to verify no offer applied for non-matching segment");

        addOffer(19, "FLATX", 20, new String[]{"p2"});
        Response response = applyOffer(19, 1, 200);

        response.then()
                .spec(ResponseSpecificationBuilder.getSuccessResponseSpec())
                .body("cart_value", equalTo(200));

        AssertionUtils.verifyCartValue(response, 200);

        log.info("Test passed: No offer applied to user not in target segment");
        AllureReportUtils.logVerification("No offer applied to user not in target segment");
    }
}