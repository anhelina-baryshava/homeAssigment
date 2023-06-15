package org.core;

import io.restassured.http.ContentType;
import io.restassured.response.Response;


import static io.restassured.RestAssured.given;
import static org.core.Endpoints.BASE_URL;

public class BaseRequest {

    public static Response postRequest(String endpoint, Object requestBody) {
        return given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .basePath(endpoint)
                .body(requestBody)
                .when().post()
                .then()
                .statusCode(200)
                .log().all().extract().response();
    }
}
