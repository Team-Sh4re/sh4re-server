package share.sh4re.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtConfig jwtConfig;
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private final List<SecurityPathConfig.EndpointConfig> publicUrls;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String token = jwtConfig.resolveToken(request);

        if (token != null) {
            return false;
        }

        String path = request.getRequestURI();
        String method = request.getMethod();

        return publicUrls.stream()
            .anyMatch(endpoint ->
                endpoint.getPattern().equals(path) &&
                    (endpoint.getMethod() == null || endpoint.getMethod().name().equals(method))
            );
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        // Skip authentication for refresh token endpoint
        if (request.getRequestURI().equals("/api/auth/refresh")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = jwtConfig.resolveToken(request);
        try {
            String username = jwtConfig.extractUsername(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                if (jwtConfig.validateToken(token, username)) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            request.setAttribute("exception", e);

            customAuthenticationEntryPoint.commence(request, response,
                new BadCredentialsException("Expired or invalid token", e));
            return;
        }

        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            logger.error("Exception occurred during filter chain processing", e);
            throw e;
        }
    }
}
