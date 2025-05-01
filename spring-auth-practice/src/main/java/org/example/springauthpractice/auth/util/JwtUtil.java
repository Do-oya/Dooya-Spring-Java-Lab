package org.example.springauthpractice.auth.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.example.springauthpractice.common.exception.CustomException;
import org.example.springauthpractice.common.exception.ErrorCode;
import org.example.springauthpractice.user.domain.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    private SecretKey secretKey;

    @Value("${jwt.secret-key}")
    private String secret;

    @Value("${jwt.valid-time}")
    private long TOKEN_VALID_TIME;

    @PostConstruct
    private void init() {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String createAccessToken(Long userId, String email, Role role) {
        Date timeNow = new Date(System.currentTimeMillis());
        Date expirationTime = new Date(timeNow.getTime() + TOKEN_VALID_TIME);

        return Jwts.builder()
                .claim("userId", userId)
                .claim("email", email)
                .claim("role", role.getRoleSecurity())
                .setIssuedAt(timeNow)
                .setExpiration(expirationTime)
                .signWith(secretKey)
                .compact();
    }

    public Long getUserId(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build()
                .parseClaimsJws(token).getBody().get("userId", Long.class);
    }

    public String getUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build()
                .parseClaimsJws(token).getBody().get("email", String.class);
    }

    public String getRole(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build()
                .parseClaimsJws(token).getBody().get("role", String.class);
    }

    public boolean validateToken(String token) {
        return valid(secretKey, token);
    }

    private boolean valid(SecretKey secretKey, String token) {
        if (token == null) {
            throw new CustomException(ErrorCode.MISSING_TOKEN);
        }
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build()
                    .parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (SignatureException ex) {
            throw new CustomException(ErrorCode.WRONG_TYPE_TOKEN);
        } catch (MalformedJwtException ex) {
            throw new CustomException(ErrorCode.UNSUPPORTED_TOKEN);
        } catch (ExpiredJwtException ex) {
            throw new CustomException(ErrorCode.EXPIRED_TOKEN);
        } catch (IllegalArgumentException ex) {
            throw new CustomException(ErrorCode.UNKNOWN_TOKEN_ERROR);
        }
    }
}
