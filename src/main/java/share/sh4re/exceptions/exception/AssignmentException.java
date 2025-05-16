package share.sh4re.exceptions.exception;

import share.sh4re.exceptions.errorcode.ErrorCode;

public class AssignmentException extends CustomException {
  public AssignmentException() {
    super();
  }

  public AssignmentException(String message) {
    super(message);
  }

  public AssignmentException(String message, Throwable cause) {
    super(message, cause);
  }

  public AssignmentException(ErrorCode errorCode) {
    super(errorCode);
  }

  public AssignmentException(ErrorCode errorCode, Throwable cause) {
    super(errorCode, cause);
  }
}