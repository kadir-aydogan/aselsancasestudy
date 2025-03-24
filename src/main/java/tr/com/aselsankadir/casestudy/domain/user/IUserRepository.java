package tr.com.aselsankadir.casestudy.domain.user;

import tr.com.aselsankadir.casestudy.domain.common.Email;

import java.util.Optional;

public interface IUserRepository {
    Optional<AselUser> findByEmail(Email email);

    UserId save(AselUser user);
}
