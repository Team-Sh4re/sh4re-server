package share.sh4re.config;

import org.springframework.context.annotation.Configuration;

/**
 * Configuration class that defines security path patterns for the application.
 * This class centralizes the management of which endpoints require authentication
 * and which ones are publicly accessible.
 */
@Configuration
public class SecurityPathConfig {

    /**
     * Array of endpoint patterns that are accessible without authentication.
     */
    private static final String[] PUBLIC_ENDPOINTS = {
        "/api/auth/signup",
        "/api/auth/signin",
        "/api/auth/refresh",
        "/users/{userId}"
    };

    /**
     * Returns the array of endpoint patterns that are accessible without authentication.
     *
     * @return Array of public endpoint patterns
     */
    public String[] getPublicEndpoints() {
        return PUBLIC_ENDPOINTS;
    }
}