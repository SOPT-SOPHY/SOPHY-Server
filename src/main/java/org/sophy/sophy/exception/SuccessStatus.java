package org.sophy.sophy.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum SuccessStatus {
    /**
     * 200 OK
     */
    LOGIN_SUCCESS(HttpStatus.OK, "로그인에 성공했습니다."),
    /*
     * 201 created
     */
    SIGNUP_SUCCESS(HttpStatus.CREATED, "회원가입이 완료되었습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
