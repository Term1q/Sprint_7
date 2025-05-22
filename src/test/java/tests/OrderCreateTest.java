package tests;


import baseTest.BaseTest;
import model.client.OrderClient;
import model.Order;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.*;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

@Epic("API Яндекс.Самокат")
@Feature("Создание заказа")
@RunWith(Parameterized.class)
public class OrderCreateTest extends BaseTest {
    private OrderClient orderClient;
    private List<String> colors;

    public OrderCreateTest(List<String> colors) {
        this.colors = colors;
    }

    @Parameterized.Parameters(name = "Colors: {0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {Collections.singletonList("BLACK")},
                {Collections.singletonList("GREY")},
                {Arrays.asList("BLACK", "GREY")},
                {Collections.emptyList()}
        });
    }

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    @Story("Позитивные сценарии")
    @Description("Создание заказа с разными цветами")
    @Severity(SeverityLevel.CRITICAL)
    public void shouldCreateOrderWithDifferentColors() {
        Order order = Order.builder()
                .firstName("Наруто")
                .lastName("Узумаки")
                .address("Москва")
                .metroStation(4)
                .phone("+78005553535")
                .rentTime(1)
                .deliveryDate("2025-05-05")
                .comment("Даттебаё")
                .color(colors)
                .build();

        Response response = orderClient.create(order);
        assertEquals(SC_CREATED, response.statusCode());
        assertNotNull(response.body().jsonPath().getInt("track"));
    }
}
