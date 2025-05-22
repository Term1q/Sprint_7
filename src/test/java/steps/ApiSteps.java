package steps;

import model.client.CourierClient;
import model.Courier;
import model.CourierCredentials;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class ApiSteps {
    @Step("Создание курьера")
    public static Response createCourier(CourierClient client, Courier courier) {
        return client.create(courier);
    }

    @Step("Логин курьера")
    public static Response loginCourier(CourierClient client, CourierCredentials credentials) {
        return client.login(credentials);
    }

    @Step("Удаление тестового курьера")
    public static void deleteTestCourier(CourierClient client, Courier courier) {
        Response loginResponse = loginCourier(client, CourierCredentials.builder()
                .login(courier.getLogin())
                .password(courier.getPassword())
                .build());

        if (loginResponse.statusCode() == 200) {
            int courierId = loginResponse.jsonPath().getInt("id");
            client.delete(courierId);
        }
    }
}
