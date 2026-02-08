package ee.svekko.eventmgr.exception;

import ee.svekko.eventmgr.error.ApiError;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {
    private final ApiError apiError;

    private final HttpStatus httpStatus;

    private ApiException(ApiError apiError, HttpStatus httpStatus) {
        this.apiError = apiError;
        this.httpStatus = httpStatus;
    }

    public static ApiException badRequest(ApiError apiError) {
        return new ApiException(apiError, HttpStatus.BAD_REQUEST);
    }

    public static ApiException notFound(ApiError apiError) {
        return new ApiException(apiError, HttpStatus.NOT_FOUND);
    }

    public static ApiException unauthorized(ApiError apiError) {
        return new ApiException(apiError, HttpStatus.UNAUTHORIZED);
    }
}
