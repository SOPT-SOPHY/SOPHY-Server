package org.sophy.sophy.infrastructure.query;

import java.util.List;
import org.sophy.sophy.domain.Booktalk;
import org.sophy.sophy.domain.dto.booktalk.response.BooktalkResponseDto;
import org.sophy.sophy.domain.dto.mypage.MyPageBooktalkDto;
import org.sophy.sophy.domain.enumerate.BooktalkStatus;
import org.sophy.sophy.domain.enumerate.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BooktalkQueryRepository extends JpaRepository<Booktalk, Long> {

    @Query("select new org.sophy.sophy.domain.dto.booktalk.response.BooktalkResponseDto("
        + " b.id, b.preliminaryInfo, b.title, m.name, b.startDate, b.endDate, p.name, b.participantList.size, b.maximum, b.booktalkImageUrl, b.booktalkStatus)"
        + " from Booktalk b"
        + " join b.member m"
        + " join b.place p"
        + " where b.booktalkStatus in :booktalkStatuses")
    List<BooktalkResponseDto> findBooktalkResponseDto(@Param("booktalkStatuses") List<BooktalkStatus> booktalkStatuses);

//    @Query("select new org.sophy.sophy.domain.dto.booktalk.response.BooktalkResponseDto("
//        + " b.id, b.preliminaryInfo, b.title, m.name, b.startDate, b.endDate, p.name, b.participantNum, b.maximum, b.booktalkImageUrl)"
//        + " from Booktalk b"
//        + " join b.member m"
//        + " join b.place p"
//        + " where b.booktalkStatus = :booktalkStatus")
//    List<BooktalkResponseDto> findBooktalkResponseDto(
//        @Param("booktalkStatus") BooktalkStatus booktalkStatus);
//
//    @Query(
//        "select new org.sophy.sophy.domain.dto.booktalk.response.BooktalkResponseDto("
//            + " b.id, b.preliminaryInfo, b.title, m.name, b.startDate, b.endDate, p.name, b.participantNum, b.maximum, b.booktalkImageUrl)"
//            + " from Booktalk b"
//            + " join b.member m"
//            + " join b.place p"
//            + " where b.city in :cities"
//            + " and b.booktalkStatus = :booktalkStatus"
//            + " order by b.startDate")
//    List<BooktalkResponseDto> findBooktalkResponseDto(@Param("cities") List<City> cities,
//        @Param("booktalkStatus") BooktalkStatus booktalkStatus); //count 쿼리는 매우 무거운데 이거를 참가자 리스트를 불러와 count를 세는게 맞나?

    //sql의 in절을 통해 모집중과 모집 마감 북토크들을 한번에 조회
    @Query("select b from Booktalk b where b.booktalkStatus in :booktalkStatuses")
    List<Booktalk> findByBooktalkStatuses(
        @Param("booktalkStatuses") List<BooktalkStatus> booktalkStatuses);

    @Query("select new org.sophy.sophy.domain.dto.mypage.MyPageBooktalkDto("
        + " b.id, b.booktalkImageUrl, b.title, m.name, b.startDate, b.endDate, p.name, b.participantList.size, b.maximum, b.booktalkStatus)"
        + " from Booktalk b"
        + " join b.member m"
        + " join b.place p"
        + " where b.id in :booktalkIds"
        + " order by b.startDate")
    List<MyPageBooktalkDto> findBooktalks(
        @Param("booktalkIds") List<Long> booktalkIds); //마이페이지의 북토크를 조회하는 쿼리
}
