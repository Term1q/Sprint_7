package tests;

import baseTest.BaseTest;
import model.client.OrderClient;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

@Epic("API Яндекс.Самокат")
@Feature("Список заказов")
public class OrderListTest extends BaseTest {
    private final OrderClient orderClient = new OrderClient();

    @Test
    @Story("Получение данных")
    @Description("Получение списка заказов")
    @Severity(SeverityLevel.NORMAL)
    public void shouldGetOrdersList() {
        Response response = orderClient.getOrders();
        assertEquals(SC_OK, response.statusCode());
        assertFalse(response.body().jsonPath().getList("orders").isEmpty());
        assertNotNull(response.body().jsonPath().getString("orders[0].id"));
    }
}