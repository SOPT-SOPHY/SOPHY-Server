package org.sophy.sophy.domain;

public enum BooktalkStatus {
    //개설 신청/장소 확정/청중 모집 중/모집 마감/완료
    //매칭 대기중/매칭 거절됨/모집 중/모집 마감/모집 예정
    APPLYING, //매칭 대기중
    PLACE_REFUSED, //매칭 거절됨
    PLACE_CONFIRMED, //장소 확정
    RECRUITING_EXPECTED, //모집 예정
    RECRUITING, //모집 중
    RECRUITING_CLOSED, //모집 마감
    COMPLETED //완료된 북토크
}
