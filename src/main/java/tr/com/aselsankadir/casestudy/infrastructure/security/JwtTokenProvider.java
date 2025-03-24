package tr.com.aselsankadir.casestudy.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import tr.com.aselsankadir.casestudy.domain.common.TokenProvider;
import tr.com.aselsankadir.casestudy.domain.user.AselUser;
import tr.com.aselsankadir.casestudy.domain.user.JwtToken;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenProvider implements TokenProvider {

    private static final String SECRET_KEY = "c6f63e82028f68de9686eaa76242c5b17cd0af119e0ca05aef66df2b4dbf75a937146e77add1336acd6c8074070dbbd233c469857619cffdfa197769f728886f4e1ee476ee40ecbde75b8bb3e85cd17c22d58dc6427a9837703a57760e035071f086e9c25afa6c3fbe53040cb6d2e410a199385178ebbe59d064f4f9dec6e049";

    private static final long EXPIRATION_MILLIS = 1000 * 60 * 30 * 120;

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public JwtToken generateToken(AselUser user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole().name());

        Date expiration = new Date(System.currentTimeMillis() + EXPIRATION_MILLIS);
        String token = buildToken(claims, user.getEmail().email(), expiration);

        return new JwtToken(token, expiration);
    }

    @Override
    public boolean isTokenValid(String token, String expectedUsername) {
        String actualUsername = extractUsername(token);
        return actualUsername.equals(expectedUsername) && !isTokenExpired(token);
    }

    @Override
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // ----- Internal helpers -----

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private String buildToken(Map<String, Object> claims, String subject, Date expiration) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date())
                .expiration(expiration)
                .signWith(getSigningKey())
                .compact();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
