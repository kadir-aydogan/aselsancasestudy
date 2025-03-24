package tr.com.aselsankadir.casestudy.domain.order;

import tr.com.aselsankadir.casestudy.domain.restaurant.RestaurantId;
import tr.com.aselsankadir.casestudy.domain.user.UserId;

import java.util.List;
import java.util.Optional;

public interface IAselOrderDetailRepository {
    Optional<AselOrderDetail> findByCustomerId(UserId customerId, OrderId orderId);

    Optional<AselOrderDetail> findByRestaurantId(RestaurantId restaurantId, OrderId orderId);

    List<AselOrderDetail> findAllByCustomerId(UserId userId);

    List<AselOrderDetail> findAllByRestaurantId(RestaurantId restaurantId);

}
