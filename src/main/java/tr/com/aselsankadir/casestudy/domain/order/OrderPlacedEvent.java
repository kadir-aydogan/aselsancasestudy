package tr.com.aselsankadir.casestudy.domain.order;

import tr.com.aselsankadir.casestudy.domain.common.DomainEvent;

public class OrderPlacedEvent implements DomainEvent {
    private AselOrder aselOrder;

    public OrderPlacedEvent(AselOrder aselOrder) {
        this.aselOrder = aselOrder;
    }

    public AselOrder getAselOrder() {
        return aselOrder;
    }

    public void setAselOrder(AselOrder aselOrder) {
        this.aselOrder = aselOrder;
    }
}
