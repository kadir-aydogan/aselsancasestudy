package tr.com.aselsankadir.casestudy.domain.user;

import tr.com.aselsankadir.casestudy.domain.common.AselRuntimeException;

import java.util.Date;

public record JwtToken(String token, Date expireDate) {
    public JwtToken {
        if (token == null || expireDate == null || expireDate.before(new Date())) {
            throw new AselRuntimeException("Invalid Jwt Token");
        }
    }
}
