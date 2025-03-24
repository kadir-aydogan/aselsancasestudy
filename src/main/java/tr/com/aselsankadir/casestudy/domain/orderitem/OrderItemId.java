package tr.com.aselsankadir.casestudy.domain.orderitem;

import tr.com.aselsankadir.casestudy.domain.common.RandomLongGenerator;

public final class OrderItemId {
    private final Long id;

    public OrderItemId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    static OrderItemId newId() {
        return new OrderItemId(RandomLongGenerator.getLong());
    }
}
