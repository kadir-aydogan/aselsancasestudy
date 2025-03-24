package tr.com.aselsankadir.casestudy.infrastructure.security;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import tr.com.aselsankadir.casestudy.domain.common.PasswordHasher;

@Component
public class BcryptPasswordHasher implements PasswordHasher {
    public boolean matches(String raw, String hashed) {
        return BCrypt.checkpw(raw, hashed);
    }

    @Override
    public String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
