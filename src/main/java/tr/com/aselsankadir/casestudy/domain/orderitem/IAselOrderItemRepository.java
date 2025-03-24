package tr.com.aselsankadir.casestudy.domain.orderitem;

import tr.com.aselsankadir.casestudy.domain.order.AselOrderItemDetail;
import tr.com.aselsankadir.casestudy.domain.order.OrderId;
import tr.com.aselsankadir.casestudy.domain.restaurant.RestaurantId;

import java.util.List;

public interface IAselOrderItemRepository {
    List<AselOrderItemDetail> findAllByOrderId(RestaurantId restaurantId, OrderId orderId);

    List<AselOrderItemDetail> findAllByOrderId(OrderId orderId);

    Long insert(AselOrderItem orderItem);
}
