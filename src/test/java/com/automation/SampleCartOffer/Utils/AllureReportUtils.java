package com.automation.SampleCartOffer.Utils;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AllureReportUtils {
    private static final Logger log = LogManager.getLogger(AllureReportUtils.class);

    private AllureReportUtils() {
        // Private constructor to prevent instantiation
    }

    public static void logRequest(String endpoint, Object requestBody) {
        log.info("Logging request to Allure for endpoint: {}", endpoint);
        try {
            Allure.step("API Request - Endpoint: " + endpoint);
            if (requestBody != null) {
                attachRequestBody(requestBody.toString());
            }
        } catch (Exception e) {
            log.error("Error attaching request to Allure: {}", e.getMessage());
        }
    }

    public static void logResponse(Response response) {
        if (response == null) return;

        log.info("Logging response to Allure with status code: {}", response.getStatusCode());
        try {
            Allure.step("API Response - Status code: " + response.getStatusCode());
            attachResponseBody(response.getBody().asString());
        } catch (Exception e) {
            log.error("Error attaching response to Allure: {}", e.getMessage());
        }
    }

    public static void logStep(String stepDescription) {
        log.info("Step: {}", stepDescription);
        Allure.step(stepDescription);
    }

    public static void logVerification(String verification) {
        log.info("Verification: {}", verification);
        Allure.step("VERIFICATION: " + verification);
    }

    @Attachment(value = "Request Body", type = "application/json")
    private static String attachRequestBody(String requestBody) {
        return requestBody;
    }

    @Attachment(value = "Response Body", type = "application/json")
    private static String attachResponseBody(String responseBody) {
        return responseBody;
    }
}