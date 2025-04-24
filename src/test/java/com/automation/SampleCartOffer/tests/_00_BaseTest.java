package com.automation.SampleCartOffer.tests;

import com.automation.SampleCartOffer.Utils.AllureReportUtils;
import com.automation.SampleCartOffer.Utils.AllureRestAssuredFilter;
import com.automation.SampleCartOffer.Utils.PropertyReader;
import com.automation.SampleCartOffer.pojo.AddOffer;
import com.automation.SampleCartOffer.pojo.ApplyOffer;
import com.automation.SampleCartOffer.specs.RequestSpecificationBuilder;
import com.automation.SampleCartOffer.specs.ResponseSpecificationBuilder;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.given;

@Epic("Cart Offer API Tests")
public class _00_BaseTest {
    protected static final Logger log = LogManager.getLogger(_00_BaseTest.class);
    protected static final PropertyReader propertyReader = PropertyReader.getInstance();

    protected String OFFER_ENDPOINT;
    protected String APPLY_OFFER_ENDPOINT;

    @BeforeClass
    public void setup() {
        OFFER_ENDPOINT = propertyReader.getProperty("offer.endpoint", "/api/v1/offer");
        APPLY_OFFER_ENDPOINT = propertyReader.getProperty("apply.offer.endpoint", "/api/v1/cart/apply_offer");
        RestAssured.baseURI = propertyReader.getProperty("base.url", "http://localhost:9001");
        log.info("Test setup complete. Base URI set to: {}", RestAssured.baseURI);

        AllureReportUtils.logStep("Test environment initialized with base URL: " + RestAssured.baseURI);
    }

    @Step("Add offer with restaurantId={0}, offerType={1}, offerValue={2}")
    protected void addOffer(int restaurantId, String offerType, double offerValue, String[] segments) {
        AddOffer offerRequest = new AddOffer(restaurantId, offerType, offerValue, segments);

        log.info("Adding offer: restaurant_id={}, offer_type={}, offer_value={}, segments={}",
                restaurantId, offerType, offerValue, String.join(",", segments));
        AllureReportUtils.logStep("Adding offer with restaurantId=" + restaurantId +
                ", offerType=" + offerType +
                ", offerValue=" + offerValue +
                ", segments=" + String.join(",", segments));
        AllureReportUtils.logRequest(OFFER_ENDPOINT, offerRequest);

        RequestSpecification requestSpec = RequestSpecificationBuilder.getBaseRequestSpec();

        Response response = given()
                .spec(requestSpec)
                .filter(AllureRestAssuredFilter.getInstance())
                .body(offerRequest)
                .when()
                .post(OFFER_ENDPOINT);

        AllureReportUtils.logResponse(response);
        response.then()
                .spec(ResponseSpecificationBuilder.getOfferCreationSuccessSpec());

        AllureReportUtils.logVerification("Offer successfully created with response code: " + response.getStatusCode());
    }

    @Step("Apply offer with restaurantId={0}, userId={1}, cartValue={2}")
    protected Response applyOffer(int restaurantId, int userId, double cartValue) {
        ApplyOffer cartRequest = new ApplyOffer(restaurantId, userId, cartValue);

        log.info("Applying offer: restaurant_id={}, user_id={}, cart_value={}",
                restaurantId, userId, cartValue);

        AllureReportUtils.logStep("Applying offer with restaurantId=" + restaurantId +
                ", userId=" + userId +
                ", cartValue=" + cartValue);
        AllureReportUtils.logRequest(APPLY_OFFER_ENDPOINT, cartRequest);

        RequestSpecification requestSpec = RequestSpecificationBuilder.getBaseRequestSpec();

        Response response = given()
                .spec(requestSpec)
                .filter(AllureRestAssuredFilter.getInstance())
                .body(cartRequest)
                .when()
                .post(APPLY_OFFER_ENDPOINT);

        AllureReportUtils.logResponse(response);
        return response;
    }
}