package share.sh4re.exception;

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