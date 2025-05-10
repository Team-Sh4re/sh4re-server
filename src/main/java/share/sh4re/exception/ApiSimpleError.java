package share.sh4re.exception;

import java.util.List;
import lombok.Builder;
import lombok.NonNull;

@Builder
public record ApiSimpleError(@NonNull String field, @NonNull String message) {
  public static List<ApiSimpleError> listOfCauseSimpleError(Throwable cause) {
    return List.of(arrayOfCauseSimpleError(cause));
  }

  public static ApiSimpleError[] arrayOfCauseSimpleError(Throwable cause) {
    int depth = 0;
    ApiSimpleError[] subErrors;
    Throwable currentCause = cause;

    while (currentCause != null) {
      currentCause = currentCause.getCause();
      depth++;
    }

    subErrors = new ApiSimpleError[depth];
    currentCause = cause;
    for (int i = 0; i < depth; i++) {
      String errorFullName = currentCause.getClass().getSimpleName();
      String field = errorFullName.substring(errorFullName.lastIndexOf('.') + 1);
      subErrors[i] = ApiSimpleError.builder()
          .field(field)
          .message(currentCause.getLocalizedMessage())
          .build();

      currentCause = currentCause.getCause();
    }

    return subErrors;
  }
}