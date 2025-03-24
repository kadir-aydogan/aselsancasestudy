package tr.com.aselsankadir.casestudy.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.com.aselsankadir.casestudy.domain.common.DomainEvent;
import tr.com.aselsankadir.casestudy.domain.common.IDomainEventPublisher;
import tr.com.aselsankadir.casestudy.domain.dish.AselDishNotFoundException;
import tr.com.aselsankadir.casestudy.domain.menuitem.AselMenuItem;
import tr.com.aselsankadir.casestudy.domain.order.AselOrder;
import tr.com.aselsankadir.casestudy.domain.order.IAselOrderRepository;
import tr.com.aselsankadir.casestudy.domain.order.OrderId;
import tr.com.aselsankadir.casestudy.domain.order.PlaceOrderCommand;
import tr.com.aselsankadir.casestudy.domain.orderitem.AselOrderItem;
import tr.com.aselsankadir.casestudy.domain.orderitem.CreateOrderItemCommand;
import tr.com.aselsankadir.casestudy.domain.restaurant.RestaurantId;
import tr.com.aselsankadir.casestudy.domain.user.UserId;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceAselOrderUseCase {

    private final IAselOrderRepository orderRepository;
    private final AselMenuItemService aselMenuItemService;
    private final AselOrderItemService orderItemService;
    private final IDomainEventPublisher domainEventPublisher;


    @Transactional
    public OrderId createOrder(PlaceOrderCommand command) {

        RestaurantId restaurantId = command.getRestaurantId();
        UserId userId = command.getUserId();

        List<AselOrderItem> domainOrderItems = new ArrayList<>();

        for (CreateOrderItemCommand orderItemCommand : command.getDishIds()) {

            AselMenuItem menuItem = aselMenuItemService
                    .findByRestaurantId(restaurantId, orderItemCommand.getMenuItemId())
                    .orElseThrow(AselDishNotFoundException::new);

            BigDecimal totalItemAmount = menuItem.getAmount().multiply(
                    BigDecimal.valueOf(orderItemCommand.getQuantity()));

            AselOrderItem orderItem = AselOrderItem.create(
                    menuItem.getId(),
                    restaurantId,
                    orderItemCommand.getQuantity(),
                    totalItemAmount
            );

            domainOrderItems.add(orderItem);
        }

        AselOrder aselOrder = AselOrder.createOrder(
                userId,
                restaurantId,
                domainOrderItems
        );

        insert(aselOrder);

        List<DomainEvent> events = aselOrder.getEvents();

        domainEventPublisher.publishAll(events);

        aselOrder.clearEvents();

        return aselOrder.getId();
    }

    private AselOrder insert(AselOrder order) {

        orderRepository.insert(order);

        order.getItems().forEach(orderItemService::insert);

        return order;
    }
}

