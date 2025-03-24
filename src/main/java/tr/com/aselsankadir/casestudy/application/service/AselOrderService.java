package tr.com.aselsankadir.casestudy.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tr.com.aselsankadir.casestudy.domain.common.AselRuntimeException;
import tr.com.aselsankadir.casestudy.domain.order.AselOrderDetail;
import tr.com.aselsankadir.casestudy.domain.order.AselOrderItemDetail;
import tr.com.aselsankadir.casestudy.domain.order.IAselOrderDetailRepository;
import tr.com.aselsankadir.casestudy.domain.order.OrderId;
import tr.com.aselsankadir.casestudy.domain.restaurant.RestaurantId;
import tr.com.aselsankadir.casestudy.domain.user.UserId;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AselOrderService {

    private final AselOrderItemService orderItemService;

    private final IAselOrderDetailRepository aselOrderDetailRepository;

    public AselOrderDetail getOrderForRestaurant(RestaurantId restaurantId, OrderId orderId) {

        AselOrderDetail detail = aselOrderDetailRepository.findByRestaurantId(restaurantId, orderId)
                .orElseThrow(() -> new AselRuntimeException("Sipariş bulunamadı!"));

        List<AselOrderItemDetail> items = orderItemService.findAllByOrderId(restaurantId, orderId);

        detail.setItems(items);

        return detail;
    }

    public List<AselOrderDetail> getOrdersForCustomer(UserId customerId) {
        return aselOrderDetailRepository.findAllByCustomerId(customerId);
    }

    public List<AselOrderDetail> getOrdersForRestaurant(RestaurantId customerId) {
        return aselOrderDetailRepository.findAllByRestaurantId(customerId);
    }

    public AselOrderDetail getOrderForCustomer(UserId userId, OrderId orderId) {

        AselOrderDetail detail = aselOrderDetailRepository.findByCustomerId(userId, orderId)
                .orElseThrow(() -> new AselRuntimeException("Sipariş bulunamadı!"));

        List<AselOrderItemDetail> items = orderItemService.findAllByOrderId(orderId);

        detail.setItems(items);

        return detail;
    }
}
