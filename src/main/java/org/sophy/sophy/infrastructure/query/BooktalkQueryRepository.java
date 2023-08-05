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
        + " b.id, b.preliminaryInfo, b.title, m.name, b.startDate, b.endDate, p.name, pr.size, b.maximum, b.booktalkImageUrl)"
        + " from Booktalk b"
        + " join b.member m"
        + " join b.place p"
        + " left outer join b.participantList pr"
        + " where b.booktalkStatus = :booktalkStatus")
    List<BooktalkResponseDto> findBooktalkResponseDto(
        @Param("booktalkStatus") BooktalkStatus booktalkStatus);

    @Query(
        "select new org.sophy.sophy.domain.dto.booktalk.response.BooktalkResponseDto("
            + " b.id, b.preliminaryInfo, b.title, m.name, b.startDate, b.endDate, p.name, pr.size, b.maximum, b.booktalkImageUrl)"
            + " from Booktalk b"
            + " join b.member m"
            + " join b.place p"
            + " left outer join b.participantList pr"
            + " where b.city = :city"
            + " and b.booktalkStatus = :booktalkStatus"
            + " order by b.startDate")
    List<BooktalkResponseDto> findBooktalkResponseDto(@Param("city") City city,
        @Param("booktalkStatus") BooktalkStatus booktalkStatus);

    //sql의 in절을 통해 모집중과 모집 마감 북토크들을 한번에 조회
    @Query("select b from Booktalk b where b.booktalkStatus in :booktalkStatuses")
    List<Booktalk> findByBooktalkStatuses(
        @Param("booktalkStatuses") List<BooktalkStatus> booktalkStatuses);

    @Query("select new org.sophy.sophy.domain.dto.mypage.MyPageBooktalkDto("
        + " b.id, b.booktalkImageUrl, b.title, m.name, b.startDate, b.endDate, p.name, pr.size, b.maximum, b.booktalkStatus)"
        + " from Booktalk b"
        + " join b.member m"
        + " join b.place p"
        + " left outer join b.participantList pr"
        + " where b.id in :booktalkIds"
        + " order by b.startDate")
    List<MyPageBooktalkDto> findBooktalks(
        @Param("booktalkIds") List<Long> booktalkIds); //마이페이지의 북토크를 조회하는 쿼리
}
