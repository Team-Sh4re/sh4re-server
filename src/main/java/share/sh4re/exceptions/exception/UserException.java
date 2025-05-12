package share.sh4re.exceptions.exception;

import share.sh4re.exceptions.errorcode.ErrorCode;

public class UserException extends CustomException {
  public UserException() {
    super();
  }

  public UserException(String message) {
    super(message);
  }

  public UserException(String message, Throwable cause) {
    super(message, cause);
  }

  public UserException(ErrorCode errorCode) {
    super(errorCode);
  }

  public UserException(ErrorCode errorCode, Throwable cause) {
    super(errorCode, cause);
  }
}