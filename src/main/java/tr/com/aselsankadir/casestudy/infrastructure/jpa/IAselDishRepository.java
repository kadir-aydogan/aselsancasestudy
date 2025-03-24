package tr.com.aselsankadir.casestudy.infrastructure.jpa;

import java.util.Optional;

public interface IAselDishRepository {
    Optional<AselDishEntity> findById(Long restaurantId, Long dishId);

}
