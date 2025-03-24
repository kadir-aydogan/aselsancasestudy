package tr.com.aselsankadir.casestudy.application.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tr.com.aselsankadir.casestudy.domain.common.AselRuntimeException;
import tr.com.aselsankadir.casestudy.domain.order.AselOrderDetail;
import tr.com.aselsankadir.casestudy.domain.order.IAselOrderDetailRepository;
import tr.com.aselsankadir.casestudy.domain.order.OrderId;
import tr.com.aselsankadir.casestudy.domain.restaurant.RestaurantId;
import tr.com.aselsankadir.casestudy.domain.user.UserId;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AselOrderServiceTest {

    @Mock private IAselOrderDetailRepository detailRepository;
    @Mock private AselOrderItemService orderItemService;

    @InjectMocks
    private AselOrderService orderService;

    private final OrderId orderId = new OrderId(1L);
    private final RestaurantId restaurantId = new RestaurantId(99L);
    private final UserId customerId = UserId.create();

    @Test
    void should_get_order_for_restaurant() {

        AselOrderDetail dto = new AselOrderDetail();

        when(detailRepository.findByRestaurantId(restaurantId, orderId)).thenReturn(Optional.of(dto));
        when(orderItemService.findAllByOrderId(restaurantId, orderId)).thenReturn(List.of());

        AselOrderDetail result = orderService.getOrderForRestaurant(restaurantId, orderId);

        assertThat(result).isEqualTo(dto);
        verify(orderItemService).findAllByOrderId(restaurantId, orderId);
    }

    @Test
    void should_throw_if_order_for_restaurant_not_found() {
        when(detailRepository.findByRestaurantId(restaurantId, orderId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.getOrderForRestaurant(restaurantId, orderId))
                .isInstanceOf(AselRuntimeException.class)
                .hasMessageContaining("Sipariş bulunamadı");
    }

    @Test
    void should_get_orders_for_customer() {
        when(detailRepository.findAllByCustomerId(customerId)).thenReturn(List.of(new AselOrderDetail()));

        List<AselOrderDetail> results = orderService.getOrdersForCustomer(customerId);

        assertThat(results).hasSize(1);
    }

    @Test
    void should_get_order_for_customer() {
        AselOrderDetail dto = new AselOrderDetail();
        when(detailRepository.findByCustomerId(customerId, orderId)).thenReturn(Optional.of(dto));
        when(orderItemService.findAllByOrderId(orderId)).thenReturn(List.of());

        AselOrderDetail result = orderService.getOrderForCustomer(customerId, orderId);

        assertThat(result).isEqualTo(dto);
        verify(orderItemService).findAllByOrderId(orderId);
    }

    @Test
    void should_throw_if_order_for_customer_not_found() {
        when(detailRepository.findByCustomerId(customerId, orderId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.getOrderForCustomer(customerId, orderId))
                .isInstanceOf(AselRuntimeException.class)
                .hasMessageContaining("Sipariş bulunamadı");
    }
}
