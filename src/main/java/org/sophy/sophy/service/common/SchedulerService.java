package org.sophy.sophy.service.common;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.sophy.sophy.domain.Booktalk;
import org.sophy.sophy.domain.common.ScheduledBooktalkConverter;
import org.sophy.sophy.domain.enumerate.BooktalkStatus;
import org.sophy.sophy.infrastructure.query.BooktalkQueryRepository;
import org.sophy.sophy.service.BooktalkService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SchedulerService {
    private final BooktalkQueryRepository booktalkQueryRepository;
    private final BooktalkService booktalkService;

    @Scheduled(fixedDelay = 60000)
    @Transactional
    public void updateBooktalkStatus() { //1분마다 북토크 상태 측정
        List<Booktalk> recrutingBooktalks = booktalkQueryRepository.findByBooktalkStatuses(
            Arrays.asList(BooktalkStatus.RECRUITING, BooktalkStatus.RECRUITING_CLOSED)
        );

        recrutingBooktalks.forEach(this::booktalkToCompletedBooktalk);
    }

    private void booktalkToCompletedBooktalk(
        Booktalk booktalk) { //북토크가 기간이 지났을 때 지난 북토크로 매핑하면서 객체들 상태 및 연관관계 변경
        if (booktalk.getEndDate().isBefore(LocalDateTime.now())) { //북토크 마감일이 현재시간보다 이전이라면
            booktalkService.changeBooktalkToComplete(booktalk);
        }
    }

    @Scheduled(cron = "0 0 10 * * *")
    @Transactional
    public void changeApprovedBooktalkStatus() { //오전 10시에 승인된 북토크들 모집중으로 변경
        List<Booktalk> scheduledBooktalk = ScheduledBooktalkConverter.getScheduledBooktalk();
        for (Booktalk booktalk : scheduledBooktalk) {
            booktalk.setBooktalkStatus(BooktalkStatus.RECRUITING);
        }
        scheduledBooktalk.clear();
        //스케줄러 포트 변경(이틀 뒤 변경부터 필요)
//        if (ScheduledBooktalkConverter.getPort() == 0) {
//            ScheduledBooktalkConverter.setPort(1);
//        } else {
//            ScheduledBooktalkConverter.setPort(0);
//        }
    }
}
