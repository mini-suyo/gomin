package com.ssafy.sushi.global.error;

import com.ssafy.sushi.global.common.response.ApiResponse;
import com.ssafy.sushi.global.error.exception.CustomException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestNotUsableException;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.io.IOException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * SSE 요청이 timeout 되었을 때 발생하는 예외를 처리합니다.
     */
    @ExceptionHandler(AsyncRequestTimeoutException.class)
    public void handleAsyncRequestTimeoutException(AsyncRequestTimeoutException e) {
        log.debug("SSE connection closed due to timeout - This is expected during server shutdown");
        // SSE timeout은 정상적인 상황이므로 별도 처리 없이 void 반환
    }

    @ExceptionHandler(ClientAbortException.class)
    public void handleClientAbortException(ClientAbortException e) {
        log.debug("SSE connection closed by client - Normal disconnection");
        // 클라이언트 연결 종료는 정상적인 상황이므로 별도 처리 없이 void 반환
    }

    /**
     * SSE 요청이 인증되지 않았을 때 발생하는 예외를 처리합니다.
     */
    @ExceptionHandler(AuthorizationDeniedException.class)
    public void handleAuthorizationDeniedException(AuthorizationDeniedException e, HttpServletRequest request) {
        if (request.isAsyncStarted()) {
            log.debug("SSE connection authorization denied - closing connection");
            try {
                request.getAsyncContext().complete();
            } catch (IllegalStateException ex) {
                // 이미 완료된 경우 무시
            }
        }
    }

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ApiResponse<Object>> handleCustomException(CustomException e, HttpServletRequest request) {
        if (!e.getParameters().isEmpty()) {
            log.error("[CustomException] {} {}: {} - Parameters: {}",
                    request.getMethod(),
                    request.getRequestURI(),
                    e.getMessage(),
                    e.getParameters()
            );
        } else {
            log.error("[CustomException] {} {}: {}",
                    request.getMethod(),
                    request.getRequestURI(),
                    e.getMessage()
            );
        }

        return ApiResponse.error(e.getErrorCode());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ApiResponse<Object>> handleException(Exception e) {
        // SSE 관련 예외는 위의 핸들러들에서 처리되므로 여기서는 다른 예외들만 처리
        if (e instanceof AsyncRequestNotUsableException ||
                e instanceof IOException && e.getMessage() != null &&
                        e.getMessage().contains("Broken pipe") ||
                e.getMessage().contains("연결은 사용자의 호스트 시스템의 소프트웨어의 의해 중단되었습니다")) {
//            log.info("SSE Connection closed by client");
            return null;
        }

        log.error("HandleException", e);
        return ApiResponse.error(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ApiResponse<Object>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e, HttpServletRequest request) {

        FieldError fieldError = e.getBindingResult().getFieldError();
        String field = fieldError.getField();
        String defaultMessage = fieldError.getDefaultMessage();

        log.error("[ValidationException] {} {}: field '{}' - {}",
                request.getMethod(),
                request.getRequestURI(),
                field,
                defaultMessage
        );
        return ApiResponse.error(ErrorCode.INVALID_INPUT_VALUE, field + " : " + defaultMessage);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    protected ResponseEntity<ApiResponse<Object>> handleNoResourceFoundException(
            NoResourceFoundException e, HttpServletRequest request) {
        log.warn("[ResourceNotFound] {} {}", request.getMethod(), request.getRequestURI());
        return ApiResponse.error(ErrorCode.RESOURCE_NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ApiResponse<Object>> handleIllegalArgumentException(
            IllegalArgumentException e, HttpServletRequest request) {
        // HTTP 메소드 이름 관련 예외는 보안 시도로 간주
        if (e.getMessage() != null && e.getMessage().contains("HTTP method names must be tokens")) {
            log.warn("[InvalidRequest] Malformed HTTP method from IP: {}",
                    request.getRemoteAddr());
            return ApiResponse.error(ErrorCode.INVALID_INPUT_VALUE);
        }

        // 다른 IllegalArgumentException은 기존대로 처리
        log.error("[IllegalArgument] {} {}: {}",
                request.getMethod(),
                request.getRequestURI(),
                e.getMessage());
        return ApiResponse.error(ErrorCode.INVALID_INPUT_VALUE);
    }
}
