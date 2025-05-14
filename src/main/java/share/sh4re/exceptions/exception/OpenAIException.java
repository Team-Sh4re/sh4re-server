package share.sh4re.exceptions.exception;

import share.sh4re.exceptions.errorcode.ErrorCode;

public class OpenAIException extends CustomException {
  public OpenAIException() {
    super();
  }

  public OpenAIException(String message) {
    super(message);
  }

  public OpenAIException(String message, Throwable cause) {
    super(message, cause);
  }

  public OpenAIException(ErrorCode errorCode) {
    super(errorCode);
  }

  public OpenAIException(ErrorCode errorCode, Throwable cause) {
    super(errorCode, cause);
  }
}