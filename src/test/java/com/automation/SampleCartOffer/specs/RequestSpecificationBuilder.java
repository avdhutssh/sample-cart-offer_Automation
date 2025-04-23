package com.automation.SampleCartOffer.specs;


import com.automation.SampleCartOffer.Utils.PropertyReader;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RequestSpecificationBuilder {
    private static final Logger log = LogManager.getLogger(RequestSpecificationBuilder.class);
    private static final PropertyReader propertyReader = PropertyReader.getInstance();


    public static RequestSpecification getBaseRequestSpec() {
        log.info("Building base request specification");

        String baseUrl = propertyReader.getProperty("base.url");
        log.debug("Using base URL: {}", baseUrl);

        return new io.restassured.builder.RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();
    }

    public static RequestSpecification getRequestSpecWithLogging() {
        log.info("Building request specification with full logging");

        return new io.restassured.builder.RequestSpecBuilder()
                .setBaseUri(propertyReader.getProperty("base.url"))
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .log(io.restassured.filter.log.LogDetail.ALL)
                .build();
    }

}