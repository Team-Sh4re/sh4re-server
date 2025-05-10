package share.sh4re.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import share.sh4re.exception.ApiResponseError;
import share.sh4re.exception.AuthErrorCode;
import share.sh4re.exception.AuthException;
import share.sh4re.exception.ErrorCode;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public void commence(HttpServletRequest request,
    HttpServletResponse response,
    AuthenticationException authException) throws IOException, ServletException {
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding("UTF-8");
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    ErrorCode errorCode;
    if (authException.getCause() instanceof ExpiredJwtException) {
      errorCode = AuthErrorCode.TOKEN_EXPIRED;
    } else if (authException.getCause() instanceof JwtException || authException instanceof BadCredentialsException) {
      errorCode = AuthErrorCode.INVALID_TOKEN;
    } else {
      errorCode = AuthErrorCode.AUTHENTICATION_FAILED;
    }
    AuthException exception = new AuthException(errorCode, authException.getCause());
    ApiResponseError errorResponse = ApiResponseError.of(exception);

    objectMapper.writeValue(response.getOutputStream(), errorResponse);
  }
}
