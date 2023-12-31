package org.sophy.sophy.service;

import lombok.RequiredArgsConstructor;
import org.sophy.sophy.domain.Booktalk;
import org.sophy.sophy.domain.common.ScheduledBooktalkConverter;
import org.sophy.sophy.domain.enumerate.BooktalkStatus;
import org.sophy.sophy.infrastructure.BooktalkRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OperatorService {

    private final BooktalkRepository booktalkRepository;

    public List<Booktalk> getWaitingBooktalks(String email) { //승인 대기중 북토크 조회
        return booktalkRepository.getWatingBooktalks(email);
    }

    public void approveBooktalk(Long booktalkId) { //북토크 승인
        Booktalk booktalk = booktalkRepository.getBooktalkById(booktalkId);
        booktalk.setBooktalkStatus(BooktalkStatus.RECRUITING_EXPECTED);
        ScheduledBooktalkConverter.getScheduledBooktalk()
            .add(booktalk); //대기중 큐에 승인된 북토크 추가 -> 다음날 오전 10시에 해당 큐를 이용해 상태 변경
    }
}
