package az.azerenerji.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class TokenService {
    private static final Logger LOG = LogManager.getLogger(TokenService.class);


    @Value("${app.jwt-secret}")
    private String jwtSecretKey;

    @Value("${app.jwt-expiration-milliseconds}")
    private int expirationDate;

   // private final RedisTemplate<String, String> redisTemplate;

    //public TokenService(RedisTemplate<String, String> redisTemplate) {
       // this.redisTemplate = redisTemplate;
   // }

    public String generateJwtToken(Authentication authentication) {
        String email = authentication.getName();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationDate);
        SecretKey key = Keys.hmacShaKeyFor(jwtSecretKey.getBytes());
        return Jwts.builder()
                .setSubject(email)
                .claim("authorities", authentication.getAuthorities())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    @Transactional
    public String generateTokens(Map<String, Object> claims) {
        long currentTimeMillis = System.currentTimeMillis();
        String token = Jwts.builder().
                setClaims(claims)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date(currentTimeMillis))
                .signWith(getSignInKey())
                .setExpiration(DateUtils.addMinutes(new Date(currentTimeMillis),
                        expirationDate)).compact();

        log.info("Generated token: " + token);
        return token;
    }

   // public void saveAccessToken(String uuid, String refreshToken) {
      //  redisTemplate.opsForValue().set(uuid, refreshToken, expirationDate, TimeUnit.MILLISECONDS);
    //}
    private JwtParser createParser() {
        return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(jwtSecretKey.getBytes())).build();
    }

    public Claims validateJwtToken(String authToken) {
        try {
            Jws<Claims> claimsJws = createParser().parseClaimsJws(authToken);
            return claimsJws.getBody();
        } catch (SignatureException e) {
            LOG.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            LOG.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            LOG.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            LOG.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            LOG.error("JWT claims string is empty: {}", e.getMessage());
        }
        return null;
    }

    public Claims validateAndExtractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getJWTFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public Key getSignInKey() {
        byte[] secret = jwtSecretKey.getBytes();
        Key signInKey = Keys.hmacShaKeyFor(secret);
        log.info("Generated sign-in key: " + signInKey);
        return signInKey;

    }
}
