package com.automation.SampleCartOffer.tests;

import com.automation.SampleCartOffer.Utils.PropertyReader;
import com.automation.SampleCartOffer.Utils.TestListener;
import com.automation.SampleCartOffer.pojo.AddOffer;
import com.automation.SampleCartOffer.pojo.ApplyOffer;
import com.automation.SampleCartOffer.specs.RequestSpecificationBuilder;
import com.automation.SampleCartOffer.specs.ResponseSpecificationBuilder;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

import static io.restassured.RestAssured.given;


//@Listeners(TestListener.class)
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
    }

    protected void addOffer(int restaurantId, String offerType, double offerValue, String[] segments) {
        AddOffer offerRequest = new AddOffer(restaurantId, offerType, offerValue, segments);

        log.info("Adding offer: restaurant_id={}, offer_type={}, offer_value={}, segments={}",
                restaurantId, offerType, offerValue, String.join(",", segments));
        RequestSpecification requestSpec = RequestSpecificationBuilder.getBaseRequestSpec();

        given()
                .spec(requestSpec)
                .body(offerRequest)
                .when()
                .post(OFFER_ENDPOINT)
                .then()
                .spec(ResponseSpecificationBuilder.getOfferCreationSuccessSpec());
    }

    protected Response applyOffer(int restaurantId, int userId, double cartValue) {
        ApplyOffer cartRequest = new ApplyOffer(restaurantId, userId, cartValue);

        log.info("Applying offer: restaurant_id={}, user_id={}, cart_value={}",
                restaurantId, userId, cartValue);
        RequestSpecification requestSpec = RequestSpecificationBuilder.getBaseRequestSpec();

        return given()
                .spec(requestSpec)
                .body(cartRequest)
                .when()
                .post(APPLY_OFFER_ENDPOINT);
    }

    protected Response applyOfferWithLogging(int restaurantId, int userId, double cartValue) {
        ApplyOffer cartRequest = new ApplyOffer(restaurantId, userId, cartValue);

        log.info("Applying offer with detailed logging: restaurant_id={}, user_id={}, cart_value={}",
                restaurantId, userId, cartValue);
        RequestSpecification requestSpec = RequestSpecificationBuilder.getRequestSpecWithLogging();

        return given()
                .spec(requestSpec)
                .body(cartRequest)
                .when()
                .post(APPLY_OFFER_ENDPOINT);
    }
}