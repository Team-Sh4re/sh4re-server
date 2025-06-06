package share.sh4re.config;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class WebSecurityConfig {

    private final JwtConfig jwtConfig;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final SecurityPathConfig securityPathConfig;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(
            jwtConfig,
            securityPathConfig.getPublicEndpoints(),
            customAuthenticationEntryPoint
        );
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorize -> {
                for(SecurityPathConfig.EndpointConfig endpoint : securityPathConfig.getPublicEndpoints()) {
                    if(endpoint.getMethod() != null) {
                        authorize.requestMatchers(endpoint.getMethod(), endpoint.getPattern()).permitAll();
                    } else {
                        authorize.requestMatchers(endpoint.getPattern()).permitAll();
                    }
                }
                authorize.anyRequest().authenticated();
            })
            .exceptionHandling(exceptions ->
                exceptions.authenticationEntryPoint(customAuthenticationEntryPoint)
            )
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
