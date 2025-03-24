package tr.com.aselsankadir.casestudy.domain.order;

import tr.com.aselsankadir.casestudy.domain.common.RandomLongGenerator;

public class OrderId {
    private final Long id;

    public OrderId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    protected static OrderId newOrderId() {
        return new OrderId(RandomLongGenerator.getLong());
    }
}
