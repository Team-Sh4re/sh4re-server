package share.sh4re.exceptions.errorcode;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import share.sh4re.exceptions.exception.CustomException;

@RequiredArgsConstructor
public enum AssignmentErrorCode implements ErrorCode {
  INVALID_ARGUMENT("요청 값이 유효하지 않습니다.", HttpStatus.BAD_REQUEST);

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
