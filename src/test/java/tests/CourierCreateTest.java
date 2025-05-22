package tests;

import baseTest.BaseTest;
import model.client.CourierClient;
import model.Courier;
import generator.CourierGenerator;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.*;
import steps.ApiSteps;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

@Epic("API Яндекс.Самокат")
@Feature("Создание курьера")
public class CourierCreateTest extends BaseTest {
    private CourierClient courierClient;
    private Courier courier;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = CourierGenerator.randomCourier();
    }

    @Test
    @Story("Позитивные сценарии")
    @Description("Успешное создание курьера")
    @Severity(SeverityLevel.BLOCKER)
    public void shouldCreateCourierSuccessfully() {
        Response response = ApiSteps.createCourier(courierClient, courier);
        assertEquals(SC_CREATED, response.statusCode());
        assertTrue(response.body().jsonPath().getBoolean("ok"));
    }

    @Test
    @Story("Негативные сценарии")
    @Description("Создание дубликата курьера")
    @Severity(SeverityLevel.CRITICAL)
    public void shouldNotCreateDuplicateCourier() {
        ApiSteps.createCourier(courierClient, courier);
        Response response = ApiSteps.createCourier(courierClient, courier);
        assertEquals(SC_CONFLICT, response.statusCode());
        assertEquals("Этот логин уже используется. Попробуйте другой.", response.body().jsonPath().getString("message"));
    }

    @Test
    @Story("Негативные сценарии")
    @Description("Создание курьера без обязательного поля")
    @Severity(SeverityLevel.NORMAL)
    public void shouldNotCreateCourierWithoutRequiredField() {
        courier.setLogin(null);
        Response response = ApiSteps.createCourier(courierClient, courier);
        assertEquals(SC_BAD_REQUEST, response.statusCode());
        assertEquals("Недостаточно данных для создания учетной записи", response.body().jsonPath().getString("message"));
    }

    @After
    public void tearDown() {
        ApiSteps.deleteTestCourier(courierClient, courier);
    }
}