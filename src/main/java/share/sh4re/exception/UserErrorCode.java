package share.sh4re.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {
  USERNAME_ALREADY_EXISTS("이미 사용 중인 아이디입니다.", HttpStatus.CONFLICT),
  SIGN_UP_PASSWORD_INCORRECT("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
  SIGN_UP_FAILED_DEFAULT(
      "회원 가입을 다시 진행해 주십시오. 오류가 지속되는 경우 문의하시기 바랍니다.",
      HttpStatus.INTERNAL_SERVER_ERROR
  ),
  MEMBER_NOT_FOUND("회원을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  DEFAULT("회원 취급 중 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

  private final String message;
  private final HttpStatus httpStatus;

  @Override
  public String defaultMessage(){
    return message;
  }

  @Override
  public HttpStatus defaultHttpStatus(){
    return httpStatus;
  }

  @Override
  public UserException defaultException(){
    return new UserException(this);
  }

  @Override
  public UserException defaultException(Throwable cause){
    return new UserException(this, cause);
  }


}
