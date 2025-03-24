package tr.com.aselsankadir.casestudy.application.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tr.com.aselsankadir.casestudy.domain.common.AselDomainException;
import tr.com.aselsankadir.casestudy.domain.common.Email;
import tr.com.aselsankadir.casestudy.domain.common.PasswordHasher;
import tr.com.aselsankadir.casestudy.domain.common.TokenProvider;
import tr.com.aselsankadir.casestudy.domain.user.*;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest  {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private PasswordHasher passwordHasher;

    @Mock
    private TokenProvider tokenProvider;

    @Mock
    private UniqueEmailChecker uniqueEmailChecker;

    @InjectMocks
    private AuthenticationService authenticationService;

    private final Email email = new Email("kadir@test.com");
    private final String rawPassword = "secure123";
    private final String hashedPassword = "hashed-123";
    private final Role role = Role.ROLE_CUSTOMER;

    private final AselUser existingUser = AselUser.restore(
            UserId.restore(1L),
            email,
            hashedPassword,
            role,
            null);


    @Test
    void should_register_successfully() {

        given(uniqueEmailChecker.isEmailAvailable(email)).willReturn(true);
        given(passwordHasher.hash(rawPassword)).willReturn(hashedPassword);

        authenticationService.register(email, rawPassword, role);

        then(userRepository).should().save(any(AselUser.class));
    }

    @Test
    void should_throw_exception_when_user_already_exists() {

        given(uniqueEmailChecker.isEmailAvailable(email)).willReturn(false);

        assertThrows(AselDomainException.class, () ->
                authenticationService.register(email, rawPassword, role)
        );
    }

    @Test
    void should_login_successfully() {

        given(userRepository.findByEmail(email)).willReturn(Optional.of(existingUser));
        given(passwordHasher.matches(rawPassword, hashedPassword)).willReturn(true);
        given(tokenProvider.generateToken(existingUser)).willReturn(new JwtToken("token", new Date()));

        JwtToken token = authenticationService.login(email, rawPassword);

        Assertions.assertThat(token).isNotNull();
        Assertions.assertThat(token.token()).isEqualTo("token");
    }

    @Test
    void should_throw_exception_when_user_not_found() {
        given(userRepository.findByEmail(email)).willReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () ->
                authenticationService.login(email, rawPassword)
        );
    }

    @Test
    void should_throw_exception_when_password_incorrect() {
        given(userRepository.findByEmail(email)).willReturn(Optional.of(existingUser));
        given(passwordHasher.matches(rawPassword, hashedPassword)).willReturn(false);

        assertThrows(AselBadCredentialsException.class, () ->
                authenticationService.login(email, rawPassword)
        );
    }
}
