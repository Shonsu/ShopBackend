package pl.shonsu.shop.passwordresettoken.model;

import lombok.Getter;

@Getter
public class PasswordResetRequest {
    String password;
    String repeatedPassword;
    String token;
}
