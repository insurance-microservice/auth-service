package test.avows.auth.exception;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {

    private final int status;
    private final String error;

    public ApiException(int status, String message, String error) {
        super(message);
        this.status = status;
        this.error = error;
    }
}
