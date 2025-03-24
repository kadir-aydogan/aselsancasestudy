package tr.com.aselsankadir.casestudy.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tr.com.aselsankadir.casestudy.domain.order.AselOrderItemDetail;
import tr.com.aselsankadir.casestudy.domain.order.OrderId;
import tr.com.aselsankadir.casestudy.domain.orderitem.AselOrderItem;
import tr.com.aselsankadir.casestudy.domain.orderitem.IAselOrderItemRepository;
import tr.com.aselsankadir.casestudy.domain.restaurant.RestaurantId;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AselOrderItemService {

    private final IAselOrderItemRepository orderItemRepository;

    public List<AselOrderItemDetail> findAllByOrderId(RestaurantId restaurantId, OrderId orderId) {
        return orderItemRepository.findAllByOrderId(restaurantId, orderId);
    }

    public List<AselOrderItemDetail> findAllByOrderId(OrderId orderId) {
        return orderItemRepository.findAllByOrderId(orderId);
    }

    public Long insert(AselOrderItem orderItem) {
        return orderItemRepository.insert(orderItem);
    }
}
