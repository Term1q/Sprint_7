package model.client;



import model.config.Configuration;
import model.Order;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderClient {
    public Response create(Order order) {
        return given()
                .header("Content-type", "application/json")
                .body(order)
                .post(Configuration.ORDERS);
    }

    public Response getOrders() {
        return given()
                .get(Configuration.ORDERS);
    }
}