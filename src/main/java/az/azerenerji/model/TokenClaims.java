package az.azerenerji.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TokenClaims {

    JWT_ID("jti"),
    SUBJECT("sub"),
    ISSUED_AT("iat"),
    EXPIRES_AT("exp"),
    ROLES("roles"),
    USER_ID("userId"),
    EMAIL("username");

    private final String value;

}