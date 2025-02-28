package me.kjeok.monvoca.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import me.kjeok.monvoca.domain.User;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL) // null 값인 필드는 제외
public class UserResponse {
    private String userName;
    private String userPassword;
    private String userEmail;

    public UserResponse(User user) {
        this.userName = user.getUserName();
        this.userPassword = user.getUserPassword();
        this.userEmail = user.getUserEmail();
    }
}
