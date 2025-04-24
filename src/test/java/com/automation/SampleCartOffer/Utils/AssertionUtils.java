package com.automation.SampleCartOffer.Utils;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

public class AssertionUtils {
    private static final Logger log = LogManager.getLogger(AssertionUtils.class);

    private AssertionUtils() {
    }

    @Step("Verify status code: expected={1}")
    public static void verifyStatusCode(Response response, int statusCode) {
        log.info("Verifying status code: {}", statusCode);
        int actualStatusCode = response.getStatusCode();

        AllureReportUtils.logStep("Verifying status code - Expected: " + statusCode + ", Actual: " + actualStatusCode);

        Assert.assertEquals(actualStatusCode, statusCode,
                "Expected status code " + statusCode + " but got " + actualStatusCode);

        AllureReportUtils.logVerification("Status code verification passed: " + statusCode);
        log.info("Status code verification passed: {}", statusCode);
    }

    @Step("Verify response contains field: {1}")
    public static void verifyResponseContainsField(Response response, String path) {
        log.info("Verifying response contains field: {}", path);
        AllureReportUtils.logStep("Verifying response contains field: " + path);

        Assert.assertNotNull(response.jsonPath().get(path),
                "Response does not contain field: " + path);

        AllureReportUtils.logVerification("Response field verification passed: " + path);
        log.info("Response field verification passed: {}", path);
    }

    @Step("Verify string value: {1}={2}")
    public static void verifyStringValue(Response response, String path, String expectedValue) {
        log.info("Verifying string value at {}: expected '{}'", path, expectedValue);
        AllureReportUtils.logStep("Verifying string value at '" + path + "' - Expected: '" + expectedValue + "'");

        String actualValue = response.jsonPath().getString(path);
        Assert.assertEquals(actualValue, expectedValue,
                "Expected '" + expectedValue + "' at path '" + path + "' but got '" + actualValue + "'");

        AllureReportUtils.logVerification("String value verification passed for " + path);
        log.info("String value verification passed for {}", path);
    }

    @Step("Verify cart value: expected={1}")
    public static void verifyCartValue(Response response, double expectedValue) {
        log.info("Verifying cart value: expected {}", expectedValue);
        AllureReportUtils.logStep("Verifying cart value - Expected: " + expectedValue);

        verifyDoubleValue(response, "cart_value", expectedValue);
    }

    public static void verifyDoubleValue(Response response, String path, double expectedValue) {
        log.info("Verifying double value at {}: expected {}", path, expectedValue);

        double actualValue = response.jsonPath().getDouble(path);
        Assert.assertEquals(actualValue, expectedValue,
                "Expected " + expectedValue + " at path '" + path + "' but got " + actualValue);
        log.info("Double value verification passed for {}", path);
    }

    public static void verifyDoubleValue(Response response, String path, double expectedValue, double delta) {
        log.info("Verifying double value at {}: expected {} with delta {}", path, expectedValue, delta);

        double actualValue = response.jsonPath().getDouble(path);
        Assert.assertEquals(actualValue, expectedValue, delta,
                "Expected " + expectedValue + " (±" + delta + ") at path '" + path + "' but got " + actualValue);
        log.info("Double value verification with delta passed for {}", path);
    }

}