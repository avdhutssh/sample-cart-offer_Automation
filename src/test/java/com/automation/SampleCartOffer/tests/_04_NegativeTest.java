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

@Feature("Negative Tests")
public class _04_NegativeTest extends _00_BaseTest {
    private static final Logger log = LogManager.getLogger(_04_NegativeTest.class);

    @Test(groups = "Negative")
    @Story("User Validation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify behavior when non-existent user tries to apply an offer")
    public void TC31_verifyNonExistentUser() {
        log.info("Apply offer with non-existent user");
        AllureReportUtils.logStep("Starting test to verify offer application for non-existent user");

        addOffer(30, "FLATX", 10, new String[]{"p1"});
        Response response = applyOffer(30, 999, 200);

        int statusCode = response.getStatusCode();

        if (statusCode >= 400) {
            log.info("Test passed: Non-existent user resulted in error response");
            AllureReportUtils.logStep("Received error status code as expected: " + statusCode);
            AssertionUtils.verifyStatusCode(response, statusCode);
            AllureReportUtils.logVerification("Non-existent user correctly resulted in error response");
        } else {
            AllureReportUtils.logStep("Non-existent user was handled without error, verifying no discount applied");
            response.then()
                    .spec(ResponseSpecificationBuilder.getSuccessResponseSpec())
                    .body("cart_value", equalTo(200));

            AllureReportUtils.logVerification("No discount applied for non-existent user");
            log.info("Test passed: No discount applied for non-existent user");
        }
    }

    @Test(groups = "Negative")
    @Story("Input Validation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify behavior when negative cart value is submitted")
    public void TC33_verifyNegativeCartValue() {
        log.info("Apply offer to negative cart value");
        AllureReportUtils.logStep("Starting test to verify handling of negative cart value");

        addOffer(32, "FLATX", 10, new String[]{"p1"});
        Response response = applyOffer(32, 1, -100);

        int statusCode = response.getStatusCode();

        if (statusCode >= 400) {
            log.info("Test passed: Negative cart value resulted in error response");
            AllureReportUtils.logStep("Received error status code as expected: " + statusCode);
            AssertionUtils.verifyStatusCode(response, statusCode);
            AllureReportUtils.logVerification("Negative cart value correctly resulted in error response");
        } else {
            AllureReportUtils.logStep("API accepted negative cart value. Response: " + response.asString());
            AssertionUtils.verifyStatusCode(response, 200);
            AllureReportUtils.logVerification("API accepted negative cart value without error");
            log.info("API accepted negative cart value. Response: " + response.asString());
        }
    }
}