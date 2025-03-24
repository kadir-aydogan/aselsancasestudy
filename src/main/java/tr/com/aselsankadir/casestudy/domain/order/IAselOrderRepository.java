package tr.com.aselsankadir.casestudy.domain.order;

import tr.com.aselsankadir.casestudy.domain.restaurant.RestaurantId;
import tr.com.aselsankadir.casestudy.domain.user.UserId;

import java.util.List;
import java.util.Optional;

public interface IAselOrderRepository {

    List<AselOrder> findAllByCustomerId(UserId customerId);

    List<AselOrder> findAllByRestaurantId(RestaurantId restaurantId);

    int updateStatus(RestaurantId id, OrderId orderId, AselOrderStatus status);

    OrderId insert(AselOrder order);

    Optional<AselOrder> findByCustomerId(UserId customerId, OrderId orderId);

    Optional<AselOrder> findByRestaurantId(RestaurantId restaurantId, OrderId orderId);

}
