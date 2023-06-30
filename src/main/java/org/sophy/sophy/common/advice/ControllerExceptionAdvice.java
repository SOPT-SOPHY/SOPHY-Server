package org.sophy.sophy.common.advice;

import org.sophy.sophy.common.dto.ApiResponseDto;
import org.sophy.sophy.exception.model.SophyException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionAdvice {

    /**
     * 500 Internal Server
     */
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(Exception.class)
//    protected ApiResponseDto<Object> handleException(final Exception e) {
//        return ApiResponseDto.error(ErrorStatus.INTERNAL_SERVER_ERROR);
//    }

    /**
     * Sopt custom error
     */
    @ExceptionHandler(SophyException.class)
    protected ResponseEntity<ApiResponseDto> handleSophyException(SophyException e) {
        return ResponseEntity.status(e.getHttpStatus())
                .body(ApiResponseDto.error(e.getErrorStatus(), e.getMessage()));
    }
}
