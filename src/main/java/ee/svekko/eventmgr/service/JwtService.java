package ee.svekko.eventmgr.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

@Slf4j
@Service
public class JwtService {
    @Value("${jwt.generator.secret}")
    private String secret;

    private SecretKey getSigningKey() {
        return new SecretKeySpec(secret.getBytes(), "HmacSHA256");
    }

    public String generateToken(String username) {
        return Jwts.builder()
            .signWith(getSigningKey())
            .subject(username)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + (1000 * 60 * 60)))
            .compact();
    }

    public boolean isTokenValid(String token) {
        return new Date().before(getTokenClaims(token).getExpiration());
    }

    public String getUsername(String token) {
        return getTokenClaims(token).getSubject();
    }

    private Claims getTokenClaims(String token) {
        return Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }
}
