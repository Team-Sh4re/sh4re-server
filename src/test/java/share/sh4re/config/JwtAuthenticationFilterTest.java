package share.sh4re.config;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;

public class JwtAuthenticationFilterTest {

    @Mock
    private JwtConfig jwtConfig;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtConfig);
        SecurityContextHolder.clearContext();

        // Mock the getRequestURI method to return a default value
        when(request.getRequestURI()).thenReturn("/api/some/endpoint");
    }

    @Test
    public void testDoFilterInternal_WithExpiredToken_ShouldSetExceptionAttribute() throws ServletException, IOException {
        // Arrange
        String token = "expired.token.value";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtConfig.extractUsername(token)).thenThrow(new ExpiredJwtException(null, null, "Token expired"));

        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> {
            jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        });

        verify(request).setAttribute(eq("exception"), any(ExpiredJwtException.class));
    }

    @Test
    public void testDoFilterInternal_WithInvalidToken_ShouldSetExceptionAttribute() throws ServletException, IOException {
        // Arrange
        String token = "invalid.token.value";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtConfig.extractUsername(token)).thenThrow(new JwtException("Invalid token"));

        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> {
            jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        });

        verify(request).setAttribute(eq("exception"), any(JwtException.class));
    }

    @Test
    public void testDoFilterInternal_WithValidToken_ShouldAuthenticate() throws ServletException, IOException {
        // Arrange
        String token = "valid.token.value";
        String username = "testuser";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtConfig.extractUsername(token)).thenReturn(username);
        when(jwtConfig.validateToken(token, username)).thenReturn(true);

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals(username, SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    public void testDoFilterInternal_WithNoToken_ShouldContinueFilterChain() throws ServletException, IOException {
        // Arrange
        when(request.getHeader("Authorization")).thenReturn(null);

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    public void testDoFilterInternal_WithRefreshTokenEndpoint_ShouldSkipAuthentication() throws ServletException, IOException {
        // Arrange
        when(request.getRequestURI()).thenReturn("/api/auth/refresh");

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(filterChain).doFilter(request, response);
        verify(request, never()).getHeader("Authorization");
    }
}
