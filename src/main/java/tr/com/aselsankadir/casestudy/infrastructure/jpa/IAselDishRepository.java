package tr.com.aselsankadir.casestudy.infrastructure.jpa;

import tr.com.aselsankadir.casestudy.infrastructure.jpa.AselDishEntity;

import java.util.Optional;

public interface IAselDishRepository {
    Optional<AselDishEntity> findById(Long restaurantId, Long dishId);

}
