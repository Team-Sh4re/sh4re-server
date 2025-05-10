package share.sh4re.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import share.sh4re.domain.User;

@Component
public class JwtConfig {
  private final SecretKey SECRET_KEY;
  private final long ACCESS_TOKEN_VALIDITY = 1000 * 60 * 15; // 15 mins
  private final long REFRESH_TOKEN_VALIDITY = 1000 * 60 * 60 * 24 * 7; // 7 days

  public JwtConfig(@Value("${jwt.secret-key}") String secretKey) {
    if (secretKey == null || secretKey.isEmpty()) {
      throw new IllegalArgumentException("JWT Secret key cannot be null or empty");
    }
    this.SECRET_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
  }

  // Extract username from token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract expiration date from token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Extract any claim from token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extract all claims from token
    private Claims extractAllClaims(String token) {
      return Jwts.parser()
          .verifyWith(SECRET_KEY)
          .build()
          .parseSignedClaims(token)
          .getPayload();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("name", user.getName());
        return createToken(claims, user.getName(), ACCESS_TOKEN_VALIDITY);
    }

    public String generateRefreshToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        return createToken(claims, user.getName(), REFRESH_TOKEN_VALIDITY);
    }

    private String createToken(Map<String, Object> claims, String subject, long validity) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + validity))
                .signWith(SECRET_KEY)
                .compact();
    }

    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    public Boolean validateRefreshToken(String token) {
          return !isTokenExpired(token);
    }

    public String generateTokenFromRefreshToken(String refreshToken) {
        String username = extractUsername(refreshToken);
        Long userId = extractClaim(refreshToken, claims -> claims.get("id", Long.class));

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userId);
        claims.put("name", username);

        return createToken(claims, username, ACCESS_TOKEN_VALIDITY);
    }
}
