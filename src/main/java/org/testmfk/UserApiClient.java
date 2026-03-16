package org.testmfk;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

public class UserApiClient {
    public static final String API_V1_PATH = "/api/v1/users";
    public static final String API_V1_PATH_WITH_ID = API_V1_PATH + "/{id}";
    private static UserApiClient instance;
    private final String baseUrl;
//    private int timeout = 5000;

    private UserApiClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public static UserApiClient getInstance(String baseUrl) {
        if (instance == null) {
            instance = new UserApiClient(baseUrl);
        }
        return instance;
    }

    public UserResponse createUser(CreateUserRequest payload) {
        return given()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post(API_V1_PATH)
                .then()
                .statusCode(200)
                .extract()
                .as(UserResponse.class);
    }

    public UserResponse getUserById(String userId) {
        return given()
                .baseUri(baseUrl)
                .pathParam("id", userId)
                .when()
                .get(API_V1_PATH_WITH_ID)
                .then()
                .statusCode(200)
                .extract()
                .as(UserResponse.class);
    }

    public void deleteUser(String userId) {
        given()
                .baseUri(baseUrl)
                .pathParam("id", userId)
                .when()
                .delete(API_V1_PATH_WITH_ID)
                .then()
                .statusCode(200);
    }
}
