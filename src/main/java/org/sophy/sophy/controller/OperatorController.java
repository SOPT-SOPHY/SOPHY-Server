package org.sophy.sophy.controller;

import lombok.RequiredArgsConstructor;
import org.sophy.sophy.domain.Booktalk;
import org.sophy.sophy.service.OperatorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/operator")
public class OperatorController {

    private final OperatorService operatorService;

    @GetMapping("/{memberId}")
    public List<Booktalk> getWaitingBooktalks(@PathVariable(name = "memberId") Long memberId) { //승인 대기중 북토크 조회
        return operatorService.getWaitingBooktalks(memberId);
    }

    @PostMapping("/{booktalkId}")
    public void approveBooktalk(@PathVariable(name = "booktalkId") Long booktalkId) { //북토크 승인
        operatorService.approveBooktalk(booktalkId);
    }
}
