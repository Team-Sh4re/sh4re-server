package share.sh4re.exceptions.exception;

import org.springframework.http.HttpStatus;
import share.sh4re.exceptions.errorcode.ErrorCode;

public class CustomException extends RuntimeException {
  protected ErrorCode ERROR_CODE;

  private static ErrorCode getDefaultErrorCode(){
    return DefaultErrorCodeHolder.DEFAULT_ERROR_CODE;
  }

  private static class DefaultErrorCodeHolder {
    private static final ErrorCode DEFAULT_ERROR_CODE = new ErrorCode() {
      @Override
      public String name(){
        return "SERVER_ERROR";
      }

      @Override
      public HttpStatus defaultHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
      }

      @Override
      public String defaultMessage(){
        return "서버 오류";
      }

      @Override
      public RuntimeException defaultException(){
        return new CustomException("SERVER_ERROR");
      }

      @Override
      public RuntimeException defaultException(Throwable cause){
        return new CustomException("SERVER_ERROR", cause);
      }
    };
  }

  public CustomException(){
    this.ERROR_CODE = getDefaultErrorCode();
  }

  public CustomException(String message){
    super(message);
    this.ERROR_CODE = getDefaultErrorCode();
  }

  public CustomException(String message, Throwable cause){
    super(message, cause);
    this.ERROR_CODE = getDefaultErrorCode();
  }

  public CustomException(ErrorCode errorCode){
    super(errorCode.defaultMessage());
    this.ERROR_CODE = errorCode;
  }

  public CustomException(ErrorCode errorCode, Throwable cause){
    super(errorCode.defaultMessage(), cause);
    this.ERROR_CODE = errorCode;
  }

  public ErrorCode getErrorCode(){
    return ERROR_CODE;
  }
}
