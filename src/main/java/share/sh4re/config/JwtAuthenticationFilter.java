package share.sh4re.config;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import share.sh4re.exceptions.errorcode.AuthErrorCode;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtConfig jwtConfig;
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private final List<SecurityPathConfig.EndpointConfig> publicUrls;

    protected boolean isPublicURL(HttpServletRequest request) {
        AntPathMatcher pathMatcher = new AntPathMatcher();
        String requestURI = request.getRequestURI();
        HttpMethod requestMethod;
        try {
            requestMethod = HttpMethod.valueOf(request.getMethod());
        } catch (IllegalArgumentException e) {
            return false;
        }

        for (SecurityPathConfig.EndpointConfig endpoint : publicUrls) {
            if (pathMatcher.match(endpoint.getPattern(), requestURI)) {
                HttpMethod method = endpoint.getMethod();
                if (method == null || method.equals(requestMethod)) {
                    return true;
                }
            }
        }
        return false;
    }

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

                // Re-throw BadCredentialsException for testing purposes
                if (e instanceof BadCredentialsException) {
                    throw (BadCredentialsException) e;
                } else if (e instanceof JwtException || e instanceof ExpiredJwtException) {
                    throw new BadCredentialsException(AuthErrorCode.INVALID_TOKEN.defaultMessage(), e);
                }
            }
        } else {
            if(!isPublicURL(request)){
                throw new BadCredentialsException(AuthErrorCode.INVALID_TOKEN.defaultMessage(), new JwtException("Invalid token"));
            }
            SecurityContextHolder.clearContext();
        }

        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            logger.error("Exception occurred during filter chain processing", e);
            throw e;
        }
    }
}
