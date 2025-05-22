package model;

import lombok.*;

@Data
@Builder
public class Courier {
    private String login;
    private String password;
    private String firstName;
}