package org.sophy.sophy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.sophy.sophy.domain.Booktalk;
import org.sophy.sophy.service.api.OperatorService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/operator")
@Tag(name = "공간 운영자", description = "공간 운영자 관련 API docs")
@SecurityRequirement(name = "JWT Auth")
public class OperatorController {

    private final OperatorService operatorService;

    @GetMapping
    @Operation(summary = "승인 대기중 북토크 조회")
    public List<Booktalk> getWaitingBooktalks(
        @Parameter(hidden = true) @AuthenticationPrincipal User user) {
        return operatorService.getWaitingBooktalks(user.getUsername());
    }

    @PostMapping("/{booktalkId}")
    @Operation(summary = "북토크 승인")
    public void approveBooktalk(@PathVariable(name = "booktalkId") Long booktalkId) {
        operatorService.approveBooktalk(booktalkId);
    }
}
