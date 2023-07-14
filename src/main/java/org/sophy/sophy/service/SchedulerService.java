package org.sophy.sophy.service;

import lombok.RequiredArgsConstructor;
import org.sophy.sophy.domain.Booktalk;
import org.sophy.sophy.domain.enumerate.BooktalkStatus;
import org.sophy.sophy.infrastructure.BooktalkRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SchedulerService {
    private final BooktalkRepository booktalkRepository;

    @Scheduled(fixedDelay = 60000)
    public void updateBooktalkStatus() {
        List<Booktalk> recrutingBooktalks = booktalkRepository.findAllByBooktalkStatus(BooktalkStatus.RECRUITING);
        List<Booktalk> closedBooktalks = booktalkRepository.findAllByBooktalkStatus(BooktalkStatus.RECRUITING_CLOSED);

        recrutingBooktalks.forEach(booktalk -> {
            if(booktalk.getEndDate().isBefore(LocalDateTime.now())){
                booktalk.setBooktalkStatus(BooktalkStatus.COMPLETED);
            }
        });

        closedBooktalks.forEach(booktalk -> {
            if(booktalk.getEndDate().isBefore(LocalDateTime.now())){
                booktalk.setBooktalkStatus(BooktalkStatus.COMPLETED);
            }
        });
    }
}
