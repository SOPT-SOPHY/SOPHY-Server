package org.sophy.sophy.service;

import lombok.RequiredArgsConstructor;
import org.sophy.sophy.domain.*;
import org.sophy.sophy.domain.enumerate.BooktalkStatus;
import org.sophy.sophy.infrastructure.BooktalkRepository;
import org.sophy.sophy.infrastructure.CompletedBooktalkRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SchedulerService {
    private final BooktalkRepository booktalkRepository;
    private final CompletedBooktalkRepository completedBooktalkRepository;

    @Scheduled(fixedDelay = 60000)
    @Transactional
    public void updateBooktalkStatus() { //1분마다 북토크 상태 측정
        List<Booktalk> recrutingBooktalks = booktalkRepository.findAllByBooktalkStatus(BooktalkStatus.RECRUITING);
        List<Booktalk> closedBooktalks = booktalkRepository.findAllByBooktalkStatus(BooktalkStatus.RECRUITING_CLOSED);

        recrutingBooktalks.forEach(booktalk -> {
            booktalkToCompletedBooktalk(booktalk);
        });

        closedBooktalks.forEach(booktalk -> {
            booktalkToCompletedBooktalk(booktalk);
        });
    }

    private void booktalkToCompletedBooktalk(Booktalk booktalk) { //북토크가 기간이 지났을 때 지난 북토크로 매핑하면서 객체들 상태 및 연관관계 변경
        if(booktalk.getEndDate().isBefore(LocalDateTime.now())){ //북토크 마감일이 현재시간보다 이전이라면
            booktalk.setBooktalkStatus(BooktalkStatus.COMPLETED);
            CompletedBooktalk completedBooktalk = CompletedBooktalk.builder()
                    .title(booktalk.getTitle())
                    .bookName(booktalk.getBook().getTitle())
                    .authorName(booktalk.getMember().getName())
                    .booktalkDate(booktalk.getEndDate())
                    .placeName(booktalk.getPlace().getName())
                    .build();
            completedBooktalkRepository.save(completedBooktalk);
            for(MemberBooktalk memberBooktalk : booktalk.getParticipantList()){
                Member member = memberBooktalk.getMember();
                member.getCompletedBookTalkList().add(completedBooktalk);
                completedBooktalk.setMember(member);
            }
            booktalkRepository.delete(booktalk);
        }
    }

    @Scheduled(cron = "0 0 10 * * *")
    @Transactional
    public void changeApprovedBooktalkStatus() { //오전 10시에 승인된 북토크들 모집중으로 변경
        List<Booktalk> scheduledBooktalk = ScheduledBooktalkConverter.getScheduledBooktalk();
        scheduledBooktalk.forEach(booktalk -> {
            booktalk.setBooktalkStatus(BooktalkStatus.RECRUITING);
        });
        scheduledBooktalk.clear();
        //스케줄러 포트 변경(이틀 뒤 변경부터 필요)
//        if (ScheduledBooktalkConverter.getPort() == 0) {
//            ScheduledBooktalkConverter.setPort(1);
//        } else {
//            ScheduledBooktalkConverter.setPort(0);
//        }
    }
}
