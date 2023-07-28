package org.sophy.sophy.common.advice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import io.jsonwebtoken.ExpiredJwtException;
import io.lettuce.core.RedisCommandExecutionException;
import org.sophy.sophy.common.dto.ApiResponseDto;
import org.sophy.sophy.exception.ErrorStatus;
import org.sophy.sophy.exception.model.SophyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionAdvice {

    /*
    400 BAD_REQUEST
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ApiResponseDto handleMethodArgumentNotValidException(
        final MethodArgumentNotValidException e) {
        return ApiResponseDto.error(ErrorStatus.VALIDATION_REQUEST_MISSING_EXCEPTION);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RedisCommandExecutionException.class)
    protected ApiResponseDto handleRedisCommandExecutionException(
        final RedisCommandExecutionException e) {
        return ApiResponseDto.error(ErrorStatus.INVALID_TOKEN_INFO_EXCEPTION);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ExpiredJwtException.class)
    protected ApiResponseDto handleExpiredRefreshTokenException(final ExpiredJwtException e) {
        return ApiResponseDto.error(ErrorStatus.REFRESH_TOKEN_TIME_EXPIRED_EXCEPTION);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidFormatException.class)
    protected ApiResponseDto handleInvalidFormatException(final InvalidFormatException e) {
        return ApiResponseDto.error(ErrorStatus.INVALID_FORMAT_EXCEPTION);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(IllegalArgumentException.class)
    protected ApiResponseDto handleIllegalArgumentException(final IllegalArgumentException e) {
        return ApiResponseDto.error(ErrorStatus.NOT_FOUND_CITY_EXCEPTION);
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
