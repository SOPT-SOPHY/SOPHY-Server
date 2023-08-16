package org.sophy.sophy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sophy.sophy.common.dto.ApiResponseDto;
import org.sophy.sophy.domain.dto.booktalk.request.BooktalkParticipationRequestDto;
import org.sophy.sophy.domain.dto.booktalk.response.BooktalkDetailResponseDto;
import org.sophy.sophy.domain.dto.booktalk.response.BooktalkResponseDto;
import org.sophy.sophy.domain.enumerate.City;
import org.sophy.sophy.exception.SuccessStatus;
import org.sophy.sophy.service.BooktalkService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/booktalk")
@Tag(name = "북토크", description = "북토크 관련 API docs")
@SecurityRequirement(name = "JWT Auth")
public class BooktalkController {

    private final BooktalkService booktalkService;

    @GetMapping("/search/{booktalkId}/detail")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "북토크 상세 조회")
    public ApiResponseDto<BooktalkDetailResponseDto> getBooktalkDetail(
        @Parameter(example = "1") @PathVariable("booktalkId") Long booktalkId) {
        return ApiResponseDto.success(SuccessStatus.GET_BOOKTALK_DETAIL_SUCCESS,
            booktalkService.getBooktalkDetail(booktalkId));
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "지역으로 북토크 조회")
    public ApiResponseDto<List<BooktalkResponseDto>> getBooktalksByCities(
        @Parameter(description = "지역 이름", example = "UIJEONGBU_DONG") @Valid @RequestParam(name = "cities") List<City> cities) {
        return ApiResponseDto.success(SuccessStatus.GET_BOOKTALKS_BY_CITY_SUCCESS,
            booktalkService.getBooktalksByCities(cities));
    }

    @PostMapping("/participation")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "북토크 참가")
    public ApiResponseDto postBooktalkParticipation(
        @Valid @RequestBody BooktalkParticipationRequestDto booktalkParticipationRequestDto,
        @Parameter(hidden = true) @AuthenticationPrincipal User user) {
        booktalkService.postBooktalkParticipation(booktalkParticipationRequestDto,
            user.getUsername());
        return ApiResponseDto.success(SuccessStatus.CREATE_BOOKTALK_PARTICIPATION_SUCCESS);
    }
}
