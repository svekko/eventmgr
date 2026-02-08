package ee.svekko.eventmgr.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler(value = ApiException.class)
    public ErrorResponse handleApiException(ApiException e) {
        log.warn("API exception: {}", e.getApiError(), e);
        return ErrorResponse.builder(e, e.getHttpStatus(), e.getApiError().name()).build();
    }
}
