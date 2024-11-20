package spring.security.practice.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class JwtService {

    private static final String SECRET = "0055F9967C06B0CEE8A2C452C16792CC52D854AA6C343F1E9CA58942DA57AB2C804FC80837BD4FF60A211859D1EC54009470FC1241248EA1B2B79CEC08BBA252";
    private static final long JWT_EXPIRATION = TimeUnit.MINUTES.toMillis(60);
    private static final long REFRESH_EXPIRATION = TimeUnit.HOURS.toMillis(24);

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "access");

        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusMillis(JWT_EXPIRATION)))
                .signWith(generateKey())
                .compact();
    }

    public String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "refresh");

        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusMillis(REFRESH_EXPIRATION)))
                .signWith(generateKey())
                .compact();
    }

    public String extractUsername(String jwt) {
        Claims claims = getClaims(jwt);
        return claims.getSubject();
    }

    public long expiredAt(String jwt) {
        // Ambil klaim dari token
        Claims claims = getClaims(jwt);

        // Waktu expired dari token
        Date expiration = claims.getExpiration();

        // Waktu saat ini dalam milidetik
        long nowMillis = Instant.now().toEpochMilli();

        // Waktu expired dalam milidetik
        long expirationMillis = expiration.getTime();

        // Hitung sisa waktu
        long remainingMillis = Math.max(0, expirationMillis - nowMillis);

        // Konversi sisa waktu dari milidetik ke menit
        return remainingMillis / 1000 / 60;
    }

    public boolean isTokenValid(String jwt) {
        Claims claims = getClaims(jwt);
        return claims.getExpiration().after(Date.from(Instant.now()));
    }

    private SecretKey generateKey() {
        byte[] decodedKey = Base64.getDecoder().decode(SECRET);
        return Keys.hmacShaKeyFor(decodedKey);
    }

    private Claims getClaims(String jwt) {
        return Jwts.parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }
}
