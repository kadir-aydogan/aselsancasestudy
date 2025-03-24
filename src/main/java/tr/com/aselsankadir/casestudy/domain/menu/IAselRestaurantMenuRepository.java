package tr.com.aselsankadir.casestudy.domain.menu;

import tr.com.aselsankadir.casestudy.domain.restaurant.RestaurantId;

import java.time.LocalDate;
import java.util.Optional;

public interface IAselRestaurantMenuRepository {
    Optional<AselRestaurantMenu> findAllByDate(RestaurantId restaurantId, LocalDate date);
}
