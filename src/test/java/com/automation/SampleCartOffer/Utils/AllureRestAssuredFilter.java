package com.automation.SampleCartOffer.Utils;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.filter.Filter;

public class AllureRestAssuredFilter {

    private static final Filter FILTER = new AllureRestAssured();

    private AllureRestAssuredFilter() {
    }

    public static Filter getInstance() {
        return FILTER;
    }
}