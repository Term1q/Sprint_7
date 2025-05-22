package tests;


import baseTest.BaseTest;
import model.client.CourierClient;
import model.Courier;
import model.CourierCredentials;
import generator.CourierGenerator;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.*;
import steps.ApiSteps;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

@Epic("API Яндекс.Самокат")
@Feature("Логин курьера")
public class CourierLoginTest extends BaseTest {
    private CourierClient courierClient;
    private Courier courier;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = CourierGenerator.randomCourier();
        ApiSteps.createCourier(courierClient, courier);
    }

    @Test
    @Story("Позитивные сценарии")
    @Description("Успешный логин курьера")
    @Severity(SeverityLevel.BLOCKER)
    public void shouldLoginSuccessfully() {
        Response response = ApiSteps.loginCourier(courierClient,
                CourierCredentials.builder()
                        .login(courier.getLogin())
                        .password(courier.getPassword())
                        .build());

        assertEquals(SC_OK, response.statusCode());
        assertNotNull(response.body().jsonPath().getInt("id"));
    }

    @Test
    @Story("Негативные сценарии")
    @Description("Логин с неверным паролем")
    @Severity(SeverityLevel.CRITICAL)
    public void shouldNotLoginWithWrongPassword() {
        Response response = ApiSteps.loginCourier(courierClient,
                CourierCredentials.builder()
                        .login(courier.getLogin())
                        .password("wrong_password")
                        .build());

        assertEquals(SC_NOT_FOUND, response.statusCode());
        assertEquals("Учетная запись не найдена", response.body().jsonPath().getString("message"));
    }

    @After
    public void tearDown() {
        ApiSteps.deleteTestCourier(courierClient, courier);
    }
}