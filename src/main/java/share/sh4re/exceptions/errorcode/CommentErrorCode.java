package share.sh4re.exceptions.errorcode;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import share.sh4re.exceptions.exception.CustomException;

@RequiredArgsConstructor
public enum CommentErrorCode implements ErrorCode {
  INVALID_ARGUMENT("요청 값이 유효하지 않습니다.", HttpStatus.BAD_REQUEST),
  COMMENT_NOT_FOUND("댓글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  FORBIDDEN_REQUEST("해당 작업을 수행할 권한이 없습니다.", HttpStatus.FORBIDDEN);

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
