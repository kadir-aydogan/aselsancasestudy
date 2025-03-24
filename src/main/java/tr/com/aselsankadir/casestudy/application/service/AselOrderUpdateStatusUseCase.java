package tr.com.aselsankadir.casestudy.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.com.aselsankadir.casestudy.domain.common.AselRuntimeException;
import tr.com.aselsankadir.casestudy.domain.common.DomainEvent;
import tr.com.aselsankadir.casestudy.domain.common.IAselLogger;
import tr.com.aselsankadir.casestudy.domain.common.IDomainEventPublisher;
import tr.com.aselsankadir.casestudy.domain.order.AselOrder;
import tr.com.aselsankadir.casestudy.domain.order.AselOrderStatus;
import tr.com.aselsankadir.casestudy.domain.order.IAselOrderRepository;
import tr.com.aselsankadir.casestudy.domain.order.OrderId;
import tr.com.aselsankadir.casestudy.domain.restaurant.RestaurantId;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AselOrderUpdateStatusUseCase {

    private final IAselOrderRepository orderRepository;

    private final IDomainEventPublisher domainEventPublisher;

    @Transactional
    public int updateStatus(RestaurantId restaurantId, OrderId orderId, AselOrderStatus status) {

        AselOrder order = orderRepository.findByRestaurantId(restaurantId, orderId)
                .orElseThrow(() -> new AselRuntimeException("Sipariş bulunamadı."));

        order.changeStatus(status);

        int updated = orderRepository.updateStatus(restaurantId, orderId, status);

        if (updated < 0) {
            return updated;
        }

        List<DomainEvent> events = order.getEvents();

        domainEventPublisher.publishAll(events);

        return updated;
    }
}
