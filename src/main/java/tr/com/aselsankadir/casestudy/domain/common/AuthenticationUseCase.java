package tr.com.aselsankadir.casestudy.domain.common;

import tr.com.aselsankadir.casestudy.domain.user.JwtToken;
import tr.com.aselsankadir.casestudy.domain.user.Role;

public interface AuthenticationUseCase {
    void register(Email email, String password, Role role);
    JwtToken login(Email email, String password);
}
