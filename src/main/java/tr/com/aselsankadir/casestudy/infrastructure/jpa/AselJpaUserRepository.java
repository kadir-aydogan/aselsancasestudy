package tr.com.aselsankadir.casestudy.infrastructure.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AselJpaUserRepository extends JpaRepository<AselUserEntity, Long> {
    Optional<AselUserEntity> findByEmail(String email);
}
