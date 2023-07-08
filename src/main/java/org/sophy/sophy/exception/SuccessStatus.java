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
    REISSUE_SUCCESS(HttpStatus.OK, "토큰 재발행에 성공했습니다."),
    CHECK_DUPL_EMAIL_SUCCESS(HttpStatus.OK, "사용 가능한 이메일 주소입니다."),
    GET_MYPAGE_SUCCESS(HttpStatus.OK, "마이페이지를 성공적으로 불러왔습니다."),
    GET_MYINFO_SUCCESS(HttpStatus.OK, "내 정보를 성공적으로 불러왔습니다."),
    TEST_SUCCESS(HttpStatus.OK, "Test :: OK"),
    /*
     * 201 created
     */
    SIGNUP_SUCCESS(HttpStatus.CREATED, "회원가입이 완료되었습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
