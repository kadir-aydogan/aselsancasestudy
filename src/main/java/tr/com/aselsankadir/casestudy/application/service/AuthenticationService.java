package tr.com.aselsankadir.casestudy.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tr.com.aselsankadir.casestudy.domain.common.AuthenticationUseCase;
import tr.com.aselsankadir.casestudy.domain.common.Email;
import tr.com.aselsankadir.casestudy.domain.common.PasswordHasher;
import tr.com.aselsankadir.casestudy.domain.common.TokenProvider;
import tr.com.aselsankadir.casestudy.domain.user.*;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements AuthenticationUseCase {

    private final IUserRepository userRepository;

    private final PasswordHasher passwordHasher;

    private final TokenProvider tokenProvider;

    private final UniqueEmailChecker uniqueEmailChecker;

    public void register(Email email, String password, Role role) {

        AselUser user = AselUser.create(email, password, role, passwordHasher, uniqueEmailChecker);

        userRepository.save(user);
    }

    @Override
    public JwtToken login(Email email, String password) {
        AselUser user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        if (!user.authenticate(passwordHasher, password)) {
            throw new AselBadCredentialsException();
        }

        return tokenProvider.generateToken(user);
    }

}
