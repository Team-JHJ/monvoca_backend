package me.kjeok.monvoca.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRequest {
    private String userName;
    private String userPassword;
    private String userEmail;

}
