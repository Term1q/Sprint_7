package generator;


import model.Courier;
import java.time.Instant;

public class CourierGenerator {
    public static Courier randomCourier() {
        return Courier.builder()
                .login("ninja_courier" + Instant.now().getEpochSecond())
                .password("Dattebayo123")
                .firstName("Naruto")
                .build();
    }
}
