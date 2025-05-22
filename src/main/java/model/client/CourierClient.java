package model.client;

import model.config.Configuration;
import model.Courier;
import model.CourierCredentials;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierClient {
    public Response create(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .body(courier)
                .post(Configuration.CREATE_COURIER);
    }

    public Response login(CourierCredentials credentials) {
        return given()
                .header("Content-type", "application/json")
                .body(credentials)
                .post(Configuration.LOGIN_COURIER);
    }

    public void delete(int courierId) {
        given()
                .delete(Configuration.CREATE_COURIER + "/" + courierId);
    }
}

