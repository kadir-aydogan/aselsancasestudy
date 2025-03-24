package tr.com.aselsankadir.casestudy.domain.order;

import org.junit.jupiter.api.Test;
import tr.com.aselsankadir.casestudy.domain.common.AselDomainException;
import tr.com.aselsankadir.casestudy.domain.common.DomainEvent;
import tr.com.aselsankadir.casestudy.domain.menuitem.MenuItemId;
import tr.com.aselsankadir.casestudy.domain.orderitem.AselOrderItem;
import tr.com.aselsankadir.casestudy.domain.restaurant.RestaurantId;
import tr.com.aselsankadir.casestudy.domain.user.UserId;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AselOrderTest {

    @Test
    void should_create_order_with_items_and_add_event() {
        // given
        UserId customerId = UserId.create();
        RestaurantId restaurantId = new RestaurantId(100L);
        AselOrderItem item1 = AselOrderItem.restore(null, null, new MenuItemId(10L), restaurantId, 2, BigDecimal.valueOf(50));
        AselOrderItem item2 = AselOrderItem.restore(null, null, new MenuItemId(11L), restaurantId, 1, BigDecimal.valueOf(100));

        // when
        AselOrder order = AselOrder.createOrder(customerId, restaurantId, List.of(item1, item2));

        // then
        assertThat(order.getId()).isNotNull();
        assertThat(order.getCustomerId()).isEqualTo(customerId);
        assertThat(order.getRestaurantId()).isEqualTo(restaurantId);
        assertThat(order.getItems()).hasSize(2);
        assertThat(order.getTotalAmount()).isEqualTo(BigDecimal.valueOf(150)); // 2*50 + 1*100
        assertThat(order.getStatus()).isEqualTo(AselOrderStatus.PENDING);
        assertThat(order.getCreatedTime()).isNotNull();

        // Event kontrolü
        assertThat(order.getEvents()).hasSize(1);
        assertThat(order.getEvents().get(0)).isInstanceOf(OrderPlacedEvent.class);
    }

    @Test
    void should_throw_if_no_items_provided() {
        // given
        UserId customerId = UserId.create();
        RestaurantId restaurantId = new RestaurantId(100L);

        // when & then
        assertThatThrownBy(() ->
                AselOrder.createOrder(customerId, restaurantId, List.of())
        )
                .isInstanceOf(AselDomainException.class)
                .hasMessageContaining("Sipariş oluştururken kalemler boş bırakılamaz.");
    }

    @Test
    void should_throw_if_customer_or_restaurant_is_null() {
        // Tek item oluşturduk
        AselOrderItem item = AselOrderItem.restore(null, null, new MenuItemId(33L), new RestaurantId(5L), 2, BigDecimal.TEN);

        // expect
        assertThatThrownBy(() ->
                AselOrder.createOrder(null, new RestaurantId(5L), List.of(item))
        )
                .isInstanceOf(AselDomainException.class)
                .hasMessageContaining("Müşteri veya Restorant boş olamaz");

        assertThatThrownBy(() ->
                AselOrder.createOrder(UserId.create(), null, List.of(item))
        )
                .isInstanceOf(AselDomainException.class)
                .hasMessageContaining("Müşteri veya Restorant boş olamaz");
    }

    @Test
    void should_add_item_and_update_total_amount() {
        // given
        UserId customerId = UserId.create();
        RestaurantId restaurantId = new RestaurantId(100L);
        AselOrderItem initialItem = AselOrderItem.restore(null, null, new MenuItemId(10L), restaurantId, 1, BigDecimal.valueOf(50));

        AselOrder order = AselOrder.createOrder(customerId, restaurantId, List.of(initialItem));
        assertThat(order.getItems()).hasSize(1);
        assertThat(order.getTotalAmount()).isEqualTo(BigDecimal.valueOf(50));

        AselOrderItem newItem = AselOrderItem.restore(null, null, new MenuItemId(20L),restaurantId,  3, BigDecimal.valueOf(10));
        order.addItem(newItem);

        assertThat(order.getItems()).hasSize(2);

        assertThat(order.getTotalAmount()).isEqualTo(BigDecimal.valueOf(60));
        assertThat(newItem.getOrderId()).isEqualTo(order.getId());
    }

    @Test
    void should_restore_existing_order_correctly() {
        // given
        OrderId id = new OrderId(999L);
        UserId customerId = UserId.create();
        RestaurantId restaurantId = new RestaurantId(100L);
        LocalDateTime createdAt = LocalDateTime.now().minusDays(1);

        // when
        AselOrder restored = AselOrder.restore(
                id,
                customerId,
                restaurantId,
                BigDecimal.valueOf(120),
                AselOrderStatus.IN_PROGRESS,
                createdAt,
                null
        );

        // then
        assertThat(restored.getId()).isEqualTo(id);
        assertThat(restored.getCustomerId()).isEqualTo(customerId);
        assertThat(restored.getRestaurantId()).isEqualTo(restaurantId);
        assertThat(restored.getTotalAmount()).isEqualTo(BigDecimal.valueOf(120));
        assertThat(restored.getStatus()).isEqualTo(AselOrderStatus.IN_PROGRESS);
        assertThat(restored.getCreatedTime()).isEqualTo(createdAt);
        assertThat(restored.getEvents()).isEmpty();
    }

    @Test
    void should_change_status_if_valid_transition() {
        AselOrder order = AselOrder.createOrder(
                UserId.create(),
                new RestaurantId(10L),
                List.of(AselOrderItem.restore(null, null, new MenuItemId(11L), new RestaurantId(10L), 2, BigDecimal.valueOf(30)))
        );
        order.clearEvents();

        order.changeStatus(AselOrderStatus.IN_PROGRESS);

        assertThat(order.getStatus()).isEqualTo(AselOrderStatus.IN_PROGRESS);
        assertThat(order.getEvents()).hasSize(1);

        DomainEvent event = order.getEvents().get(0);
        assertThat(event).isInstanceOf(OrderStatusChangedEvent.class);
    }

    @Test
    void should_throw_when_invalid_status_transition() {
        // given
        AselOrder order = AselOrder.createOrder(
                UserId.create(),
                new RestaurantId(10L),
                List.of(AselOrderItem.restore(null, null, new MenuItemId(11L), new RestaurantId(10L), 2, BigDecimal.valueOf(30)))
        );
        order.changeStatus(AselOrderStatus.CANCELLED);

        // when & then
        assertThatThrownBy(() -> order.changeStatus(AselOrderStatus.IN_PROGRESS))
                .isInstanceOf(AselDomainException.class)
                .hasMessageContaining("yeni durumla değiştirilemez");
    }

    @Test
    void should_clear_events() {
        // given
        AselOrder order = AselOrder.createOrder(
                UserId.create(),
                new RestaurantId(10L),
                List.of(AselOrderItem.restore(null, null, new MenuItemId(11L),new RestaurantId(10L), 2, BigDecimal.valueOf(30)))
        );

        assertThat(order.getEvents()).isNotEmpty();

        // when
        order.clearEvents();

        // then
        assertThat(order.getEvents()).isEmpty();
    }
}

