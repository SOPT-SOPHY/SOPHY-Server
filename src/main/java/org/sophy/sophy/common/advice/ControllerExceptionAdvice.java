package org.sophy.sophy.common.advice;

import io.jsonwebtoken.ExpiredJwtException;
import org.sophy.sophy.common.dto.ApiResponseDto;
import org.sophy.sophy.exception.ErrorStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.sophy.sophy.exception.model.SophyException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionAdvice {
    /*
    400 BAD_REQUEST
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ApiResponseDto handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        return ApiResponseDto.error(ErrorStatus.VALIDATION_REQUEST_MISSING_EXCEPTION);
    }
    /**
     * 500 Internal Server
     */
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(Exception.class)
//    protected ApiResponseDto<Object> handleException(final Exception e) {
//        return ApiResponseDto.error(ErrorStatus.INTERNAL_SERVER_ERROR);
//    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ExpiredJwtException.class)
    protected ApiResponseDto handleExpiredRefreshTokenException(final ExpiredJwtException e) {
        return ApiResponseDto.error(ErrorStatus.REFRESH_TOKEN_TIME_EXPIRED_EXCEPTION);
    }

    /**
     * Sopt custom error
     */
    @ExceptionHandler(SophyException.class)
    protected ResponseEntity<ApiResponseDto> handleSophyException(SophyException e) {
        return ResponseEntity.status(e.getHttpStatus())
                .body(ApiResponseDto.error(e.getErrorStatus(), e.getMessage()));
    }
}
