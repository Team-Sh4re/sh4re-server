package share.sh4re.config;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import share.sh4re.exception.AuthErrorCode;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtConfig jwtConfig;
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        // Skip authentication for refresh token endpoint
        if (request.getRequestURI().equals("/api/auth/refresh")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);

        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_PREFIX)) {
            String token = authorizationHeader.substring(BEARER_PREFIX.length());
            try {
                String username = jwtConfig.extractUsername(token);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    if (jwtConfig.validateToken(token, username)) {
                        UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    } else {
                        throw new BadCredentialsException(AuthErrorCode.INVALID_TOKEN.defaultMessage(), new JwtException("Invalid token"));
                    }
                }
            } catch (Exception e) {
                SecurityContextHolder.clearContext();
                request.setAttribute("exception", e);
            }
        }

        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            logger.error("Exception occurred during filter chain processing", e);
            throw e;
        }
    }
}
