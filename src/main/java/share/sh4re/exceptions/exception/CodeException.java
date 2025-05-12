package share.sh4re.exceptions.exception;

import share.sh4re.exceptions.errorcode.ErrorCode;

public class CodeException extends CustomException {
  public CodeException() {
    super();
  }

  public CodeException(String message) {
    super(message);
  }

  public CodeException(String message, Throwable cause) {
    super(message, cause);
  }

  public CodeException(ErrorCode errorCode) {
    super(errorCode);
  }

  public CodeException(ErrorCode errorCode, Throwable cause) {
    super(errorCode, cause);
  }
}
