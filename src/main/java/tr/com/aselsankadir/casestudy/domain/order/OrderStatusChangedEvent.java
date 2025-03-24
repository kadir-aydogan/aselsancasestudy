package tr.com.aselsankadir.casestudy.domain.order;

import tr.com.aselsankadir.casestudy.domain.common.DomainEvent;

import java.time.LocalDateTime;

public class OrderStatusChangedEvent implements DomainEvent {
    private AselOrder order;
    private LocalDateTime when;

    public OrderStatusChangedEvent(AselOrder order) {
        this.order = order;
        this.when = LocalDateTime.now();
    }

    public AselOrder getOrder() {
        return order;
    }

    public LocalDateTime getWhen() {
        return when;
    }
}
