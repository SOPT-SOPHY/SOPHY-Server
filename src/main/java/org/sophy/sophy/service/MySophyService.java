package org.sophy.sophy.service;

import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.sophy.sophy.domain.CompletedBooktalk;
import org.sophy.sophy.domain.Member;
import org.sophy.sophy.domain.dto.SophyStoryDto;
import org.sophy.sophy.domain.dto.SophyStoryRequestDto;
import org.sophy.sophy.domain.dto.mypage.MyPageBooktalkDto;
import org.sophy.sophy.infrastructure.CompletedBooktalkRepository;
import org.sophy.sophy.infrastructure.MemberRepository;
import org.sophy.sophy.infrastructure.query.BooktalkQueryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MySophyService {

    private final MemberRepository memberRepository;
    private final CompletedBooktalkRepository completedBooktalkRepository;
    private final BooktalkQueryRepository booktalkQueryRepository;

    public List<SophyStoryDto> getMySophyStory(String email,
        SophyStoryRequestDto sophyStoryRequestDto) { //연, 월 선택했을 때 반환하는 소피스토리
        Member member = memberRepository.getMemberByEmail(email);

        LocalDate targetDate = LocalDate.of(sophyStoryRequestDto.getYear(),
            sophyStoryRequestDto.getMonth(), 1);
        List<CompletedBooktalk> completedBooktalkList = completedBooktalkRepository.findAllByMemberAndCreateAtBetween(
            member //선택한 연 월에 해당되는 북토크 조회
            , LocalDateTime.of(targetDate
                , LocalTime.of(0, 0, 0))
            , LocalDateTime.of(
                LocalDate.of(sophyStoryRequestDto.getYear(), sophyStoryRequestDto.getMonth(),
                    targetDate.lengthOfMonth())
                , LocalTime.of(23, 59, 59)));

        List<SophyStoryDto> sophyStoryDtos = new ArrayList<>();
        completedBooktalkList.forEach(completedBooktalk -> sophyStoryDtos.add(SophyStoryDto.builder()
            .title(completedBooktalk.getTitle())
            .bookName(completedBooktalk.getBookName())
            .authorName(completedBooktalk.getAuthorName())
            .booktalkDate(completedBooktalk.getBooktalkDate())
            .placeName(completedBooktalk.getPlaceName())
            .build()));
        return sophyStoryDtos;
    }

    public List<SophyStoryDto> getMySophyStory(String email) { //모든 소피스토리 반환
        Member member = memberRepository.getMemberByEmail(email);

        List<CompletedBooktalk> completedBooktalkList = member.getCompletedBookTalkList();
        List<SophyStoryDto> sophyStoryDtos = new ArrayList<>();

        completedBooktalkList.forEach(completedBooktalk -> sophyStoryDtos.add(SophyStoryDto.builder()
            .title(completedBooktalk.getTitle())
            .bookName(completedBooktalk.getBookName())
            .authorName(completedBooktalk.getAuthorName())
            .booktalkDate(completedBooktalk.getBooktalkDate())
            .placeName(completedBooktalk.getPlaceName())
            .bookCategory(completedBooktalk.getBookCategory())
            .build()));
        return sophyStoryDtos;
    }

    //예정된 북토크 메서드
    public List<MyPageBooktalkDto> getBooktalksByMember(String email) { //예정된 북토크 조회 메서드
        List<Long> booktalkIds = memberRepository.getMemberByEmail(email).getUserBookTalkList()
            .stream().map(b -> b.getBooktalk().getId())
            .collect(Collectors.toList());

        return booktalkQueryRepository.findBooktalks(booktalkIds);
    }
}
