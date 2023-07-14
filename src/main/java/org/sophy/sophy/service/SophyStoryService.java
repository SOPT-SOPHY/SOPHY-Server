package org.sophy.sophy.service;

import lombok.RequiredArgsConstructor;
import org.sophy.sophy.domain.CompletedBooktalk;
import org.sophy.sophy.domain.Member;
import org.sophy.sophy.domain.dto.SophyStoryDto;
import org.sophy.sophy.domain.dto.SophyStoryRequestDto;
import org.sophy.sophy.exception.ErrorStatus;
import org.sophy.sophy.exception.model.NotFoundException;
import org.sophy.sophy.infrastructure.CompletedBooktalkRepository;
import org.sophy.sophy.infrastructure.MemberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SophyStoryService {

    private final MemberRepository memberRepository;
    private final CompletedBooktalkRepository completedBooktalkRepository;

    public List<SophyStoryDto> getMySophyStory(Long memberId, SophyStoryRequestDto sophyStoryRequestDto) { //연, 월 선택했을 때 반환하는 소피스토리
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));

        LocalDate targetDate = LocalDate.of(sophyStoryRequestDto.getYear(), sophyStoryRequestDto.getMonth(), 1);
        List<CompletedBooktalk> completedBooktalkList = completedBooktalkRepository.findAllByMemberAndCreateAtBetween(member //선택한 연 월에 해당되는 북토크 조회
                , LocalDateTime.of(targetDate
                        , LocalTime.of(0, 0, 0))
                , LocalDateTime.of(LocalDate.of(sophyStoryRequestDto.getYear(), sophyStoryRequestDto.getMonth(), targetDate.lengthOfMonth())
                        , LocalTime.of(23, 59, 59)));

        List<SophyStoryDto> sophyStoryDtos = new ArrayList<>();
        completedBooktalkList.forEach(completedBooktalk -> {
            sophyStoryDtos.add(SophyStoryDto.builder()
                    .title(completedBooktalk.getTitle())
                    .bookName(completedBooktalk.getBookName())
                    .authorName(completedBooktalk.getAuthorName())
                    .booktalkDate(completedBooktalk.getBooktalkDate())
                    .placeName(completedBooktalk.getPlaceName())
                    .build());
        });
        return sophyStoryDtos;
    }

    public List<SophyStoryDto> getMySophyStory(Long memberId) { //모든 소피스토리 반환
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));

        List<CompletedBooktalk> completedBooktalkList = member.getCompletedBookTalkList();

        List<SophyStoryDto> sophyStoryDtos = new ArrayList<>();
        completedBooktalkList.forEach(completedBooktalk -> {
            sophyStoryDtos.add(SophyStoryDto.builder()
                    .title(completedBooktalk.getTitle())
                    .bookName(completedBooktalk.getBookName())
                    .authorName(completedBooktalk.getAuthorName())
                    .booktalkDate(completedBooktalk.getBooktalkDate())
                    .placeName(completedBooktalk.getPlaceName())
                    .build());
        });
        return sophyStoryDtos;
    }
}
