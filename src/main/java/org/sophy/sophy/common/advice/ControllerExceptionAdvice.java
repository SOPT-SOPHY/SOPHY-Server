package org.sophy.sophy.common.advice;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import io.lettuce.core.RedisCommandExecutionException;
import org.sophy.sophy.common.dto.ApiResponseDto;
import org.sophy.sophy.exception.ErrorStatus;
import org.sophy.sophy.exception.model.SophyException;
import org.sophy.sophy.exception.model.SophyJwtException;
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
    protected ApiResponseDto<?> handleMethodArgumentNotValidException() {
        return ApiResponseDto.error(ErrorStatus.VALIDATION_REQUEST_MISSING_EXCEPTION);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RedisCommandExecutionException.class)
    protected ApiResponseDto<?> handleRedisCommandExecutionException() {
        return ApiResponseDto.error(ErrorStatus.INVALID_TOKEN_INFO_EXCEPTION);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    protected ApiResponseDto<?> handleIllegalArgumentException() {
        return ApiResponseDto.error(ErrorStatus.ILLEGAL_ARGUMENT_EXCEPTION);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidFormatException.class)
    protected ApiResponseDto<?> handleInvalidFormatException() {
        return ApiResponseDto.error(ErrorStatus.INVALID_FORMAT_EXCEPTION);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {SignatureException.class, JsonParseException.class})
    protected ApiResponseDto<?> handleSignatureException() {
        return ApiResponseDto.error(401, "Jwt 토큰의 형식이 잘못되었습니다.");
    }

    /*
    401 UN_AUTHORIZED
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ExpiredJwtException.class)
    protected ApiResponseDto<?> handleExpiredRefreshTokenException() {
        return ApiResponseDto.error(ErrorStatus.REFRESH_TOKEN_TIME_EXPIRED_EXCEPTION);
    }

    /**
     * Sopt custom error
     */
    @ExceptionHandler(SophyException.class)
    protected ResponseEntity<ApiResponseDto<?>> handleSophyException(SophyException e) {
        return ResponseEntity.status(e.getHttpStatus())
            .body(ApiResponseDto.error(e.getErrorStatus(), e.getMessage()));
    }

    @ExceptionHandler(SophyJwtException.class)
    protected ResponseEntity<ApiResponseDto<?>> handleSophyJwtException(SophyJwtException e) {
        return ResponseEntity.status(e.getHttpStatus())
            .body(ApiResponseDto.error(e.getHttpStatus(), e.getMessage()));
    }
}
