package com.automation.SampleCartOffer.Utils;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

public class AssertionUtils {
    private static final Logger log = LogManager.getLogger(AssertionUtils.class);

    private AssertionUtils() {
        // Private constructor to prevent instantiation
    }

    /**
     * Verify response status code
     *
     * @param response   Response object
     * @param statusCode Expected status code
     */
    public static void verifyStatusCode(Response response, int statusCode) {
        log.info("Verifying status code: {}", statusCode);
        int actualStatusCode = response.getStatusCode();

        Assert.assertEquals(actualStatusCode, statusCode,
                "Expected status code " + statusCode + " but got " + actualStatusCode);
        log.info("Status code verification passed: {}", statusCode);
    }

    /**
     * Verify response contains a specific field
     *
     * @param response Response object
     * @param path     JSON path to the field
     */
    public static void verifyResponseContainsField(Response response, String path) {
        log.info("Verifying response contains field: {}", path);

        Assert.assertNotNull(response.jsonPath().get(path),
                "Response does not contain field: " + path);
        log.info("Response field verification passed: {}", path);
    }

    /**
     * Verify string value in response
     *
     * @param response      Response object
     * @param path          JSON path to the field
     * @param expectedValue Expected string value
     */
    public static void verifyStringValue(Response response, String path, String expectedValue) {
        log.info("Verifying string value at {}: expected '{}'", path, expectedValue);

        String actualValue = response.jsonPath().getString(path);
        Assert.assertEquals(actualValue, expectedValue,
                "Expected '" + expectedValue + "' at path '" + path + "' but got '" + actualValue + "'");
        log.info("String value verification passed for {}", path);
    }

    /**
     * Verify integer value in response
     *
     * @param response      Response object
     * @param path          JSON path to the field
     * @param expectedValue Expected integer value
     */
    public static void verifyIntValue(Response response, String path, int expectedValue) {
        log.info("Verifying int value at {}: expected {}", path, expectedValue);

        int actualValue = response.jsonPath().getInt(path);
        Assert.assertEquals(actualValue, expectedValue,
                "Expected " + expectedValue + " at path '" + path + "' but got " + actualValue);
        log.info("Integer value verification passed for {}", path);
    }

    /**
     * Verify double value in response
     *
     * @param response      Response object
     * @param path          JSON path to the field
     * @param expectedValue Expected double value
     */
    public static void verifyDoubleValue(Response response, String path, double expectedValue) {
        log.info("Verifying double value at {}: expected {}", path, expectedValue);

        double actualValue = response.jsonPath().getDouble(path);
        Assert.assertEquals(actualValue, expectedValue,
                "Expected " + expectedValue + " at path '" + path + "' but got " + actualValue);
        log.info("Double value verification passed for {}", path);
    }

    /**
     * Verify double value in response with delta
     *
     * @param response      Response object
     * @param path          JSON path to the field
     * @param expectedValue Expected double value
     * @param delta         Acceptable delta
     */
    public static void verifyDoubleValue(Response response, String path, double expectedValue, double delta) {
        log.info("Verifying double value at {}: expected {} with delta {}", path, expectedValue, delta);

        double actualValue = response.jsonPath().getDouble(path);
        Assert.assertEquals(actualValue, expectedValue, delta,
                "Expected " + expectedValue + " (±" + delta + ") at path '" + path + "' but got " + actualValue);
        log.info("Double value verification with delta passed for {}", path);
    }

    /**
     * Verify cart value in response
     *
     * @param response      Response object
     * @param expectedValue Expected cart value
     */
    public static void verifyCartValue(Response response, double expectedValue) {
        log.info("Verifying cart value: expected {}", expectedValue);
        verifyDoubleValue(response, "cart_value", expectedValue);
    }

    /**
     * Verify cart value in response with delta
     *
     * @param response      Response object
     * @param expectedValue Expected cart value
     * @param delta         Acceptable delta
     */
    public static void verifyCartValue(Response response, double expectedValue, double delta) {
        log.info("Verifying cart value: expected {} with delta {}", expectedValue, delta);
        verifyDoubleValue(response, "cart_value", expectedValue, delta);
    }

    /**
     * Verify response message
     *
     * @param response Response object
     * @param expectedMessage Expected message
     */
    public static void verifyResponseMessage(Response response, String expectedMessage) {
        log.info("Verifying response message: expected '{}'", expectedMessage);
        verifyStringValue(response, "response_msg", expectedMessage);
    }
}