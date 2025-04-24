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

@Feature("Smoke Tests")
public class _01_smokeTest extends _00_BaseTest {
    private static final Logger log = LogManager.getLogger(_01_smokeTest.class);

    @Test(groups = "Smoke")
    @Story("Flat Amount Offers")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that FLATX offer is applied correctly for a user in the correct segment")
    public void TC01_verifyBasicFlatAmountOffer() {
        log.info("Verify FLATX offer is applied correctly for a user in the correct segment");
        AllureReportUtils.logStep("Starting test to verify FLATX offer application");

        addOffer(1, "FLATX", 10, new String[]{"p1"});
        Response response = applyOffer(1, 1, 200);

        AllureReportUtils.logStep("Verifying response");
        response.then()
                .spec(ResponseSpecificationBuilder.getSuccessResponseSpec())
                .body("cart_value", equalTo(190));

        AssertionUtils.verifyCartValue(response, 190);

        log.info("Test passed: FLATX offer of 10 correctly applied to cart of 200, resulting in 190");
        AllureReportUtils.logVerification("FLATX offer of 10 correctly applied to cart of 200, resulting in 190");
    }

    @Test(groups = "Smoke")
    @Story("Flat Percentage Offers")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that FLATP percentage offer is applied correctly for a user in the correct segment")
    public void TC02_verifyBasicFlatPercentageOffer() {
        log.info("Verify FLATP offer is applied correctly for a user in the correct segment");
        AllureReportUtils.logStep("Starting test to verify FLATP percentage offer application");

        addOffer(2, "FLATP", 10, new String[]{"p1"});
        Response response = applyOffer(2, 1, 200);

        AllureReportUtils.logStep("Verifying response");
        response.then()
                .spec(ResponseSpecificationBuilder.getSuccessResponseSpec())
                .body("cart_value", equalTo(180));

        AssertionUtils.verifyCartValue(response, 180);

        log.info("Test passed: FLATP offer of 10% correctly applied to cart of 200, resulting in 180");
        AllureReportUtils.logVerification("FLATP offer of 10% correctly applied to cart of 200, resulting in 180");
    }

    @Test(groups = "Smoke")
    @Story("Segmentation Rules")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify no offer is applied when user is not in the target segment")
    public void TC03_verifyNoMatchingSegmentNoOffer() {
        log.info("Verify no offer is applied when user is not in the segment");
        AllureReportUtils.logStep("Starting test to verify behavior when user is not in target segment");

        addOffer(3, "FLATX", 10, new String[]{"p2"});
        Response response = applyOffer(3, 1, 200);

        AllureReportUtils.logStep("Verifying response shows no discount applied");
        response.then()
                .spec(ResponseSpecificationBuilder.getSuccessResponseSpec())
                .body("cart_value", equalTo(200));

        AssertionUtils.verifyCartValue(response, 200);

        log.info("Test passed: No offer applied when user is not in the matching segment");
        AllureReportUtils.logVerification("No offer applied when user is not in the matching segment");
    }

    @Test(groups = "Smoke")
    @Story("Restaurant Configuration")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify no offer is applied when restaurant has no offers configured")
    public void TC04_verifyNoOfferForRestaurant() {
        log.info("Verify no offer is applied when restaurant has no offers");
        AllureReportUtils.logStep("Starting test to verify behavior when restaurant has no offers");

        Response response = applyOffer(4, 1, 200);

        AllureReportUtils.logStep("Verifying response shows no discount applied");
        response.then()
                .spec(ResponseSpecificationBuilder.getSuccessResponseSpec())
                .body("cart_value", equalTo(200));

        AssertionUtils.verifyCartValue(response, 200);

        log.info("Test passed: No offer applied when restaurant has no offers");
        AllureReportUtils.logVerification("No offer applied when restaurant has no offers configured");
    }
}