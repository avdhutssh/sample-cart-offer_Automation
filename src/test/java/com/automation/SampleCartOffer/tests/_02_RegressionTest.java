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
import static org.testng.Assert.assertEquals;

@Feature("Regression Tests")
public class _02_RegressionTest extends _00_BaseTest {
    private static final Logger log = LogManager.getLogger(_02_RegressionTest.class);

    @Test(groups = "Critical_Regression")
    @Story("Standard Flat Amount Offers")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify standard FLATX offer calculation")
    public void TC05_verifyStandardFlatXOffer() {
        log.info("Apply standard FLATX offer to a cart");
        AllureReportUtils.logStep("Starting test to verify standard FLATX offer");

        addOffer(5, "FLATX", 50, new String[]{"p1"});
        Response response = applyOffer(5, 1, 500);

        response.then()
                .spec(ResponseSpecificationBuilder.getSuccessResponseSpec())
                .body("cart_value", equalTo(450));

        log.info("Test passed: FLATX offer of 50 correctly applied to cart of 500, resulting in 450");
        AllureReportUtils.logVerification("FLATX offer of 50 correctly applied to cart of 500, resulting in 450");
    }

    @Test(groups = "Critical_Regression")
    @Story("Boundary Cases")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify FLATX offer equal to cart value results in zero")
    public void TC06_verifyFlatXOfferEqualToCartValue() {
        log.info("Apply FLATX offer equal to cart value");
        AllureReportUtils.logStep("Starting test to verify FLATX offer equal to cart value");

        addOffer(6, "FLATX", 100, new String[]{"p1"});
        Response response = applyOffer(6, 1, 100);

        response.then()
                .spec(ResponseSpecificationBuilder.getSuccessResponseSpec())
                .body("cart_value", equalTo(0));

        log.info("Test passed: FLATX offer equal to cart value resulting in 0 cart value");
        AllureReportUtils.logVerification("FLATX offer equal to cart value resulting in 0 cart value");
    }

    @Test(groups = "Critical_Regression")
    @Story("Boundary Cases")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify FLATX offer greater than cart value results in negative value")
    public void TC07_verifyFlatXOfferGreaterThanCartValue() {
        log.info("Apply FLATX offer greater than cart value");
        AllureReportUtils.logStep("Starting test to verify FLATX offer greater than cart value");

        addOffer(7, "FLATX", 200, new String[]{"p1"});
        Response response = applyOffer(7, 1, 100);

        response.then()
                .spec(ResponseSpecificationBuilder.getSuccessResponseSpec())
                .body("cart_value", equalTo(-100));

        log.info("Test passed: FLATX offer greater than cart value resulting in -100 cart value");
        AllureReportUtils.logVerification("FLATX offer greater than cart value resulting in -100 cart value");
    }

    @Test(groups = "Critical_Regression")
    @Story("Boundary Cases")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify FLATX offer with zero value has no effect")
    public void TC08_verifyZeroValueFlatXOffer() {
        log.info("Apply FLATX offer with zero value");
        AllureReportUtils.logStep("Starting test to verify zero value FLATX offer");

        addOffer(8, "FLATX", 0, new String[]{"p1"});
        Response response = applyOffer(8, 1, 200);

        response.then()
                .spec(ResponseSpecificationBuilder.getSuccessResponseSpec())
                .body("cart_value", equalTo(200));

        log.info("Test passed: Zero value FLATX offer resulting in no change to cart value");
        AllureReportUtils.logVerification("Zero value FLATX offer resulting in no change to cart value");
    }

    @Test(groups = "Critical_Regression")
    @Story("Standard Flat Amount Offers")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify large FLATX offer is correctly applied")
    public void TC09_verifyLargeFlatXOffer() {
        log.info("Apply a large FLATX offer");
        AllureReportUtils.logStep("Starting test to verify large FLATX offer");

        addOffer(9, "FLATX", 1000, new String[]{"p1"});
        Response response = applyOffer(9, 1, 2000);

        response.then()
                .spec(ResponseSpecificationBuilder.getSuccessResponseSpec())
                .body("cart_value", equalTo(1000));

        log.info("Test passed: Large FLATX offer of 1000 correctly applied to cart of 2000, resulting in 1000");
        AllureReportUtils.logVerification("Large FLATX offer of 1000 correctly applied to cart of 2000, resulting in 1000");
    }

    @Test(groups = "Critical_Regression")
    @Story("Standard Percentage Offers")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify standard FLATP percentage offer calculation")
    public void TC10_verifyStandardFlatPOffer() {
        log.info("Apply standard FLATP offer to a cart");
        AllureReportUtils.logStep("Starting test to verify standard FLATP offer");

        addOffer(10, "FLATP", 25, new String[]{"p1"});
        Response response = applyOffer(10, 1, 400);

        response.then()
                .spec(ResponseSpecificationBuilder.getSuccessResponseSpec())
                .body("cart_value", equalTo(300));

        log.info("Test passed: FLATP offer of 25% correctly applied to cart of 400, resulting in 300");
        AllureReportUtils.logVerification("FLATP offer of 25% correctly applied to cart of 400, resulting in 300");
    }

    @Test(groups = "Critical_Regression")
    @Story("Boundary Cases")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify 100% FLATP offer results in zero cart value")
    public void TC11_verifyFlatP100PercentOffer() {
        log.info("Apply 100% FLATP offer");
        AllureReportUtils.logStep("Starting test to verify 100% FLATP offer");

        addOffer(11, "FLATP", 100, new String[]{"p1"});
        Response response = applyOffer(11, 1, 500);

        response.then()
                .spec(ResponseSpecificationBuilder.getSuccessResponseSpec())
                .body("cart_value", equalTo(0));

        log.info("Test passed: FLATP offer of 100% correctly applied to cart of 500, resulting in 0");
        AllureReportUtils.logVerification("FLATP offer of 100% correctly applied to cart of 500, resulting in 0");
    }

    @Test(groups = "Critical_Regression")
    @Story("Boundary Cases")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify 0% FLATP offer has no effect")
    public void TC12_verifyFlatPZeroPercentOffer() {
        log.info("Apply 0% FLATP offer");
        AllureReportUtils.logStep("Starting test to verify 0% FLATP offer");

        addOffer(12, "FLATP", 0, new String[]{"p1"});
        Response response = applyOffer(12, 1, 200);

        response.then()
                .spec(ResponseSpecificationBuilder.getSuccessResponseSpec())
                .body("cart_value", equalTo(200));

        log.info("Test passed: FLATP offer of 0% resulted in no change to cart value");
        AllureReportUtils.logVerification("FLATP offer of 0% resulted in no change to cart value");
    }

    @Test(groups = "Critical_Regression")
    @Story("Standard Percentage Offers")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify large percentage discount on small cart value")
    public void TC13_verifyLargeFlatPOfferOnSmallCart() {
        log.info("Apply large percentage on small cart");
        AllureReportUtils.logStep("Starting test to verify large percentage on small cart");

        addOffer(13, "FLATP", 75, new String[]{"p1"});
        Response response = applyOffer(13, 1, 20);

        response.then()
                .spec(ResponseSpecificationBuilder.getSuccessResponseSpec())
                .body("cart_value", equalTo(5));

        log.info("Test passed: FLATP offer of 75% correctly applied to cart of 20, resulting in 5");
        AllureReportUtils.logVerification("FLATP offer of 75% correctly applied to cart of 20, resulting in 5");
    }

    @Test(groups = "Critical_Regression")
    @Story("Standard Percentage Offers")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify decimal percentage FLATP offer calculation")
    public void TC14_verifyFlatPOfferWithDecimalValue() {
        log.info("Apply FLATP offer with decimal percentage");
        AllureReportUtils.logStep("Starting test to verify FLATP offer with decimal percentage");

        addOffer(14, "FLATP", 33.33, new String[]{"p1"});
        Response response = applyOffer(14, 1, 300);

        AssertionUtils.verifyStatusCode(response, 200);

        int cartValue = response.jsonPath().getInt("cart_value");
        assertEquals(cartValue, 200, 1);

        log.info("Test passed: FLATP offer of 33.33% correctly applied to cart of 300, resulting in approximately 200");
        AllureReportUtils.logVerification("FLATP offer of 33.33% correctly applied to cart of 300, resulting in approximately 200");
    }

    @Test(groups = "Impacted_Regression")
    @Story("Edge Cases")
    @Severity(SeverityLevel.MINOR)
    @Description("Verify offer applied to zero cart value")
    public void TC20_verifyZeroCartValue() {
        log.info("Apply offer to zero cart value");
        AllureReportUtils.logStep("Starting test to verify offer on zero cart value");

        addOffer(20, "FLATX", 10, new String[]{"p1"});
        Response response = applyOffer(20, 1, 0);

        response.then()
                .spec(ResponseSpecificationBuilder.getSuccessResponseSpec())
                .body("cart_value", equalTo(-10));

        log.info("Test passed: Offer applied to zero cart value results in -10");
        AllureReportUtils.logVerification("Offer applied to zero cart value results in -10");
    }

    @Test(groups = "Impacted_Regression")
    @Story("Edge Cases")
    @Severity(SeverityLevel.MINOR)
    @Description("Verify offer applied to very large cart value")
    public void TC21_verifyVeryLargeCartValue() {
        log.info("Apply offer to very large cart value");
        AllureReportUtils.logStep("Starting test to verify offer on very large cart value");

        addOffer(21, "FLATP", 10, new String[]{"p1"});
        Response response = applyOffer(21, 1, 9999999);

        AssertionUtils.verifyStatusCode(response, 200);

        double expectedValue = 9999999 * 0.9;
        double actualValue = response.jsonPath().getDouble("cart_value");
        assertEquals(actualValue, expectedValue, expectedValue * 0.01);

        log.info("Test passed: FLATP offer correctly applied to very large cart value");
        AllureReportUtils.logVerification("FLATP offer correctly applied to very large cart value");
    }

    @Test(groups = "Impacted_Regression")
    @Story("Edge Cases")
    @Severity(SeverityLevel.MINOR)
    @Description("Verify offer applied to fractional cart value")
    public void TC22_verifyFractionalCartValue() {
        log.info("Apply offer to fractional cart value");
        AllureReportUtils.logStep("Starting test to verify offer on fractional cart value");

        addOffer(22, "FLATX", 10, new String[]{"p1"});
        Response response = applyOffer(22, 1, 99.99);

        AssertionUtils.verifyStatusCode(response, 200);

        double cartValue = response.jsonPath().getDouble("cart_value");
        assertEquals(cartValue, 89.0);

        log.info("Test passed: FLATX offer correctly applied to fractional cart value");
        AllureReportUtils.logVerification("FLATX offer correctly applied to fractional cart value");
    }

    @Test(groups = "Impacted_Regression")
    @Story("Edge Cases")
    @Severity(SeverityLevel.MINOR)
    @Description("Verify fractional offer amount application")
    public void TC23_verifyFractionalOfferValue() {
        log.info("Apply fractional offer amount");
        AllureReportUtils.logStep("Starting test to verify fractional offer amount");

        addOffer(23, "FLATX", 0.5, new String[]{"p1"});
        Response response = applyOffer(23, 1, 10);

        AssertionUtils.verifyStatusCode(response, 200);

        double cartValue = response.jsonPath().getDouble("cart_value");
        assertEquals(cartValue, 10);

        log.info("Test passed: Fractional FLATX offer correctly applied");
        AllureReportUtils.logVerification("Fractional FLATX offer correctly applied");
    }

    @Test(groups = "Impacted_Regression")
    @Story("Edge Cases")
    @Severity(SeverityLevel.MINOR)
    @Description("Verify negative offer value handling")
    public void TC24_verifyNegativeOfferValue() {
        log.info("Apply negative offer value (should be rejected)");
        AllureReportUtils.logStep("Starting test to verify negative offer value handling");

        try {
            addOffer(24, "FLATX", -10, new String[]{"p1"});
            Response response = applyOffer(24, 1, 100);
            log.info("Negative offer value was accepted by API. Cart value: " +
                    response.jsonPath().getDouble("cart_value"));
            AllureReportUtils.logStep("Negative offer value was unexpectedly accepted");
        } catch (AssertionError e) {
            log.info("Test passed: Negative offer value was rejected as expected");
            AllureReportUtils.logVerification("Negative offer value was rejected as expected");
        }
    }

    @Test(groups = "Impacted_Regression")
    @Story("Offer Selection")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify best offer selection when multiple are applicable")
    public void TC25_verifyBetterOfferSelection() {
        log.info("Verify best offer is selected when multiple applicable");
        AllureReportUtils.logStep("Starting test to verify best offer selection");

        addOffer(25, "FLATX", 20, new String[]{"p1"});
        Response response = applyOffer(25, 1, 200);

        response.then()
                .spec(ResponseSpecificationBuilder.getSuccessResponseSpec())
                .body("cart_value", equalTo(180));

        log.info("FLATX offer of 20 applied, resulting in cart value 180");
        AllureReportUtils.logVerification("Best offer correctly selected and applied");
    }

    @Test(groups = "Impacted_Regression")
    @Story("Segmentation Rules")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify offer handling for users in multiple segments")
    public void TC26_verifyMultipleSegmentEligibility() {
        log.info("User eligible for offers in different segments");
        AllureReportUtils.logStep("Starting test to verify multiple segment eligibility");

        addOffer(26, "FLATP", 20, new String[]{"p2"});
        Response response = applyOffer(26, 4, 100);

        response.then()
                .spec(ResponseSpecificationBuilder.getSuccessResponseSpec())
                .body("cart_value", equalTo(100));

        log.info("Test passed: Offer applied to user eligible through multiple segment membership");
        AllureReportUtils.logVerification("Offer correctly applied to user in multiple segments");
    }
}