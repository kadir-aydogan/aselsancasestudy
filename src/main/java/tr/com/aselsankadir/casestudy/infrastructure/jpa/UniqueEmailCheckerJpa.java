package tr.com.aselsankadir.casestudy.infrastructure.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tr.com.aselsankadir.casestudy.domain.common.Email;
import tr.com.aselsankadir.casestudy.domain.user.IUserRepository;
import tr.com.aselsankadir.casestudy.domain.user.UniqueEmailChecker;

@Component
@RequiredArgsConstructor
public class UniqueEmailCheckerJpa implements UniqueEmailChecker {

    private final IUserRepository userRepository;

    @Override
    public boolean isEmailAvailable(Email email) {
        return userRepository.findByEmail(email).isEmpty();
    }
}

