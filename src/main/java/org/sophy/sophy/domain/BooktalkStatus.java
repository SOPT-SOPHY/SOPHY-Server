package org.sophy.sophy.domain;

public enum BooktalkStatus {
    //개설 신청/장소 확정/청중 모집 중/모집 마감/완료
    //매칭 대기중/매칭 거절됨/모집 중/모집 마감/모집 예정
    APPLYING,
    PLACE_REFUSED,
    PLACE_CONFIRMED,
    RECRUITING_EXPECTED,
    RECRUITING,
    RECRUITING_CLOSED,
    COMPLETED
}
