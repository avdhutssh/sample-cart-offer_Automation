package com.automation.SampleCartOffer.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.Matchers;

public class ResponseSpecificationBuilder {
    private static final Logger log = LogManager.getLogger(ResponseSpecBuilder.class);

    public static ResponseSpecification getSuccessResponseSpec() {
        log.info("Building success response specification");

        return new io.restassured.builder.ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(io.restassured.http.ContentType.JSON)
                .build();
    }

    public static ResponseSpecification getOfferCreationSuccessSpec() {
        log.info("Building offer creation success response specification");

        return new io.restassured.builder.ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(io.restassured.http.ContentType.JSON)
                .expectBody("response_msg", Matchers.equalTo("success"))
                .build();
    }

    public static ResponseSpecification getResponseSpecWithStatus(int statusCode) {
        log.info("Building response specification with status code: {}", statusCode);

        return new io.restassured.builder.ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .expectContentType(io.restassured.http.ContentType.JSON)
                .build();
    }

    public static ResponseSpecification getResponseSpecWithLogging() {
        log.info("Building response specification with logging");

        return new io.restassured.builder.ResponseSpecBuilder()
                .log(io.restassured.filter.log.LogDetail.ALL)
                .build();
    }
}