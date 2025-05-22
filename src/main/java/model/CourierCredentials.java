package model;

import lombok.*;

@Data
@Builder

public class CourierCredentials {
    private String login;
    private String password;
}
