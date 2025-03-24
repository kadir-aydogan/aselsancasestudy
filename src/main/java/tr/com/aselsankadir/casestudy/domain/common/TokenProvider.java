package tr.com.aselsankadir.casestudy.domain.common;

import tr.com.aselsankadir.casestudy.domain.user.AselUser;
import tr.com.aselsankadir.casestudy.domain.user.JwtToken;

public interface TokenProvider {
    String extractUsername(String token);
    JwtToken generateToken(AselUser user);
    boolean isTokenExpired(String token);
    boolean isTokenValid(String token, String expectedUsername);

}
