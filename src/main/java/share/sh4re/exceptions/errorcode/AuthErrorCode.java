package share.sh4re.exceptions.errorcode;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import share.sh4re.exceptions.exception.CustomException;

@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {
    TOKEN_EXPIRED("토큰이 만료되었습니다. 다시 로그인해주세요.", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN("유효하지 않은 토큰입니다.", HttpStatus.UNAUTHORIZED),
    AUTHENTICATION_FAILED("인증에 실패하였습니다.", HttpStatus.UNAUTHORIZED);

    private final String message;
    private final HttpStatus httpStatus;

    @Override
    public String defaultMessage() {
        return message;
    }

    @Override
    public HttpStatus defaultHttpStatus() {
        return httpStatus;
    }

    @Override
    public RuntimeException defaultException() {
        return new CustomException(this);
    }

    @Override
    public RuntimeException defaultException(Throwable cause) {
        return new CustomException(this, cause);
    }
}