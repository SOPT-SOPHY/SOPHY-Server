package org.sophy.sophy.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.sophy.sophy.common.dto.ApiResponseDto;
import org.sophy.sophy.exception.SuccessStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Hidden
public class TestController {

    @GetMapping("/test")
    public ApiResponseDto<String> test() {
        return ApiResponseDto.success(SuccessStatus.TEST_SUCCESS,
            SuccessStatus.TEST_SUCCESS.getMessage());
    }
}
