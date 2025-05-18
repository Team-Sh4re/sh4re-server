package share.sh4re.exceptions.exception;

import share.sh4re.exceptions.errorcode.ErrorCode;

public class CommentException extends CustomException {
  public CommentException() {
    super();
  }

  public CommentException(String message) {
    super(message);
  }

  public CommentException(String message, Throwable cause) {
    super(message, cause);
  }

  public CommentException(ErrorCode errorCode) {
    super(errorCode);
  }

  public CommentException(ErrorCode errorCode, Throwable cause) {
    super(errorCode, cause);
  }
}
