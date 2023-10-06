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
    LOGOUT_SUCCESS(HttpStatus.OK, "로그아웃에 성공했습니다."),
    WITHDRAWAL_SUCCESS(HttpStatus.OK, "회원 탈퇴에 성공했습니다."),
    REISSUE_SUCCESS(HttpStatus.OK, "토큰 재발행에 성공했습니다."),
    CHECK_DUPL_EMAIL_SUCCESS(HttpStatus.OK, "사용 가능한 이메일 주소입니다."),
    GET_MY_SOPHY_SUCCESS(HttpStatus.OK, "나의 소피를 성공적으로 불러왔습니다."),
    GET_MYINFO_SUCCESS(HttpStatus.OK, "내 정보를 성공적으로 불러왔습니다."),
    PATCH_MYINFO_SUCCESS(HttpStatus.OK, "내 정보를 성공적으로 수정했습니다."),
    POST_ADDITIONALINFO_SUCCESS(HttpStatus.OK, "내 정보를 성공적으로 추가했습니다."),
    GET_PLACES_BY_CITY_SUCCESS(HttpStatus.OK, "지역으로 공간 리스트를 성공적으로 불러왔습니다."),
    PATCH_BOOKTALK_SUCCESS(HttpStatus.OK, "북토크를 성공적으로 수정했습니다."),
    DELETE_BOOKTALK_SUCCESS(HttpStatus.OK, "북토크를 성공적으로 삭제했습니다."),
    GET_BOOKTALK_DETAIL_SUCCESS(HttpStatus.OK, "북토크 상세정보를 성공적으로 불러왔습니다."),
    GET_BOOKTALKS_BY_CITY_SUCCESS(HttpStatus.OK, "지역으로 북토크 리스트를 성공적으로 불러왔습니다"),
    GET_BOOKTALKS_DEADLINE_UPCOMING_SUCCESS(HttpStatus.OK, "마감임박 소피 북토크 리스트를 성공적으로 불러왔습니다."),
    GET_MY_BOOKTALKS_SUCCESS(HttpStatus.OK, "예정된 북토크 리스트를 성공적으로 불러왔습니다"),
    GET_AUTHOR_BOOKTALKS_SUCCESS(HttpStatus.OK, "작가 북토크 리스트를 성공적으로 불러왔습니다"),
    GET_HOME_SUCCESS(HttpStatus.OK, "홈페이지를 성공적으로 불러왔습니다"),
    GET_GUEST_HOME_SUCCESS(HttpStatus.OK, "게스트 홈페이지를 성공적으로 불러왔습니다"),
    GET_AUTHOR_HOME_SUCCESS(HttpStatus.OK, "작가 홈페이지를 성공적으로 불러왔습니다"),
    GET_SOPHY_STORY_SUCCESS(HttpStatus.OK, "소피스토리를 성공적으로 불러왔습니다"),
    PATCH_PROFILE_SUCCESS(HttpStatus.OK, "프로필 이미지를 성공적으로 수정했습니다."),
    TEST_SUCCESS(HttpStatus.OK, "Test :: OK"),
    /*
     * 201 created
     */
    SIGNUP_SUCCESS(HttpStatus.CREATED, "회원가입이 완료되었습니다."),
    CREATE_AUTHCODE_SUCCESS(HttpStatus.CREATED, "이메일 인증 코드를 성공적으로 발송하였습니다."),
    CREATE_BOOKTALK_SUCCESS(HttpStatus.CREATED, "북토크를 생성했습니다."),
    CREATE_BOOKTALK_PARTICIPATION_SUCCESS(HttpStatus.CREATED, "북토크 참여 신청을 성공했습니다."),
    CREATE_PLACE_SUCCESS(HttpStatus.CREATED, "공간을 생성했습니다."),

    ;

    private final HttpStatus httpStatus;
    private final String message;
}
