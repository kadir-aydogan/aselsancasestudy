package tr.com.aselsankadir.casestudy.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tr.com.aselsankadir.casestudy.domain.common.AselRuntimeException;
import tr.com.aselsankadir.casestudy.domain.common.IDomainEventPublisher;
import tr.com.aselsankadir.casestudy.domain.order.AselOrder;
import tr.com.aselsankadir.casestudy.domain.order.AselOrderStatus;
import tr.com.aselsankadir.casestudy.domain.order.IAselOrderRepository;
import tr.com.aselsankadir.casestudy.domain.order.OrderId;
import tr.com.aselsankadir.casestudy.domain.restaurant.RestaurantId;
import tr.com.aselsankadir.casestudy.domain.user.UserId;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

public class AselOrderUpdateStatusUseCaseTest {

    @Mock
    private IAselOrderRepository orderRepository;
    @Mock
    private IDomainEventPublisher domainEventPublisher;

    @InjectMocks
    private AselOrderUpdateStatusUseCase useCase;

    private final OrderId orderId = new OrderId(1L);
    private final RestaurantId restaurantId = new RestaurantId(42L);

    private AselOrder order;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        order = AselOrder.restore(
                orderId,
                UserId.create(),
                restaurantId,
                BigDecimal.valueOf(100),
                AselOrderStatus.PENDING,
                LocalDateTime.now(),
                null
        );
    }

    @Test
    void should_update_order_status_and_publish_event() {
        // Arrange
        when(orderRepository.findByRestaurantId(restaurantId, orderId)).thenReturn(Optional.of(order));
        when(orderRepository.updateStatus(restaurantId, orderId, AselOrderStatus.IN_PROGRESS)).thenReturn(1);

        // Act
        int result = useCase.updateStatus(restaurantId, orderId, AselOrderStatus.IN_PROGRESS);

        // Assert
        assertThat(order.getStatus()).isEqualTo(AselOrderStatus.IN_PROGRESS);
        assertThat(result).isEqualTo(1);

        verify(orderRepository).updateStatus(restaurantId, orderId, AselOrderStatus.IN_PROGRESS);
        verify(domainEventPublisher).publishAll(anyList());
    }

    @Test
    void should_throw_if_order_not_found() {
        when(orderRepository.findByRestaurantId(restaurantId, orderId)).thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                useCase.updateStatus(restaurantId, orderId, AselOrderStatus.IN_PROGRESS)
        )
                .isInstanceOf(AselRuntimeException.class)
                .hasMessageContaining("Sipariş bulunamadı.");

        verify(orderRepository).findByRestaurantId(restaurantId, orderId);
        verify(domainEventPublisher, never()).publishAll(any());
    }

    @Test
    void should_not_publish_event_if_update_failed() {
        when(orderRepository.findByRestaurantId(restaurantId, orderId)).thenReturn(Optional.of(order));
        when(orderRepository.updateStatus(restaurantId, orderId, AselOrderStatus.IN_PROGRESS)).thenReturn(-1);

        int result = useCase.updateStatus(restaurantId, orderId, AselOrderStatus.IN_PROGRESS);

        assertThat(result).isEqualTo(-1);
        verify(domainEventPublisher, never()).publishAll(any());
    }
}
