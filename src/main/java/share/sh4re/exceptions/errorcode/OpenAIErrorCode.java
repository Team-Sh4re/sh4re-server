package share.sh4re.exceptions.errorcode;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import share.sh4re.exceptions.exception.CustomException;

@RequiredArgsConstructor
public enum OpenAIErrorCode implements ErrorCode {
    FAILED_TO_GENERATE("AI 생성에 실패했습니다.", HttpStatus.UNAUTHORIZED),
    INVALID_ARGUMENT("유효하지 않는 입력값입니다.", HttpStatus.UNAUTHORIZED);

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