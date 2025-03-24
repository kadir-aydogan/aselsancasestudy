package tr.com.aselsankadir.casestudy.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import tr.com.aselsankadir.casestudy.domain.common.Email;
import tr.com.aselsankadir.casestudy.domain.user.AselUser;
import tr.com.aselsankadir.casestudy.domain.user.IUserRepository;
import tr.com.aselsankadir.casestudy.domain.user.UserId;
import tr.com.aselsankadir.casestudy.infrastructure.AselUserMapper;
import tr.com.aselsankadir.casestudy.infrastructure.jpa.AselJpaUserRepository;
import tr.com.aselsankadir.casestudy.infrastructure.jpa.AselUserEntity;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements IUserRepository {

    private final AselJpaUserRepository jpaRepository;
    @Override
    public Optional<AselUser> findByEmail(Email email) {
        return jpaRepository.findByEmail(email.email())
                .map(AselUserMapper::toDomain);
    }

    @Override
    public UserId save(AselUser user) {
        AselUserEntity entity = AselUserMapper.toEntity(user);
        AselUserEntity saved = jpaRepository.save(entity);
        return UserId.restore(saved.getId());
    }
}
