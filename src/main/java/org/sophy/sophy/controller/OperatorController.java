package org.sophy.sophy.controller;

import lombok.RequiredArgsConstructor;
import org.sophy.sophy.domain.Booktalk;
import org.sophy.sophy.infrastructure.MemberRepository;
import org.sophy.sophy.service.OperatorService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/operator")
public class OperatorController {

    private final OperatorService operatorService;
    private final MemberRepository memberRepository;

    @GetMapping
    public List<Booktalk> getWaitingBooktalks(@AuthenticationPrincipal User user) { //승인 대기중 북토크 조회
        Long memberId = memberRepository.getMemberByEmail(user.getUsername()).getId();
        return operatorService.getWaitingBooktalks(memberId);
    }

    @PostMapping("/{booktalkId}")
    public void approveBooktalk(@PathVariable(name = "booktalkId") Long booktalkId) { //북토크 승인
        operatorService.approveBooktalk(booktalkId);
    }
}
