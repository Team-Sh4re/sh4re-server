package share.sh4re.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

/**
 * Configuration class that defines security path patterns for the application.
 * This class centralizes the management of which endpoints require authentication
 * and which ones are publicly accessible.
 */
@Configuration
public class SecurityPathConfig {

    /**
     * Class representing an endpoint with its HTTP method.
     */
    public static class EndpointConfig {
        private final String pattern;
        private final HttpMethod method;

        public EndpointConfig(String pattern, HttpMethod method) {
            this.pattern = pattern;
            this.method = method;
        }

        public EndpointConfig(String pattern) {
            this.pattern = pattern;
            this.method = null;
        }

        public String getPattern() {
            return pattern;
        }

        public HttpMethod getMethod() {
            return method;
        }
    }

    /**
     * Array of endpoint patterns that are accessible without authentication.
     * For endpoints where the HTTP method is not specified, all methods are allowed.
     */
    private static final EndpointConfig[] PUBLIC_ENDPOINTS = {
        new EndpointConfig("/api/auth/signup"),
        new EndpointConfig("/api/auth/signin"),
        new EndpointConfig("/api/auth/refresh"),
        new EndpointConfig("/users/{userId}", HttpMethod.GET),
        new EndpointConfig("/codes", HttpMethod.GET),
        new EndpointConfig("/codes/{codeId}", HttpMethod.GET)
    };

    /**
     * Returns the array of endpoint configurations that are accessible without authentication.
     *
     * @return Array of public endpoint configurations
     */
    public EndpointConfig[] getPublicEndpoints() {
        return PUBLIC_ENDPOINTS;
    }
}
