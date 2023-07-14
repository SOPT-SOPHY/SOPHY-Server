package org.sophy.sophy.service;

import lombok.RequiredArgsConstructor;
import org.sophy.sophy.domain.Booktalk;
import org.sophy.sophy.domain.CompletedBooktalk;
import org.sophy.sophy.domain.Member;
import org.sophy.sophy.domain.MemberBooktalk;
import org.sophy.sophy.domain.enumerate.BooktalkStatus;
import org.sophy.sophy.infrastructure.BooktalkRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SchedulerService {
    private final BooktalkRepository booktalkRepository;

    @Scheduled(fixedDelay = 60000)
    @Transactional
    public void updateBooktalkStatus() {
        List<Booktalk> recrutingBooktalks = booktalkRepository.findAllByBooktalkStatus(BooktalkStatus.RECRUITING);
        List<Booktalk> closedBooktalks = booktalkRepository.findAllByBooktalkStatus(BooktalkStatus.RECRUITING_CLOSED);

        recrutingBooktalks.forEach(SchedulerService::booktalkToCompletedBooktalk);

        closedBooktalks.forEach(SchedulerService::booktalkToCompletedBooktalk);
    }

    private static void booktalkToCompletedBooktalk(Booktalk booktalk) { //북토크가 기간이 지났을 때 지난 북토크로 매핑하면서 객체들 상태 및 연관관계 변경
        if(booktalk.getEndDate().isBefore(LocalDateTime.now())){
            booktalk.setBooktalkStatus(BooktalkStatus.COMPLETED);
            CompletedBooktalk completedBooktalk = CompletedBooktalk.builder()
                    .title(booktalk.getTitle())
                    .bookName(booktalk.getBook().getTitle())
                    .authorName(booktalk.getMember().getName())
                    .booktalkDate(booktalk.getEndDate())
                    .placeName(booktalk.getPlace().getName())
                    .build();
            for(MemberBooktalk memberBooktalk : booktalk.getParticipantList()){
                Member member = memberBooktalk.getMember();
                member.getCompletedBookTalkList().add(completedBooktalk);
                completedBooktalk.setMember(member);
            }
        }
    }
}
