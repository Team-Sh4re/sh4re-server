package share.sh4re.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import share.sh4re.exceptions.ApiResponseError;
import share.sh4re.exceptions.errorcode.AuthErrorCode;
import share.sh4re.exceptions.exception.AuthException;
import share.sh4re.exceptions.errorcode.ErrorCode;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Autowired
  private ObjectMapper objectMapper;

  @Override
  public void commence(HttpServletRequest request,
    HttpServletResponse response,
    AuthenticationException authException) throws IOException, ServletException {
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding("UTF-8");
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    // Get the original exception from the request if available
    Throwable exception = (Throwable) request.getAttribute("exception");
    if (exception == null) {
      exception = authException.getCause();
    }

    ErrorCode errorCode;
    if (exception instanceof ExpiredJwtException) {
      errorCode = AuthErrorCode.TOKEN_EXPIRED;
    } else if (exception instanceof JwtException || authException instanceof BadCredentialsException) {
      errorCode = AuthErrorCode.INVALID_TOKEN;
    } else {
      errorCode = AuthErrorCode.AUTHENTICATION_FAILED;
    }

//    if (exception != null) {
//      log.error("Authentication failed: {}", exception.getMessage());
//    }

    AuthException authException2 = new AuthException(errorCode, exception);
    ApiResponseError errorResponse = ApiResponseError.of(authException2);

    objectMapper.writeValue(response.getOutputStream(), errorResponse);
  }
}
