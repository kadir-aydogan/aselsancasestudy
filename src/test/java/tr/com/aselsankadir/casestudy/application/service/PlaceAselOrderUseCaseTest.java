package tr.com.aselsankadir.casestudy.application.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import tr.com.aselsankadir.casestudy.domain.common.IDomainEventPublisher;
import tr.com.aselsankadir.casestudy.domain.dish.AselDishNotFoundException;
import tr.com.aselsankadir.casestudy.domain.dish.DishId;
import tr.com.aselsankadir.casestudy.domain.menu.MenuId;
import tr.com.aselsankadir.casestudy.domain.menuitem.AselMenuItem;
import tr.com.aselsankadir.casestudy.domain.menuitem.MenuItemId;
import tr.com.aselsankadir.casestudy.domain.order.AselOrder;
import tr.com.aselsankadir.casestudy.domain.order.IAselOrderRepository;
import tr.com.aselsankadir.casestudy.domain.order.OrderId;
import tr.com.aselsankadir.casestudy.domain.order.PlaceOrderCommand;
import tr.com.aselsankadir.casestudy.domain.orderitem.AselOrderItem;
import tr.com.aselsankadir.casestudy.domain.orderitem.CreateOrderItemCommand;
import tr.com.aselsankadir.casestudy.domain.restaurant.RestaurantId;
import tr.com.aselsankadir.casestudy.domain.user.UserId;
import tr.com.aselsankadir.casestudy.infrastructure.jpa.AselMenuItemEntity;
import tr.com.aselsankadir.casestudy.presentation.dto.CreateAselOrderRequest;
import tr.com.aselsankadir.casestudy.presentation.dto.CreateMenuItemRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

public class PlaceAselOrderUseCaseTest {

    @Mock
    private IAselOrderRepository orderRepository;
    @Mock private AselMenuItemService menuItemService;
    @Mock private AselOrderItemService orderItemService;
    @Mock
    private IDomainEventPublisher eventPublisher;

    @InjectMocks
    private PlaceAselOrderUseCase useCase;

    private final RestaurantId restaurantId = new RestaurantId(99L);

    private final MenuId menuId = new MenuId(54L);
    private final UserId userId = UserId.create();
    private final MenuItemId menuItemId = new MenuItemId(10L);

    private final DishId dishId = new DishId(86L);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void should_create_order_and_publish_events() {

        CreateOrderItemCommand itemReq = new CreateOrderItemCommand(menuItemId, 2);
        PlaceOrderCommand command = new PlaceOrderCommand(userId, restaurantId, List.of(itemReq));

        AselMenuItem menuItem = new AselMenuItem(menuItemId,
                                                BigDecimal.TEN,
                                                restaurantId,
                                                dishId,
                                                menuId);

        when(menuItemService.findByRestaurantId(restaurantId, menuItemId))
                .thenReturn(Optional.of(menuItem));

        ArgumentCaptor<AselOrder> orderCaptor = ArgumentCaptor.forClass(AselOrder.class);

        // Act
        OrderId orderId = useCase.createOrder(command);

        // Assert
        Mockito.verify(orderRepository).insert(orderCaptor.capture());

        AselOrder insertedOrder = orderCaptor.getValue();

        Assertions.assertThat(insertedOrder.getCustomerId()).isEqualTo(userId);
        Assertions.assertThat(insertedOrder.getItems()).hasSize(1);
        Assertions.assertThat(insertedOrder.getTotalAmount()).isEqualTo(BigDecimal.valueOf(20));

        Mockito.verify(orderItemService, Mockito.times(1)).insert(ArgumentMatchers.any(AselOrderItem.class));

        Mockito.verify(eventPublisher).publishAll(ArgumentMatchers.anyList());
    }

    @Test
    void should_throw_when_menu_item_not_found() {
        // Arrange
        CreateOrderItemCommand itemReq = new CreateOrderItemCommand(menuItemId, 1);
        PlaceOrderCommand request = new PlaceOrderCommand(userId, restaurantId, List.of(itemReq));

        when(menuItemService.findByRestaurantId(restaurantId, menuItemId))
                .thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThatThrownBy(() -> useCase.createOrder(request))
                .isInstanceOf(AselDishNotFoundException.class);

        Mockito.verifyNoInteractions(orderRepository, orderItemService, eventPublisher);
    }
}

