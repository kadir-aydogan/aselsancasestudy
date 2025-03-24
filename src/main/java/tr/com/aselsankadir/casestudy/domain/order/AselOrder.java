package tr.com.aselsankadir.casestudy.domain.order;

import tr.com.aselsankadir.casestudy.domain.common.AselDomainException;
import tr.com.aselsankadir.casestudy.domain.common.DomainEvent;
import tr.com.aselsankadir.casestudy.domain.orderitem.AselOrderItem;
import tr.com.aselsankadir.casestudy.domain.restaurant.RestaurantId;
import tr.com.aselsankadir.casestudy.domain.user.UserId;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class AselOrder {
    private OrderId id;
    private UserId customerId;
    private RestaurantId restaurantId;
    private BigDecimal totalAmount;
    private AselOrderStatus status;
    private List<AselOrderItem> items = new ArrayList<>();
    private LocalDateTime createdTime;
    private LocalDateTime updateTime;

    private List<DomainEvent> events = new ArrayList<>();

    private AselOrder(OrderId id,
                     UserId customerId,
                     RestaurantId restaurantId,
                     BigDecimal totalAmount,
                     AselOrderStatus status,
                     List<AselOrderItem> items,
                     LocalDateTime createdTime,
                     LocalDateTime updateTime) {

        if (customerId == null || restaurantId == null)
            throw new AselDomainException("Müşteri veya Restorant boş olamaz.");

        this.id = id;
        this.customerId = customerId;
        this.restaurantId = restaurantId;
        this.totalAmount = totalAmount;
        this.status = status;
        this.items = items;
        this.createdTime = createdTime;
        this.updateTime = updateTime;
    }

    public static AselOrder createOrder(UserId customerId, RestaurantId restaurantId, List<AselOrderItem> items) {

        if (items == null || items.isEmpty()) {
            throw new AselDomainException("Sipariş oluştururken kalemler boş bırakılamaz.");
        }

        AselOrder order = new AselOrder(
                OrderId.newOrderId(),
                customerId,
                restaurantId,
                BigDecimal.ZERO,
                AselOrderStatus.PENDING,
                new ArrayList<>(),
                LocalDateTime.now(),
                null
        );
        items.forEach(order::addItem);

        order.addEvent(new OrderPlacedEvent(order));

        return order;
    }

    public void addItem(AselOrderItem aselOrderItem) {

        if (!aselOrderItem.getRestaurantId().equals(this.restaurantId)) {
            throw new AselDomainException("Farklı restorandan kalem eklenemez!");
        }

        aselOrderItem.setOrderId(this.id);

        BigDecimal currentAmount = this.totalAmount;
        if (Objects.isNull(currentAmount)) {
            this.totalAmount = BigDecimal.ZERO;
        }

        BigDecimal added = currentAmount.add(aselOrderItem.getAmount());
        this.setTotalAmount(added);

        this.items.add(aselOrderItem);
    }

    public static AselOrder restore(
            OrderId id,
            UserId customerId,
            RestaurantId restaurantId,
            BigDecimal totalAmount,
            AselOrderStatus status,
            LocalDateTime createdTime,
            LocalDateTime updateTime
    ) {
        return new AselOrder(id, customerId, restaurantId, totalAmount, status, null, createdTime, updateTime);
    }

    public OrderId getId() { return id; }
    public void setId(OrderId id) {this.id= id ;}
    public UserId getCustomerId() { return customerId; }
    public RestaurantId getRestaurantId() { return restaurantId; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public AselOrderStatus getStatus() { return status; }
    public List<AselOrderItem> getItems() { return items; }
    public LocalDateTime getCreatedTime() { return createdTime; }
    public LocalDateTime getUpdateTime() { return updateTime; }

    public void changeStatus(AselOrderStatus newStatus) {
        if (!canChangeTo(newStatus)) {
            throw new AselDomainException("Bu siparişin durumu, yeni durumla değiştirilemez. Sipariş durumu: " + this.status);
        }
        this.status = newStatus;
        this.updateTime = LocalDateTime.now();

        this.addEvent(new OrderStatusChangedEvent(this));
    }

    public void clearEvents() {
        this.events.clear();
    }

    public List<DomainEvent> getEvents() {
        return Collections.unmodifiableList(this.events);
    }

    private void addEvent(DomainEvent evet) {
        this.events.add(evet);
    }

    public boolean canChangeTo(AselOrderStatus newStatus) {
        return switch (this.status) {
            case PENDING -> newStatus == AselOrderStatus.IN_PROGRESS || newStatus == AselOrderStatus.CANCELLED;
            case IN_PROGRESS -> newStatus == AselOrderStatus.DELIVERED || newStatus == AselOrderStatus.CANCELLED;
            case DELIVERED, CANCELLED -> false;
        };
    }

    private void setTotalAmount(BigDecimal amount) {
        this.totalAmount = amount;
    }
}
