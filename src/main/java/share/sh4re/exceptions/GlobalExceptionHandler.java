package share.sh4re.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import share.sh4re.exceptions.exception.CustomException;

@RestControllerAdvice
public final class GlobalExceptionHandler {
  @ExceptionHandler(CustomException.class)
  public ResponseEntity<ApiResponseError> handleMemberException(CustomException exception) {
    ApiResponseError response = ApiResponseError.of(exception);
    HttpStatus httpStatus = exception
        .getErrorCode()
        .defaultHttpStatus();

    return new ResponseEntity<>(response, httpStatus);
  }


  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponseError> handleException(Exception exception) {
    System.out.println("exception = " + exception);
    ApiResponseError response = ApiResponseError.of(new CustomException());
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }

}