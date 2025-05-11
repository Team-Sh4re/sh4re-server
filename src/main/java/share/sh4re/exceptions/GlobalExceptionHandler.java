package share.sh4re.exceptions;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import share.sh4re.exceptions.errorcode.UserErrorCode;
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


  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponseError> handleValidationExceptions(MethodArgumentNotValidException ex) {
    List<ApiSimpleError> errors = new ArrayList<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.add(ApiSimpleError.builder().field(fieldName).message(errorMessage).build());
    });

    CustomException customException = UserErrorCode.INVALID_ARGUMENT.defaultException();
    ApiResponseError response = ApiResponseError.builder()
        .code(UserErrorCode.INVALID_ARGUMENT.name())
        .status(UserErrorCode.INVALID_ARGUMENT.defaultHttpStatus().value())
        .name("ValidationException")
        .message("요청값이 유효하지 않습니다")
        .cause(errors)
        .build();

    return new ResponseEntity<>(response, UserErrorCode.INVALID_ARGUMENT.defaultHttpStatus());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponseError> handleException(Exception exception) {
    System.out.println("exception = " + exception);
    ApiResponseError response = ApiResponseError.of(new CustomException());
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
